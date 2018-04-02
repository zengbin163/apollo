package com.haitao.apollo.util.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyUtil {

	public static String copyFile(String imgType, File file, String path, String newFileName) throws IOException {
		// path的值图片存放文件夹
		File dir = new File(path);
		if (!dir.exists() && !dir.isDirectory()) {
			// 目录不存在，创建目录
			dir.mkdirs();
		}
		String newFilePath = path + "/" + newFileName + "." + imgType;
		FileInputStream in = new FileInputStream(file);
		FileOutputStream out = new FileOutputStream(newFilePath);
		int length = 2097152;
		byte[] buffer = new byte[length];
		while (true) {
			int ins = in.read(buffer);
			if (ins == -1) {
				in.close();
				out.flush();
				out.close();
				return newFilePath;
			} else {
				out.write(buffer, 0, ins);
			}
		}
	}
}
