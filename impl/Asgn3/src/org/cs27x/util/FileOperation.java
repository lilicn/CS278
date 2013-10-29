package org.cs27x.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

/**
 * This class is used to comapare files or directory
 * 
 * @author Li
 * 
 */
public class FileOperation {

	public static boolean compareInDir(File f1, File f2) throws IOException {
		File[] fileList1 = f1.listFiles();
		File[] fileList2 = f2.listFiles();
		if (fileList1.length != fileList2.length)
			return false;
		Arrays.sort(fileList1);
		Arrays.sort(fileList2);
		for (int i = fileList1.length - 1; i >= 0; i--) {
			if (fileList1[i].isFile() != fileList2[i].isFile())
				return false;
			if (fileList1[i].isFile()) {
				if (!compareFile(fileList1[i], fileList2[i])) {
					System.out.println(fileList1[i].getName() + ","
							+ fileList2[i].getName());
					return false;
				}

			} else {
				if (!compareDir(fileList1[i], fileList2[i]))
					return false;

			}

		}
		return true;
	}

	public static boolean compareFile(File f1, File f2) throws IOException {
		if (!f1.getName().equals(f2.getName())
				|| !FileUtils.contentEquals(f1, f2))
			return false;

		if (!f1.isDirectory()) {
			return FileUtils.readFileToString(f1, "utf-8").equals(
					FileUtils.readFileToString(f2, "utf-8"));
		}
		return true;
	}

	public static boolean compareDir(File f1, File f2) throws IOException {
		if (!f1.getName().equals(f2.getName()))
			return false;
		return compareInDir(f1, f2);
	}

}
