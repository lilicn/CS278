package org.cs27x.util;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * This class is used to create Drobox server/client process
 * 
 * @author Li
 * 
 */
public class DropboxProcess {
	public static Process createProcess(String dir, boolean isServer,
			String logFile) throws IOException {

		ProcessBuilder pb;
		if (isServer) {
			pb = new ProcessBuilder("java", "-jar", "Dropbox.jar", dir);
		} else {
			String IP = getIP().getHostAddress();
			pb = new ProcessBuilder("java", "-jar", "Dropbox.jar", dir, IP);
		}

		File log = new File(logFile);
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(log));
		return pb.start();
	}

	private static InetAddress getIP() throws UnknownHostException,
			SocketException {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			NetworkInterface ni = NetworkInterface.getByName("eth0");

			Enumeration<InetAddress> ias = ni.getInetAddresses();

			InetAddress iaddress;
			do {
				iaddress = ias.nextElement();
			} while (!(iaddress instanceof Inet4Address));

			return iaddress;
		}

		return InetAddress.getLocalHost();
	}
}
