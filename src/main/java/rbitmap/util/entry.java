package rbitmap.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.roaringbitmap.buffer.ImmutableRoaringBitmap;
import org.roaringbitmap.buffer.MutableRoaringBitmap;

public class entry {

	private static final int TYPE_AND = 0;
	private static final int TYPE_OR = 1;
	private static final int TYPE_XOR = 2;

	/**
	 * 保存到磁盘上
	 * 
	 * @param path
	 * @param filename
	 * @param Bitmap
	 * @throws Exception
	 */
	public static void save(Map<String, String> pathInfo,
			MutableRoaringBitmap Bitmap) throws Exception {
		String path = pathInfo.get("path");
		String filename = pathInfo.get("filename");
		File tmpfile = new File(filename);
		File pathfile = new File(path);
		pathfile.mkdirs();
		FileOutputStream fos;

		fos = new FileOutputStream(tmpfile);
		Bitmap.serialize(new DataOutputStream(fos));
		fos.close();
	}

	/**
	 * 从磁盘上读取
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static MutableRoaringBitmap read(String filename) throws Exception {
		File tmpfile = new File(filename);
		RandomAccessFile memoryMappedFile;
		memoryMappedFile = new RandomAccessFile(tmpfile, "r");
		long size = tmpfile.length();
		ByteBuffer bb = memoryMappedFile.getChannel().map(
				FileChannel.MapMode.READ_ONLY, 0, size);
		ImmutableRoaringBitmap mapped = new ImmutableRoaringBitmap(bb);
		memoryMappedFile.close();
		return mapped.toMutableRoaringBitmap();
	}

	/**
	 * 将文件路径分割
	 * 
	 * @param path
	 * @return
	 */
	public static Map<String, String> parsePath(String path) {
		if (!path.matches("^/")) {
			path = System.getProperty("user.dir") + "/" + path;
		}
		Map<String, String> pathInfo = new HashMap<String, String>();
		pathInfo.put("path", path.replaceFirst("[^\\\\/]+$", ""));
		pathInfo.put("filename",
				path.replaceFirst("^.*(/|\\\\)([^\\\\/]+$)", "$2"));
		// path.replaceFirst
		return pathInfo;
	}

	/**
	 * 
	 * 文件转bitmap
	 */
	public static MutableRoaringBitmap fileToBitmap(String[] args) {
		FileInputStream fis = null;
		BufferedReader reader = null;
		MutableRoaringBitmap bm = MutableRoaringBitmap.bitmapOf();
		try {
			fis = new FileInputStream(args[1]);
			reader = new BufferedReader(new InputStreamReader(fis));

			String line = reader.readLine();
			while (line != null) {
				bm.add(Integer.parseInt(line));
				line = reader.readLine();
			}

		} catch (Exception ex) {
			return null;
		}

		if (args.length > 2) {
			try {
				Map<String, String> path = entry.parsePath(args[2]);
				entry.save(path, bm);
				return bm;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return bm;
		}
		return null;
	}

	public static void read(String[] args) {
		MutableRoaringBitmap bm;
		try {
			bm = entry.read(args[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return;
		}

		entry.output(bm);
		if (args.length == 3) {
			int limit = Integer.parseInt(args[2]);
			if (limit > 0) {
				bm = bm.limit(limit);
			}
		}
		Iterator iter = bm.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}

	/**
	 * 
	 * bitmap转文件
	 */
	public static void bitmapToFile(String[] args) {
		try {
			MutableRoaringBitmap bm = entry.read(args[1]);

			File file = new File(args[2]);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			Iterator it = bm.iterator();

			while (it.hasNext()) {
				bw.write(String.valueOf(it.next()) + "\n");
			}
			bw.close();

			System.out.println("写入文件：" + args[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void bitmapBool(int type, String[] args) {
		MutableRoaringBitmap b1;
		try {
			b1 = entry.read(args[1]);

			MutableRoaringBitmap b2 = entry.read(args[2]);

			switch (type) {
			case entry.TYPE_AND:
				b1.and(b2);
				break;
			case entry.TYPE_OR:
				b1.or(b2);
				break;
			case entry.TYPE_XOR:
				b1.xor(b2);
				break;
			}

			entry.output(b1);
			if (args.length == 4 && args[3] != null && args[3].length() > 0) {
				entry.save(entry.parsePath(args[3]), b1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void output(MutableRoaringBitmap bm) {
		System.out.println("总数：" + bm.getCardinality());
		System.out.println("体积：" + bm.getSizeInBytes() + "Byte");
	}

	public static void main(String[] args) {
		String method = args[0];
		
		if(method.equals("f2m") || method.equals("fileToBitmap")) {
			entry.fileToBitmap(args);
		} else if(method.equals("m2f") || method.equals("bitmapToFile")) {
			entry.bitmapToFile(args);
		} else if(method.equals("r") || method.equals("read")) {
			entry.read(args);
		} else if(method.equals("and")) {
			entry.bitmapBool(entry.TYPE_AND, args);
		} else if(method.equals("or")) {
			entry.bitmapBool(entry.TYPE_OR, args);
		} else if(method.equals("xor")) {
			entry.bitmapBool(entry.TYPE_XOR, args);
		}
	}
}
