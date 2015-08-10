package rbitmap.util;

import junit.framework.TestCase;

public class pathTest extends TestCase {
	public void testParse() {
		String path = "/tmp/20123/32.b";
		String a = path.replaceFirst("[^\\\\/]+$", "");
		System.out.println(path.replaceFirst("^.*(/|\\\\)([^\\\\/]+$)", "$2"));
		System.out.println(System.getProperty("user.dir"));
	}
}
