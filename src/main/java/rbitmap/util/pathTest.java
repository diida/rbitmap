package rbitmap.util;

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
}
