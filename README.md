rbitmap
=======
This is a wrapper around https://github.com/lemire/RoaringBitmap

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
```

uid.txt必须是每行一个整型数据
