[TOC]

###选择器与I/0多路复用

![多路复用](./images/多路复用.png)


线程数会随着通道的多少而动态地增减以进行适配，在内部其实并不永远是一个线程，多路复用的核心目的就是使用最少的线程去操作更多的通道。在JDK的源代码中，创建线程的个数是根据通道的数量来决定的，每注册1023个通道就创建1个新的线程，这些线程执行Windows中select()方法来监测系统socket事件，如果发生事件则通知应用层中的main线程终止阻塞，继续向下运行，处理事件。

**注意**

学习I/O多路复用时一定要明白一个知识点，就是在使用I/O多路复用时，这个线程不是以for循环的方式来判断每个通道是否有数据要进行处理，而是以**操作系统层作为“通知器”**，来“通知JVM中的线程”哪个通道中的数据需要进行处理，这点一定要注意。当不使用for循环的方式来进行判断，而是使用通知的方式时，这就大大提高了程序运行的效率，不会出现无限期的for循环迭代空运行了。

### 通道类SelectableChannel的介绍

SelectableChannel 类可以通过选择器实现多路复用。

当SelectableChannel在选择器里注册后，通道在注销之前将一直保持注册状态。需要注意的是，不能直接注销通道，而是通过调用SelectionKey类的`cancel()`方法显式地取消，这将在选择器的下一次选择select()操作期间去注销通道。无论是通过调用通道的close()方法，还是中断阻塞于该通道上I/O操作中的线程来关闭该通道，都会隐式地取消该通道的所有SelectionKey。

如果选择器本身已关闭，则将注销该通道，并且表示其注册的SelectionKey将立即无效。

一个通道至多只能在任意特定选择器上注册一次。可以通过调用isRegistered()方法来确定是否已经向一个或多个选择器注册了某个通道。

SelectableChannel在多线程并发环境下是安全的。

#### 常用方法

#####执行注册操作与获得SelectionKey对象

1. public final SelectionKey register(Selector sel, int ops)

   向给定的选择器注册此通道，返回一个选择键(SelectionKey)。

   ops代表register()方法的返回值SelectionKey的可用操作集，操作集是在SelectionKey类中以常量的形式进行提供的。

   |            | 字段摘要                                  |
   | ---------- | ----------------------------------------- |
   | static int | OP_ACCEPT 用于套接字接受操作的操作集位。  |
   | static int | OP_CONNECT 用于套接字连接操作的操作集位。 |
   | static int | OP_READ 用于读取操作的操作集位。          |
   | static int | OP_WRITE 用于写入操作的操作集位。         |

2. public abstract boolean isRegistered()

   判断此通道当前是否已向任何选择器进行了注册。

   由于对SelectionKey执行取消操作和通道进行注销之间有**延迟**，因此在已取消某个通道的所有SelectionKey后，该通道可能在一定时间内还会保持已注册状态。关闭通道后，该通道可能在一定时间内还会保持已注册状态。

### 通道类ServerSocketChannel

#### 获得ServerSocketChannel与ServerSocket对象

ServerSocketChannel类是针对面向流的侦听套接字的可选择通道。ServerSocketChannel不是侦听网络套接字的完整抽象，必须通过调用socket()方法所获得的关联ServerSocket对象来完成对套接字选项的绑定和操作。

1. public static ServerSocketChannel open()

   打开服务器套接字通道。

   新通道的套接字最初是未绑定的；可以接受连接之前，必须通过它的某个套接字的bind()方法将其绑定到具体的地址。

2. public abstract ServerSocket socket()

   返回ServerSocket类的对象，然后与客户端套接字进行通信。

3. public abstract Object blockingLock()

   获取其configureBlocking()和register()方法实现同步的对象，防止重复注册。

4. public abstract SocketAddress getLocalAddress()

   获取绑定的SocketAddress对象。

5. public final SelectionKey keyFor(Selector sel)

   获取通道向给定选择器注册的SelectionKey。

### 通道类SocketChannel

#### 打开通道并连接到远程

1. public static SocketChannel open(SocketAddress remote)

   打开套接字通道并将其连接到远程地址。

   注意:

   如果先调用`public static SocketChannel open(SocketAddress remote)`方法,然后设置SocketOption,则不会出现预期的效果,因为在`public static SocketChannel open(SocketAddress remote)`方法中已经自动执行了connect()方法.

   ```java
   public static SocketChannel open(SocketAddress remote)
           throws IOException
   {
       SocketChannel sc = open();
       try {
           sc.connect(remote);
       } catch (Throwable x) {
           try {
               sc.close();
           } catch (Throwable suppressed) {
               x.addSuppressed(suppressed);
           }
           throw x;
       }
       assert sc.isConnected();
       return sc;
   }
   ```

#### 执行Connect连接操作

1. public abstract boolean connect(SocketAddress remote)

   连接到远程通道的Socket。如果此通道处于非阻塞模式，则此方法的调用将启动非阻塞连接操作。

   如果通道呈阻塞模式，则立即发起连接；如果呈非阻塞模式，则不是立即发起连接，而是在随后的某个时间才发起连接。

#### 判断此通道上是否正在进行连接操作

1. Public abstract boolean isConnectionPending()

   判断此通道上是否正在进行连接操作

#### 完成套接字通道的连接过程

1. public abstract boolean finishConnect()

   完成套接字通道的连接过程。通过将套接字通道置于非阻塞模式，然后调用其connect()方法来发起非阻塞连接操作。

### Selector 类的使用

Selector 类的主要作用是作为 SelectableChannel 对象的多路复用器。

可通过调用 Selector 类的 open() 方法创建选择器，该方法将使用系统的默认 SelectorProvider 创建新的选择器。也可以通过调用自定义选择器提供者的 openSelector()方法来创建选择器。

选择器内部维护了3中SelectionKey-Set(选择键集)

1）键集：包含的键表示当前通道到此选择器的注册，也就是通过某个通道的`register()`方法注册该通道时，所带来的影响是向选择器的键集中添加了一个键。此集合由`keys()`方法返回。键集本身是不可直接修改的。

2）已选择键集：在**首先**调用`select()`方法选择操作期间，检测每个键的通道是否已经至少为该键的相关操作集所标识的一个操作准备就绪，然后调用`selectedKeys()`方法返回已就绪键的集合。已选择键集始终是键集的一个子集。

3）已取消的键：表示已被取消但其通道尚未注销的键的集合。不可直接访问此集合。已取消的键集始终是键集的一个子集。在`select()`方法选择操作期间，从键集中移除已取消的键。

无论是通过关闭某个键的通道还是调用该键的`cancel()`方法来取消键，该键都被添加到其选择器的已取消键集中。取消某个键会导致**下一次select()**方法选择操作期间注销该键的通道，而在注销时将从所有选择器的键集中移除该键。

#### 常用方法

1. public abstract int select()

   具有阻塞性

   作用是选择一组键，其相应的通道已为 I/O 操作准备就绪。此方法执行处于阻塞模式的选择操作。仅 在至少选择一个通道、调用此选择器的 wakeup()方法，或者当前的线程已中断（以先到者为准）后，此方法才返回。

   返回值代表添加到就绪操作集的键的数目，该数目可能为零，为零代表就绪操作集中的内容并没有添加新的键，保持内容不变。

2. public abstract int selectNow()

   选择一组键，其相应的通道已为I/O操作准备就绪。此方法执行**非阻塞**的选择操作。

3. public abstract Selector wakeup()

   使尚未返回的第一个选择操作立即返回。

   如果另一个线程目前正阻塞在select()或select(long)方法的调用中，则该调用将立即返回。

### SelectionKey类的使用

#### 常用方法

1. public abstrace Selector selector()

   返回SelectionKey关联的选择器。

   即使已取消该键，此方法仍将继续返回选择器。

2. public final SelectionKey register(Selector sel, int ops, Object att)

   向给定的选择器注册此通道，返回一个选择键。如果当前已向给定的选择器注册了此通道，则返回表示该注册的选择键。该键的相关操作集将更改为ops，就像调用`interestOps(int)`方法一样。如果att参数不为null，则将该键的附件设置为该值。

3. public final Object attach(Object obj)

   将给点的对象附加到此键。

####  示例代码地址：https://github.com/itinytree/NIO-Socket-demo.git