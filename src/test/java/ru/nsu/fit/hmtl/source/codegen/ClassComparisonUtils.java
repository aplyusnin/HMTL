package ru.nsu.fit.hmtl.source.codegen;

import java.io.*;
import java.util.Scanner;

public class ClassComparisonUtils {

	public static boolean compareFiles(String file1, String file2) {
		try {
			File f1 = new File(file1);
			File f2 = new File(file2);

			Scanner sc1 = new Scanner(new BufferedReader(new FileReader(f1)));
			Scanner sc2 = new Scanner(new BufferedReader(new FileReader(f2)));

			while (sc1.hasNext() || sc2.hasNext()) {
				if (sc1.hasNext() != sc2.hasNext()) {
					return false;
				}
				String v1 = sc1.next();
				String v2 = sc2.next();
				if (!v1.equals(v2))
					return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
