### 获取网络设备信息
Socket不是协议，是一种实现计算机之间通信的技术，而HTTP才是协议。

### NetworkInterface 类的常用方法

1. public String getName()

   取得设备在操作系统中的名称

2. public String getDisplayName()

   取得设备在操作系统中的显示名称

3. public int getIndex()

   获得网络接口的索引。此索引值在不同的操作系统种有可能不一样。索引是大于或等于0的整数，索引未知时，值就是-1.

4. public boolean isUp()

   判断网络接口是否已经开启并正常工作

5. public boolean isLoopback()

   判断该网络接口是否为localhost回调/回环接口。

6. public int getMTU()

   返回MTU大小。在网络传输中是以数据包为基本传输单元，可以使用MTU(Maximum Transmission Unit，最大传输单元)来规定网络传输最大数据包的大小，单位是**字节**。以太网的网卡MTU大多数默认值是1500字节，在IPV6协议中，MTU的范围是1280~65535。

7. public byte[] getHardwareAddress()

   获得网卡的硬件地址.

8. public List<InterfaceAddress> getInterfaceAddresses()

   获取网络接口的InterfaceAddress列表。通过InterfaceAddress类中的方法可以取得网络接口对应的IP地址、子网掩码和广播地址等相关信息。

9. public boolean isPointToPoint()

   判断当前的网络设备是不是点对点设备。

10. public boolean supportsMulticast()

    判断当前的网络设备是否支持多播

    

### InetAddress 的常用方法

1. public String getCanonicalHostName()

   获取此IP地址的完全限定域名(Fully Qualified Domain Name, FQDN)。完全限定域名是指主机名加上全路径，全路径中列出了序列中所有域成员。

2. public String getHostName()

   获取此IP地址的主机名

3. static InetAddress getByName(String host)

   在给定主机名的情况下确定主机的IP地址。参数host可以是计算机名、IP地址，也可以是域名。

4. public static InetAddress[] getAllByName(String host)

   在给定主机名的情况下，根据系统上配置的名称服务返回其IP地址所组成的数组。

### InterfaceAddress 类的常用方法

1. public InetAddress getAddress()

   返回此InterfaceAddress的InetAddress

2. public InetAddress getBroadcast()

   返回此InterfaceAddress广播地址的InetAddress

   只有IPv4网络具有广播地址，在IPv6网络的情况下，将返回`null` 。

3. public short getNetworkPrefixLength()

   返回此地址的网络前缀长度。  这在IPv4地址的上下文中也被称为子网掩码。典型的IPv4值为8（255.0.0.0），16（255.255.0.0）或24（255.255.255.0）。典型的IPv6值为128（:: 1/128）或10（fe80 :: 203：baff：fe27：1243/10）

   

### 回调/回环接口

如果一个网络接口是一个回环/回调网络接口，那么它存在的意义是什么呢？如果某一台计算机没有安装物理硬件网卡，但安装了Tomcat后想访问Tomcat，就可以使用地址localhost或127.0.0.1进行访问。这里的localhost和127.0.0.1就是回调/回环地址，这时回调地址的作用就体现出来了：没有网卡，使用回调/回环地址就能访问Tomcat。

localhost和127.0.0.1是有区别的。其中localhost只是一个域名，只有把域名localhost解析为127.0.0.1，才能进行数据传输与通信，这个解析的过程是由hosts文件完成的。

### 单播、广播、多播

#### 单播

单播大多数都是点对点式的网络，如打开网页、发送邮件和两人网络聊天等情况，都是在使用点对点方式传播数据。

#### 广播

广播是一种一对多的形式，是对网络中所有的计算机发送数据，不区分目标，这就极易造成网络中存在大量无用的垃圾通信数据，造成“广播风暴”，使网络变慢，严重时网络会彻底瘫痪。

#### 多播

多播也称为组播，它也是一种一对多的网络。在网络中，多播一般通过多播IP地址实现，多播IP地址就是D类IP地址，即244.0.0.0 ~ 239.255.255.255 之间的IP地址。