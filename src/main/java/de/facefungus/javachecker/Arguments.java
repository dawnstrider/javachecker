package de.facefungus.javachecker;

import java.io.File;

public class Arguments {
	
	private File jarFile;
	
	private File jarDir;

	public File getJarDir() {
		return jarDir;
	}

	public void setJarDir(File jarDir) {
		this.jarDir = jarDir;
	}
	
	public boolean isDirectory() {
		return jarDir != null;
	}

	public File getJarFile() {
		return jarFile;
	}

	public void setJarFile(File jarFile) {
		this.jarFile = jarFile;
	}
	
	public boolean isJarFile() {
		return jarFile != null;
	}

}
