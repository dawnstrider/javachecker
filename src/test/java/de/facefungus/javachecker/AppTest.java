package de.facefungus.javachecker;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {

	@Test
	public void testApplication() throws URISyntaxException {

		JavaChecker ap = new JavaChecker();
		Arguments args = new Arguments();
		URL res = getClass().getClassLoader().getResource("cfsclient.jar");
		File f = new File(res.toURI());
		args.setJarFile(f);
		HashMap<Integer, ArrayList<String>> fm = ap.parseJars(args);
		Assert.assertNull("Test jar does not contain classes compiled for 1.1",
				fm.get(1));
		Assert.assertNull("Test jar does not contain classes compiled for 1.2",
				fm.get(2));
		Assert.assertNull("Test jar does not contain classes compiled for 1.3",
				fm.get(3));
		Assert.assertNull("Test jar does not contain classes compiled for 1.4",
				fm.get(4));
		Assert.assertTrue(fm.get(5).contains(f.getAbsolutePath()));
		Assert.assertTrue(fm.get(6).contains(f.getAbsolutePath()));
		Assert.assertTrue(fm.get(7).contains(f.getAbsolutePath()));
	}

}
