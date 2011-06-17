package org.lion.rs2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.lion.Server;
import org.lion.rs2.util.Logger.Level;

public class Configuration {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("Configuration");

	/**
	 * Loads the Server configuration file.
	 */
	public static void loadServerConfig() {
		logger.log(Logger.Level.NORMAL, "Loading server configuration...");
		String[] s = { "Name", "Host", "Port", "World", "Revision" };
		try {
			long start = System.currentTimeMillis();
			Object[] property = getProperty("./data/configuration/Server.ini", s);
			if (property != null) {
				Server.setName((String) property[0]);;
				Server.setPort(Integer.parseInt((String) property[2]));
				Server.setRevision(Integer.parseInt((String) property[4]));
			}
			logger.log(Level.NORMAL, "Server configuration loaded in " + Timer.formatSmallTime(System.currentTimeMillis() - start) + ".");
		} catch (FileNotFoundException e) {
			logger.log(Level.ERROR, e);
		}
	}

	/**
	 * Gets information from the Configuration files.
	 * Loops for loading more than one piece of information.
	 * 
	 * @param name The name to search for.
	 * @throws FileNotFoundException if file is not found.
	 * @return The object of the designated name.
	 */
	public static Object[] getProperty(final String directory, final String... name) throws FileNotFoundException {
		try {
			Object[] returnObject = new Object[name.length];
			File f = new File(directory);
			if (!f.exists()) {
				throw new FileNotFoundException(f.getName() + " doesn't exist!");
			}
			Properties p = new Properties();
			p.load(new FileInputStream(directory));
			for (int i = 0; i < name.length; i++) {
				returnObject[i] = p.getProperty(name[i]).trim();
			}
			return returnObject;
		} catch (FileNotFoundException e) {
			logger.log(Level.ERROR, e);
		} catch (IOException e) {
			logger.log(Level.ERROR, e);
		}
		return null;
	}

}
