### Channel接口的子接口

在JDK1.8版本中，Channel接口具有11个子接口：

1. AsynchronousChannel
2. AsynchronousByteChannel
3. ReadableByteChannel
4. ScatteringByteChannel
5. WritableByteChannel
6. GatheringByteChannel
7. ByteChannel
8. SeekableByteChannel
9. NetworkChannel
10. MulticastChannel
11. InterruptibleChannel

### AsynchronousChannel接口介绍

AsynchronousChannel 接口的主要作用是使通道支持异步I/O操作。异步I/O操作有以下两种方式进行实现：

1. 方法

```java
Future<V> operation(...)
```

operation代表I/O操作的名称，大多数是读或写操作。泛型变量V代表经过I/O操作后返回结果的数据类型。使用Future对象可以用于检测I/O操作是否完成，或者等待完成，以及用于接口I/O操作处理后的结果。

2. 回调

```java
void operation(... A attachment, CompletionHandler<V,? super A> handler)
```

A类型对象 attachment 的主要作用是让外部与 CompletionHandler 对象内部进行通信。使用 CompletionHandler 回调的方式实现异步 I/O 操作的优点是 CompletionHandler 对象可以被复用。当 I/O 操作成功或者失败时，CompletionHandler对象中的指定方法会被调用。

异步通道在多线程并发的情况下是线程安全的。某些通道的实现是可以支持并发读和写的，但是不允许在一个未完成的I/O操作上再次调用read或write操作。

在调用cancel()方法以取消读或写操作时，建议废弃I/O操作中使用的所有缓冲区，因为缓冲区中的数据并不是完整的，如果再次打开通道，那么也要尽量避免访问这些缓冲区。

### AsynchronousByteChannel接口介绍

AsynchronousByteChannel 接口的主要作用是使通道支持异步 I/O 操作，操作单位是字节。

![AsynchronousByteChannel](./images/AsynchronousByteChannel.png)

ByteBuffers 类不是线程安全的，尽量保证在对其进行读写操作时，没有其他线程一同进行读写操作。

### ReadableByteChannel接口介绍

ReadableByteChannel 接口的主要作用是使通道允许对字节进行读操作。

![ReadableByteChannel](./images/ReadableByteChannel.png)

ReadableByteChannel 接口只允许有 1 个读操作进行。如果1个线程正在1个通道上执行1个read()操作，那么任何试图发起另一个read()操作的线程都会被阻塞，直到第1个read()操作完成。

通道只接受以字节为单位的数据处理，因为通道和操作系统进行交互时，操作系统只接受字节数据。