package ru.nsu.fit.hmtl.source.codegen;

import java.io.*;

public class ClassComparisonUtils {

	public static boolean compareFiles(String file1, String file2) {
		try {
			File f1 = new File(file1);
			File f2 = new File(file2);

			BufferedReader sc1 = new BufferedReader(new FileReader(f1));
			BufferedReader sc2 = new BufferedReader(new FileReader(f2));

			while (true) {
				int v1 = sc1.read();
				int v2 = sc2.read();
				if (v1 != v2)
					return false;
				if (v1 == -1) break;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
