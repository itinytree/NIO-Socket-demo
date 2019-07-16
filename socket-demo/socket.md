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

1. public Socket accept() 

   方法的作用就是侦听并接受此套接字的链接。此方法在连接传入之前一直阻塞。 

2. public void setSoTimeout(int timeout)

   设置超时时间，通过指定超时timeout值启用/禁用 SO_TIMEOUT，以ms为单位。

   在将此选项设置为非零的超时timeout值时，对此 ServerSocket 调用 accept() 方法将只阻塞 timeout 的时间长度。如果超过超时值，将引发 **java.net.SocketTimeoutException** ，但**ServerSocket 仍旧有效**，在结合 try-catch 结构后，还可以继续进行 accept() 方法的操作。SO_TIMEOUT 选项必须在**进入阻塞操作前**被启用才能生效。注意，超时值必须是大于0的数。超时值为0被解释为无穷大超时值。

   参数 int timeout 的作用是在指定的时间内必须有客户端的连接请求，超过这个时间即出现异常，默认值是0，即永远等待。

3. public int getSoTimeout()

   获取 SO_TIMEOUT 的设置。

4. public ServerSocket(int port, int backlog)

   构造函数中的 backlog 的主要作用就是允许接受客户端连接请求的个数。客户端有很多连接进入操作系统中，将这些连接放入操作系统的队列中，当执行 accept() 方法时，允许客户端连接的个数要取决于 backlog 参数。

   利用指定 backlog 创建服务器套接字并将其绑定到指定的本地端口 port。对 port 端口参数传递值为0，意味着将自动分配空闲的端口号。

   传入 backlog 参数的作用是设置最大等待队列长度，如果队列已满，则拒绝该连接。

   backlog参数必须是大于0的正值，如果传递的值等于或小于0，则使用默认值50。

5. public void bind(SocketAddress endpoint)

   将ServerSocket绑定到特定的Socket地址(IP地址和端口号)，使用这个地址与客户端进行通信。如果地址为null，则系统将挑选一个临时端口号和一个有效本地地址来绑定套接字。

   该方法的使用场景就是在使用ServerSocket类的无参构造方法后想指定本地端口。

   SocketAddress 类表示**不带任何协议**附件的 Socket Address。SocketAddress 类是抽象类，有1个子类 InetSocketAddress。

   需要注意的是，InetAddress 类代表 IP 地址，而 InetSocketAddress 类代表 Socket 地址。

6. public void setReuseAddress(boolean on)

   启用/禁用 SO_REUSEADDR 套接字选项。

   关闭TCP连接时，该连接可能在关闭后的一段时间内保持超时状态（通常称为 TIME_WAIT 状态或2MSL等待状态）。对于使用已知套接字地址或端口的应用程序而言，如果存在处于超时状态的连接（包括地址和端口），则应用程序可能不能将套接字绑定到所需的SocketAddress上。

   如果在使用 bind(SocketAddress) 方法"绑定套接字之前"启用 SO_REUSEADDR 选项，就可以允许绑定到处于超时状态的套接字。

   在调用Socket类的close()方法时，会关闭当前连接，释放使用的端口，但在操作系统层面，并不会马上释放当前使用的端口。如果端口呈 TIME_WAIT 状态，则在 Linux 操作系统中可以重用此状态的端口。

   什么是 TIME_WAIT 状态？

   服务端（Server）与客户端（Client）建立TCP连接之后，主动关闭连接的一方就进入 TIME_WAIT 状态。

7. public boolean getReuseAddress()

   测试是否启用 SO_REUSEADDR。

8. public void setReceiveBufferSize(int size)

   为此ServerSocket接受的套接字的 SO_RCVBUF 选项设置新的建议值。

   在接受的套接字中，实际被采纳的值必须在 accept() 方法返回套接字后通过调用 Socket.getReceiveBufferSize() 方法进行获取。

### Socket类的使用

#### 常用方法

1. public void bind(SocketAddress bindpoint)

   将套接字绑定到本地地址。如果地址为null，则系统将随机挑选一个空闲的端口和一个有效的本地地址来绑定套接字。

2. public void connect(SocketAddress endpoint)

   将此套接字连接到服务端。

3. public void connect(SocketAddress endpoint, int timeout)

   将此套接字连接到服务端，并指定一个超时值。超时值是0意味着无限超时。