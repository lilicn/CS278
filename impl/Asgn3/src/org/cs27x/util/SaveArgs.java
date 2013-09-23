package org.cs27x.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

/**
 * This class is used to save root directory into a file named args.txt
 * 
 * @author Li
 * 
 */
public class SaveArgs {
	public SaveArgs(String server, String client) throws IOException {
		Path serverPath = Paths.get(server);
		Path clientPath = Paths.get(client);
		checkDirExist(serverPath);
		checkDirExist(clientPath);
		saveToFile(server, client);
	}

	public void saveToFile(String server, String client) throws IOException {
		File file = new File("args.txt");
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		String textToSave = server + ";" + client;
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		out.write(textToSave);
		out.close();
	}

	public void checkDirExist(Path path) throws IOException {
		if (!Files.exists(path)) {
			FileUtils.forceMkdir(path.toFile());
		} else
			FileUtils.cleanDirectory(path.toFile());
	}

	public static void main(String args[]) throws IOException {
		String arg1 = "test-data/server/";
		String arg2 = "test-data/client/";
		if (args.length > 0) {
			arg1 = args[0];
		}
		if (args.length > 1) {
			arg2 = args[1];
		}
		new SaveArgs(arg1, arg2);
	}
}
