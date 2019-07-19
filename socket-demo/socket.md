### 实现Socket通信

#### 基于TCP的Socket通信

TCP提供基于“流”的“长连接”的数据传递，发送的数据带有顺序性。TCP是一种流协议，以流为单位进行数据传输。

##### 什么是长连接？

长连接可以实现当服务端与客户端连接成功后连续地传输数据，在这个过程中，连接保持开启的状态，数据传输完毕后，无论是否使用这个连接，该连接都**保持连接**的状态。

##### 什么是短连接

短连接是当服务端与客户端连接成功后开始传输数据，数据传输完毕后则**连接立即关闭**，如果还想再次传输数据，则需要再**创建新的连接**进行数据传输。

##### 什么是连接？

在TCP/IP中，连接可以认为是服务端与客户端确认彼此都存在的过程。

使用短连接进行数据传输时，由于每次传输数据都要创建连接，这样会产生多个连接对象，增大占用内存的空间，在创建连接时也要进行服务端与客户端之间确认彼此存在，确认的过程比较耗时，因此运行效率较低。由于UDP是无连接协议，也就是服务端与客户端没有确认彼此都存在的握手过程，因此在UDP里面不存在长连接与短连接的概念。

长连接的优缺点

优点：除了第一次之外，客户端不需要每次传输数据时都先于服务端进行握手，这样就减少了握手确认的时间，直接传输数据，提高程序运行效率。

缺点：在服务端保存多个Socket对象，大量占用服务器资源。

短连接的优缺点

优点：在服务端不需要保存多个Socket对象，降低内存占用率。

缺点：每次传输数据前都要重新创建连接，也就是每次都要进行3次握手，增加处理的时间。

#### 服务端与客户端互传以及I/O流顺序问题

1. 服务端先获得 ObjectInputStream 对象，客户端就要先获得 ObjectOutputStream 对象
2. 服务端先获得 ObjectOutputStream 对象，客户端就要先获得 ObjectInputStream 对象。

### ServerSocket 类的使用

#### 常用方法

##### 接受accept与超时Timeout

1. public Socket accept() 

   方法的作用就是侦听并接受此套接字的链接。此方法在连接传入之前一直阻塞。 

2. public void setSoTimeout(int timeout)

   设置超时时间，通过指定超时timeout值启用/禁用 SO_TIMEOUT，以ms为单位。

   在将此选项设置为非零的超时timeout值时，对此 ServerSocket 调用 accept() 方法将只阻塞 timeout 的时间长度。如果超过超时值，将引发 **java.net.SocketTimeoutException** ，但**ServerSocket 仍旧有效**，在结合 try-catch 结构后，还可以继续进行 accept() 方法的操作。SO_TIMEOUT 选项必须在**进入阻塞操作前**被启用才能生效。注意，超时值必须是大于0的数。超时值为0被解释为无穷大超时值。

   参数 int timeout 的作用是在指定的时间内必须有客户端的连接请求，超过这个时间即出现异常，默认值是0，即永远等待。

3. public int getSoTimeout()

   获取 SO_TIMEOUT 的设置。

##### 构造方法的backlog参数含义

4. public ServerSocket(int port, int backlog)

   构造函数中的 backlog 的主要作用就是允许接受客户端连接请求的个数。客户端有很多连接进入操作系统中，将这些连接放入操作系统的队列中，当执行 accept() 方法时，允许客户端连接的个数要取决于 backlog 参数。

   利用指定 backlog 创建服务器套接字并将其绑定到指定的本地端口 port。对 port 端口参数传递值为0，意味着将自动分配空闲的端口号。

   传入 backlog 参数的作用是设置最大等待队列长度，如果队列已满，则拒绝该连接。

   backlog参数必须是大于0的正值，如果传递的值等于或小于0，则使用默认值50。

##### 绑定到指定的SocketAddress

1. public void bind(SocketAddress endpoint)

   将ServerSocket绑定到特定的Socket地址(IP地址和端口号)，使用这个地址与客户端进行通信。如果地址为null，则系统将挑选一个临时端口号和一个有效本地地址来绑定套接字。

   该方法的使用场景就是在使用ServerSocket类的无参构造方法后想指定本地端口。

   SocketAddress 类表示**不带任何协议**附件的 Socket Address。SocketAddress 类是抽象类，有1个子类 InetSocketAddress。

   需要注意的是，InetAddress 类代表 IP 地址，而 InetSocketAddress 类代表 Socket 地址。

2. public void bind(SocketAddress endpoint, int backlog)

   绑定到指定IP，还可以设置backlog的连接数量。

##### 获取本地SocketAddress对象及本地端口

1. public SocketAddress getLocalSocketAddress()

   获取本地的SocketAddress对象，它返回此Socket绑定的端口地址。

2. public int getLocalPort()

   获取Socket绑定到本地的端口号

##### Socket选项 ReuseAddress

1. public void setReuseAddress(boolean on)

   启用/禁用 SO_REUSEADDR 套接字选项。

   关闭TCP连接时，该连接可能在关闭后的一段时间内保持超时状态（通常称为 TIME_WAIT 状态或2MSL等待状态）。对于使用已知套接字地址或端口的应用程序而言，如果存在处于超时状态的连接（包括地址和端口），则应用程序可能不能将套接字绑定到所需的SocketAddress上。

   如果在使用 bind(SocketAddress) 方法"绑定套接字之前"启用 SO_REUSEADDR 选项，就可以允许绑定到处于超时状态的套接字。

   在调用Socket类的close()方法时，会关闭当前连接，释放使用的端口，但在操作系统层面，并不会马上释放当前使用的端口。如果端口呈 TIME_WAIT 状态，则在 Linux 操作系统中可以重用此状态的端口。

   什么是 TIME_WAIT 状态？

   服务端（Server）与客户端（Client）建立TCP连接之后，主动关闭连接的一方就进入 TIME_WAIT 状态。

2. public boolean getReuseAddress()

   测试是否启用 SO_REUSEADDR。

##### Socket选项ReceiveBufferSize

1. public void setReceiveBufferSize(int size)

   为此ServerSocket**接受的套接字的 SO_RCVBUF 选项**设置新的建议值。

   在接受的套接字中，**实际被采纳的值**必须在 accept() 方法返回套接字后通过调用 Socket.getReceiveBufferSize() 方法进行获取。

   注意：对于客户端，SO_RCVBUF选项必须在 connect() 方法调用之前设置，对于服务端，SO_RCVBUF 选项必须在 bind() 前设置。

### Socket类的使用

#### 常用方法

##### 绑定 bind 与 connect 以及端口生成的时机

1. public void bind(SocketAddress bindpoint)

   将套接字绑定到本地地址。如果地址为null，则系统将随机挑选一个空闲的端口和一个有效的本地地址来绑定套接字。该方法要**优先于**connect()方法执行，也就是要先绑定本地端口再执行连接方法。

2. public void connect(SocketAddress endpoint)

   将此套接字连接到服务端。

##### 连接与超时

3. public void connect(SocketAddress endpoint, int timeout)

   将此套接字连接到服务端，并指定一个超时值。超时值是0意味着无限超时。

##### 获得远程端口与本地端口

4. public int getPort()

   返回此套接字**连接到的远程端口**

5. public int getLocalPort()

   返回此套接字**绑定到的本地端口**

##### 获得本地InetAddress地址与本地SocketAddress地址

6. public InetAddress getLocalAddress()

   获取套接字**绑定的本地InetAddress地址**信息

7. public SocketAddress getLocalSocketAddress()

   返回此套接字**绑定的端点的Socket-Address地址**信息。

##### 获得远程InetAddress与远程SocketAddress地址

8. public InetAddress getInetAddress()

   返回此套接字**连接到的远程**的InetAddress地址。

9. public SocketAddress getRemoteSocketAddress()

   返回此套接字**远程端点的SocketAddress地址**

10. public void close()

    关闭此套接字

    所有当前阻塞于此套接字上的I/O操作中的线程都将抛出SocketException。套接字被关闭后，便不可在以后的网络连接中使用（即无法重新连接或重新绑定），如果想再次使用套接字，则需要创建新的套接字。

    关闭此套接字也将会关闭该套接字的 InputStream 和 OutputStream。如果此套接字有一个与之关联的通道，则关闭通道。

##### 开启半读半写状态

11. public void shutdownInput()

    将此套接字的输入流置于"流的末尾EOF"。

    也就是在套接字上调用 shutdownInput() 方法后从套接字输入流读取内容，流将返回EOF(文件结束符)。发送到套接字的输入流端的任何数据都将在确认后被**静默丢弃**。调用此方法的一端进入半读状态(read-half)，也就是此端不能获得输入流，但对端却能获得输入流。一端能读，另一端不能读，称为半读。

12. public void shutdownOutput()

    禁用此套接字的输出流。

    对于TCP套接字，任何以前写入的数据都将被发送，并且后跟TCP的正常连接终止序列。如果在套接字上调用 shutdownOutput() 方法后写入套接字输出流，则该流将抛出 IOException。调用此方法的一端进入半写状态(write-half)，也就是此端不能获得输出流。但对端却能获得输出流。一端能写，另一端不能写，称为半写。

13. public boolean isInputShutdown()

    返回是否关闭套接字连接的半读状态(read-half)。

14. public boolean isOutputShutdown()

    返回是否关闭连接的半写状态(write-half)

##### Socket选项TcpNoDelay

15. public void setTcpNoDelay(boolean on)

    启用/禁用TCP_NODELAY（启用/禁用 Nagle 算法）。

    on - true启用TCP_NODELAY， false禁用。

##### Socket选项SendBufferSize

1. public void setSendBufferSize(int size)

   Socket 中的 SO_RCVBUF 选项是设置接收缓冲区的大小的，而SO_SNDBUF选项是设置发送缓冲区大小的。

   将此Socket的SO_SNDBUF选项设置为指定的值。平台的网络连接代码将SO_SNDBUF选项用作设置底层网络I/O缓存的大小的提示。由于SO_SNDBUF是一种提示，因此想要验证缓冲区设置大小的应用程序应该调用 getSendBufferSize() 方法。

##### Socket选项Linger

1. public void setSoLinger(boolean on, int linger)

   Socket中的SO_LINGER选项用来控制 Socket 关闭 close() 方法时的行为。

   在默认情况下，执行Socket的close()方法后，该方法会立即返回，但底层的Socket实际上并不会立即关闭，它会延迟一段时间。在延迟的时间里做什么呢？是将"发送缓冲区"中的剩余数据在延迟的时间内继续发送给对方，然后才会真正地关闭Socket连接。

   setSoLinger(boolean on, int linger)方法的作用是启用/禁用具有指定逗留时间（以秒为单位）的SO_LINGER。最大超时值是特定于平台的。改设置仅影响套接字关闭。参数on的含义为是否逗留，参数linger的含义为逗留时间，单位为秒。

2. public int getSoLinger()

   返回SO_LINGER的设置。返回-1意味着禁用该选项。该设置**仅影响套接字关闭**。

##### Socket选项Timeout

1. public void setSoTimeout(int timeout)

   启用/禁用带有指定超时值得 SO_TIMEOUT，以毫秒为单位。将此选项设为非零的超时值时，在与此Socket关联的**InputStream 上调用 read()** 方法将只阻塞此时间长度。如果超过超时值，就将引发 java.net.SocketTimeoutException，尽管**Socket仍旧有效**。启用 timeOut 特性必须在进入阻塞前被启用才能生效。

2. public int getSoTimeout()

   返回SO_TIMEOUT的设置。

##### Socket 选项 OOBInline

Socket的选项SO_OOBINLINE 的作用是在套接字上接收的所有TCP紧急数据都将通过套接字输入流接收。禁用该选项时（默认），将悄悄丢弃紧急数据。OOB(Out Of Bound,带外数据)可以理解成是需要紧急发送的数据。

1. public void setOOBInline(boolean on)

   启用/禁用OOBINLINE选项（TCP紧急数据的接收者），默认情况下，此选项是禁用的，即在套接字上接收的TCP紧急数据被默认丢弃。

   public void setOOBInline(boolean on)方法在接收端进行设置来决定是否接收与忽略紧急数据。在发送端，使用public void sendUrgentData(int data)方法向对方发送1个单字节的数据。

2. public void sendUrgentData(int data)

   向对方发送1个单字节的数据，但是这个单字节的数据并不存储在输出缓冲区中，而是立即将数据发送出去，而在对方程序中并不知道发送过来的数据是由 OutputStream 还是由 sendUrgentData(int data)发送过来的。

在调用sendUrgentData()方法时所发送的数据可以被对方忽略，结合这个特性可以实现测试网络连接状态的心跳机制，测试代码如下:

```java
/**
 * 服务端
 *
 * @author xiaoshu
 */
public class Test1 {
    public static void main(String[] args) throws InterruptedException, IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        Socket socket = serverSocket.accept();
        Thread.sleep(Integer.MAX_VALUE);
        socket.close();
        serverSocket.close();
    }
}

/**
 * 客户端
 *
 * @author xiaoshu
 */
public class Test2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8088);
        try {
            int count = 0;
            for (; ; ) {
                socket.sendUrgentData(1);
                count++;
                System.out.println("执行了 " + count + "次嗅探");
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----------网络断开了!");
            socket.close();
        }
    }
}
```

##### Socket选项KeepAlive

Socket选项SO_KEEPALIVE的作用是在创建了服务端与客户端时，使客户端连接上服务端。

当设置SO_KEEPALIVE为true时，若对方在某个时间（时间取决于操作系统内核设置）内没有发送任何数据过来，那么端点都会发送一个ACK探测包到对方，探测对方的TCP/IP连接是否有效（对方可能断电，断网）。如果不设置此选项，那么当客户端宕机时，服务端永远也不知道客户端宕机了，仍然保存这个失效的连接。如果设置了此选项，就会将此连接关闭。

1. public boolean getKeepAlive()

   判断是否启用SO_KEEPALIVE选项

2. public void setKeepAlive(boolean on)

   设置是否启用SO_KEEPALIVE选项。

##### Socket选项TrafficClass

IP规定了4中服务类型，用来定性地描述服务的质量。

1）IPTOS_LOWCOST (0x02) 发送成本低
2）IPTOS_RELIABILITY (0x04) 高可靠性，保证把数据可靠地送到目的地。
3）IPTOS_THROUGHPUT (0x08) 最高吞吐量，一次可以接收或者发送大批量的数据。
4）IPTOS_LOWDELAY (0x10) 最小延迟，传输数据的速度快，把数据快速送达目的地。

这 4 中服务类型还可以使用"或"运算进行相应的组合。

1. public void setTrafficClass(int tc)

   为从此 Socket 上发送的包在 IP 头中设置流量类别(traffic class)

2. public int getTrafficClass()

   为从此 Socket上发送的包获取 IP 头中的流量类别或服务类型。

当向 IP 头中设置了流量类型后，路由器或交换机就会根据这个流量类型来进行不同的处理，同时必须要硬件设备进行参与处理。

### 基于 UDP 的 Socket通信

UDP（User Datagram Protocol，用户数据报协议)是一种面向无连接的传输层协议，提供不可靠的信息传送服务。

无连接是指通信时服务端与客户端不需要建立连接，直接把数据包从一端发送到另一端，对方获取数据包再进行数据的处理。

UDP 将网络数据流量压缩成数据包的形式，一个典型的数据包就是一个二进制的数据传输单位，每一个数据包的前 8 个字节用来包含报头信息，剩余字节则用来包含具体的传输数据。

因为 UDP 报文没有可靠性保证、没有顺序保证，以及没有流量控制等功能，所以它可靠性较差。但是，正是因为 UDP 的控制选项较少，在数据传输过程中延迟小、数据传输效率高，因而适合对可靠性要求不高的应用程序。

UDP 和 TCP 都属于传输层协议。

#### 使用 UDP 实现 Socket 通信

在使用 UDP 实现 Socket 通信时，服务端与客户端都是使用 DatagramSocket 类，传输的数据要存放在 DatagramPacket 类中。

DatagramSocket 类表示用来发送和接收数据报包的套接字。数据报套接字是包投递服务的发送或接收点。每个在数据报套接字上发送或接收的包都是单独编址和路由的。从一台机器发送到另一台机器的多个包可能选择不同的路由，也可能按不同的顺序到达。在 DatagramSocket 上总是启用 UDP 广播发送。为了接收广播包，应该将 DatagramSocket 绑定到通配符地址。在某些实现中，将 DatagramSocket绑定到一个更加具体的地址时广播包也可以被接收。

DatagramPacket 类表示数据报包。数据报包用来实现无连接包投递服务。每条报文仅根据该包中包含的信息从一台机器路由到另一台机器。从一台机器发送另一台机器的多个包可能选择不同的路由，也可能按不同的顺序到达。

理论上，一个 UDP 包最大的长度为 2<sup>16</sup>-1(65536 - 1 = 65565)，因此，IP 包最大的发送长度为 65535。但是，在这 65535 之内包含IP 协议头的 20 个字节，还有 UDP 协议头的 8 个字节，即 65535 - 20 - 8 = 65507，因此，UDP 传输用户数据最大的长度为 65507，因此 UDP 传输用户数据的最大的长度为 65507。如果传输的数据大于 65507，则在发送端出现异常。

1. public void receive(DatagramPacket p)

   从此套接字接收数据报包。数据报包也包含发送方的 IP 地址和发送方机器上的端口号。此方法在接收数据报前一直阻塞。

   数据报包对象的 length 字段包含所接收信息的长度。如果发送的信息比接收端包关联的 byte[] 长度长，该信息将被截断。如果发送信息的长度大于 65507，则发送端出现异常。

2. public void send(DatagramPacket p)

   从此套接字发送数据报包。

   DatagramPacket 包含的信息有：将要发送的数据及其长度、远程主机的 IP 地址和远程主机的端口号。

#### DatagramPacket 类中常用 API 的使用

1. public void setData(byte[] buf)

   设置此数据包的数据缓冲区。 

   将此DatagramPacket的偏移设置为0，并将长度设置为buf的长度。

2. public void setLength(int length)

   设置此数据包的长度。 

   包的长度是是指报数据缓冲区中将要发送的字节数，或用来接收数据的包数据缓冲区的字节数。长度必须小于或等于偏移量与包缓冲区长度之和。