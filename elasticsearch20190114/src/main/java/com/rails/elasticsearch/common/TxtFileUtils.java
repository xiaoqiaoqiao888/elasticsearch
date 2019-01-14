package com.rails.elasticsearch.common;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class TxtFileUtils {

	// public static void writeTxt(String path, byte[] data, boolean append) {
	// File file = new File(path);
	// try {
	// FileUtils.writeByteArrayToFile(file, data, append);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// public static void writeTxt(String path, String data, boolean append) {
	// File file = new File(path);
	// try {
	// FileUtils.writeStringToFile(file, data, "UTF-8", append);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public static void writeTxt(String path, Collection<?> lines, boolean append)
	// {
	// File file = new File(path);
	// try {
	// FileUtils.writeLines(file, lines, append);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	public static String readTxtToString(String path, String encoding) {
		File file = new File(path);
		String data = "";
		try {
			data = FileUtils.readFileToString(file, encoding);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static byte[] readTxtToByte(String path) {
		File file = new File(path);
		byte[] data = null;
		try {
			data = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	public static List<String> readTxtToList(String path, String encoding) {
		File file = new File(path);
		List<String> data = null;
		try {
			data = FileUtils.readLines(file, encoding);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
