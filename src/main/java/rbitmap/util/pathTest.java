package rbitmap.util;

import org.roaringbitmap.buffer.MutableRoaringBitmap;

import junit.framework.TestCase;

public class pathTest extends TestCase {
	public void testParse() {
		
		String path = "/Users/kevin/test/uid.bitmap";
		System.out.println(path.matches("^/.*"));
		System.out.println(entry.parsePath(path));
		String a = path.replaceFirst("[^\\\\/]+$", "");
		System.out.println(path.replaceFirst("^.*(/|\\\\)([^\\\\/]+$)", "$2"));
		System.out.println(System.getProperty("user.dir"));
	}
	
	public void testBitmap()
	{
		MutableRoaringBitmap b1 = MutableRoaringBitmap.bitmapOf(1,2,5,4);
		MutableRoaringBitmap b2 = MutableRoaringBitmap.bitmapOf(1,2,3);
		b1.andNot(b2);
		b1.
		System.out.println(b1);
	}
}
