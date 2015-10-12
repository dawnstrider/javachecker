package de.facefungus.javachecker;

import java.io.DataInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

	private static final int CAFEBABE = -889275714;

	private final int version_base = 44;

	private HashMap<Integer, ArrayList<String>> fileMap = new HashMap<Integer, ArrayList<String>>();

	private boolean verbose = false;

	private Logger log = LogManager.getFormatterLogger();

	/**
	 * Starts the application, expecting the argument(s) to be correct. If the
	 * arguments are incorrect, the application exists with exit level -1.
	 * Otherwise the jars are scanned and the result is printed to system out.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		App a = new App();
		Arguments arg = a.parseArguments(args);
		HashMap<Integer, ArrayList<String>> fm = a.parseJars(arg);
		a.printFileMap(fm);
	}

	private void printFileMap(HashMap<Integer, ArrayList<String>> fm) {
		Set<Integer> keys = fm.keySet();
		for (Integer integer : keys) {
			ArrayList<String> list = fm.get(integer);
			System.out.println("Java 1." + integer);
			for (String string : list) {
				System.out.println("\t" + string);
			}
		}
	}

	/**
	 * Parses the file or directory in the given arguments.
	 * 
	 * @param arg
	 *            the argument object
	 * @return 
	 */
	public HashMap<Integer, ArrayList<String>> parseJars(Arguments arg) {
		if (arg.isDirectory()) {
			File dir = arg.getJarDir();
			File[] files = dir.listFiles(new FilenameFilter() {

				public boolean accept(File dir, String name) {
					return name.endsWith(".jar")
							&& new File(dir, name).isFile();
				}
			});
			for (int i = 0; i < files.length; i++) {
				try {
					scanJar(files[i]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			try {
				scanJar(arg.getJarFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileMap;
	}

	private void scanJar(File jar) throws IOException {
		JarFile jf = new JarFile(jar);
		try {
			Enumeration<JarEntry> entries = jf.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
					InputStream in = jf.getInputStream(entry);
					DataInputStream dis = new DataInputStream(in);
					try {
						int check = dis.readInt();
						if (check == CAFEBABE) {
							// just read it, we dont need it
							short minVersion = dis.readShort();
							short maxVersion = dis.readShort();
							int highVersion = (maxVersion % version_base);
							ArrayList<String> files = fileMap.get(highVersion);
							if (files == null) {
								files = new ArrayList<String>();
								fileMap.put(highVersion, files);
							}
							if (!files.contains(jf.getName())) {
								files.add(jf.getName());
							}
						}
					} finally {
						dis.close();
						in.close();
					}
				}
			}
		} finally {
			jf.close();
		}
	}

	private Arguments parseArguments(String[] args) {
		if (args == null || args.length != 1) {
			if (verbose)
				log.debug("Invalid arguments");
			printHelp();
			System.exit(-1);
		} else {
			Arguments ret = new Arguments();
			File f = new File(args[0]);
			if (f.isDirectory()) {
				if (verbose)
					log.debug("Given argument is a directory");
				ret.setJarDir(f);
			} else {
				ret.setJarFile(f);
			}
			return ret;
		}
		return null;
	}

	private void printHelp() {
		System.out.println("JavaChecker by HighQSoft GmbH");
		System.out.println();
		System.out
				.println("Checks a given JAR or directory with jars for their supported Java version");
		System.out.println("Usage:");
		System.out
				.println("java -jar path/to/javachecker.jar <jarfile | directory>");
		System.out.println("jarfile - a single jarfile");
		System.out.println("directoy - a directory with jar files in it");

	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

}
