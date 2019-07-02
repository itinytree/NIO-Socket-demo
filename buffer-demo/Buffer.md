### Buffer缓冲区介绍
NIO中的Buffer是一个用于存储基本数据类型值的容器,它以类似于数组有序的方式来存储和组织数据.

在NIO技术的缓冲区中,存在4个核心技术点,分别是:
+ capacity(容量)
+ limit(限制)
+ position(位置)
+ mark(标记)

这4个技术点之间值的大小关系:

0 <= mark <= position <= limit <= capacity

缓冲区的*capacity*,它代表包含元素的数量.缓冲区的capacity不能为负数,并且capacity也不能更改.  

缓冲区的*limit*,它代表第一个不应该读取或写入元素的index(索引).缓冲区的限制(limit)不能为负,并且limit不能大于其capacity.如果position大于新的limit,则将position设置为新的limit.如果mark已定义且大于新的limit,则丢弃该mark.

缓冲区的*position*,它代表"下一个"要读取或写入元素的index(索引),缓冲区的position(位置)不能为负,并且position不能大于其limit.如果mark已定义且大于新的position,则丢弃该mark.

### 方法

* int position(): 返回此缓冲区的位置.
* Buffer position(int newPosition): 设置此缓冲区新的位置.
* int remaining(): 返回"当前位置"与limit之间的元素数.
* Buffer mark(): 在此缓冲区的位置设置标记.缓冲区的标记是一个索引,在调用reset()方法时,会将缓冲区的position位置重置为该索引.
* boolean isReadOnly(): 告知此缓冲区是否为只读缓冲区.
* boolean isDirect(): 判断此缓冲区是否为直接缓冲区.
* final Buffer clear(): **还原缓冲区到初始的状态**,包含将位置设置为0,将限制设置为容量,并丢弃标记,即"一切为默认".并"不能真正清除"缓冲区中的数据,而是使数据处于一种遗忘状态.
* final Buffer flip(): 反转此缓冲区.首先将限制设置为当前位置,然后将位置设置为0.如果已定义了标记,则丢弃该标记.
* final boolean hasArray(): 判断此缓冲区是否具有可访问的底层实现数组.
* final boolean hasRemaining(): 判断在当前位置和限制之间是否有元素.
* final Buffer rewind(): 重绕此缓冲区,将位置设置为0并丢弃标记.rewin()方法的通俗解释就是"**标记清除**,位置position值归0,limit不变".**注意**:rewind()方法的作用,通常在重新读取缓冲区中数据时使用.
* final int arrayOffset(): 返回此缓冲区的底层实现数组中第一个缓冲区元素的偏移量,这个值在文档中标注为"可选操作",也就是子类可以不处理这个值.

#### rewind()、clear()、flip()方法的比较

* rewind()：使缓冲区为“重新读取”已包含的数据做好准备，它使限制保持不变，将位置设置为 0.
* clear()：使缓冲区为一系列新的通道读取或相对 put(value) 操作做好准备，即它将限制设置为容量大小，将位置设置为 0.
* flip()：使缓冲区为一系列新的通道写入或相对 get(value) 操作做好准备，即它将限制设置为当前位置，然后将位置设置为 0.

这三个方法侧重点在于：

1. rewind()：侧重点在“重新”，在重新读取、重新写入时可以使用；
2. clear()：侧重点在“还愿一切状态”；
3. flip()：侧重点在 substring 截取。

### ByteBuffer

ByteBuffer 类是 Buffer 类的子类，可以在缓冲区中以字节为单位对数据进行存取，而且它也是比较常用和重要的缓冲区类。

#### 直接缓冲区

1. 非直接缓冲区

   通过 ByteBuffer 向磁盘存取数据时是需要将数据暂存在JVM的中间缓冲区,如果有频繁操作数据的情况发生,则在每次操作时都会将数据暂存在JVM的中间缓冲区,再交给ByteBuffer处理,这样做就大大降低软件对数据的吞吐量,提高内存占有率,造成软件运行效率降低,这就是非直接缓冲区保存数据的过程.

2. 直接缓冲区

   JVM 会尽量在直接缓冲区上执行本机 I/O 操作，也就是**直接对内核空间**进行访问，以提高运行效率。提高运行效率的原理就是在每次调用基于操作系统的 I/O 操作之前或之后，JVM 都会尽量避免将缓冲区的内容复制到中间缓冲区中，或者从中间缓冲区中复制内容，这样就节省了一个步骤。
   
   通过工厂方法`allocateDirect()`返回的缓冲区进行内存的分配和释放所需要的时间成本要高于非直接缓冲区。直接缓冲区操作的数据**不在JVM堆中**，而是在内核空间中，根据这个结构可以分析出，直接缓冲区善于保存那些**易受操作系统本机 I/O 操作影响的大量、长时间保存**的数据。
   
   使用直接缓冲区来实现两端数据交互,则直接在内核空间中就进行了处理,无须JVM创建新的缓冲区,这样就减少了再JVM中创建中间缓冲区的步骤,增加了程序运行效率.

#### 方法

+ allocateDirect(int capacity)：分配新的直接缓字节缓冲区。新缓冲区的位置将为 0，其界限将为其容量，其标记是不确定的。无论它是否具有底层实现数组，其标记都是不确定的。创建出来的缓冲区类型为`DirectByteBuffer`。使用 `allocateDirect()`方法创建 ByteBuffer 缓冲区时，capacity 指的是**字节**的个数，而创建 IntBuffer 缓冲区时，capacity 指的是 **int**值的数目，如果要转换成字节，则 capacity 的值要乘以 4，来算出占用的总字节数。

+ allocate(int capacity)：分配一个非直接字节缓冲区。新缓冲区的位置将为 0，其界限将为其容量，其标记是不确定的。它将具有一个**底层实现数组**，且数据偏移量将为 0。创建出来的缓冲区类型为`HeapByteBuffer`。

* wrap(byte[] array): 将byte数组包装到缓冲区中。新的缓冲区将由给定的byte数组支持，也就是说，缓冲区修改导致数组修改，反之亦然。
* wrap(byte[] array, int offset, int length): 将byte数组包装到缓冲区中。新的缓冲区将由给定的byte数组支持，也就是说，缓冲区修改将导致数组修改，反之亦然。新缓冲区的capacity将为array.length,其position将为offset，其limit将为offset+length,其标记是不确定的。
* put(byte[] src, int offset, int length)：相对批量put方法，此方法将把给定源数组中的字节传输到此缓冲区当前位置中。
* get(byte[] dst, int offset, int length): 相对批量get方法，此方法将此缓冲区当前位置的字节传输到给定的目标数组中。
* put(int index, byte b): 绝对put方法，将给定字节写入此缓冲区的给定索引位置。position不变。
* get(int index): 绝对get方法，读取指定位置索引处的字节。position不变。
* put(ByteBuffer src): 相对批量put方法，此方法将给定源缓冲区中的剩余字节传输到此缓冲区的当前位置中。
* slice(): 创建新的字节缓冲区，其内容是此缓冲区内容的**共享子序列**。新缓冲区的内容将从此缓冲区的当前位置开始。此缓冲区内容的更改在新缓冲区中是可见的，反之亦然；这两个缓冲区的位置、限制和标记时相互独立的。
* asReadOnlyBuffer(): 创建共享此缓冲区内容的新的只读字节缓冲区。新缓冲区的内容将为此缓冲区的内容。
* compact(): 压缩此缓冲区(可选操作)，将缓冲区的当前位置和限制之间的字节(如果有)复制到缓冲区的开始处，即将索引p=position()处的字节复制到索引0处，将索引p+1处的字节复制到索引1处，依此类推，直到将索引limit()-1处的字节复制到索引n=limit()-1-p处。然后，将缓冲区的位置设置为n+1，并将其限制设置为其容量。如果已定义了标记，则丢弃它。

