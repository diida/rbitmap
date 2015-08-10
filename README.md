rbitmap
=======
This is a wrapper around https://github.com/lemire/RoaringBitmap

这是一个基于 https://github.com/lemire/RoaringBitmap 的命令行工具

Usage:
------
```java
	//read bitmap;读取bitmap
	./sbin/read test/uid.bitmap 3
```

Read from bitmap binary file limit 3.
Note:It will print all data without limit.

从bitmap2进制文件中读取3行数据，注意：如果不带limit 参数，就会读取全部数据，那可能非常庞大。

```java
	//convent to bitmap;将文件转换为bitmap
	./sbin/fileToBitmap uid.txt uid.bitmap
	//convent to file;将二进制bitmap转化为可读的文本
	./sbin/bitmapToFile uid.bitmap uid.txt.bak
```

uid.txt必须是每行一个整型数据

```java
	//and or xor;
	./sbin/and uid.bitmap uid2.bitmap uid3.bitmap
```

将uid.bitmap 和uid2.bitmap合并，并写入到uid3.bitmap中
