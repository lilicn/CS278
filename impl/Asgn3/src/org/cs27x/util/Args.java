package org.cs27x.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is used to get DEFAULTRESPONSETIME and root directory
 * 
 * @author Li
 * 
 */
public class Args {
	private static Args instance;
	public final static int DEFAULTRESPONSETIME = 150;
	private static String[] strs = { "test-data/server/", "test-data/client/" };

	private Args() {
	};

	public static String[] getArgs() throws IOException {
		if (instance == null) {
			synchronized (Args.class) {
				if (instance == null) {
					instance = new Args();
					readFile();
				}
			}
		}
		return strs;
	}

	private static void readFile() throws IOException {
		File file = new File("args.txt");
		if (file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			if (line != null) {
				strs = line.split(";");
			}
		}
	}
}
