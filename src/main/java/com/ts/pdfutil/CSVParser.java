package com.ts.pdfutil;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.regex.Pattern;
import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CSVParser {

	public static String[][] parse(String filename) {
		String[][] ret = null;
		ArrayList<String[]> tmp = new ArrayList<String[]>();
		String line;
		String[] row;
		int i = 0;
		Pattern pattern = Pattern.compile("^\".*\"$");

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));

			while ((line = br.readLine()) != null) {
				row = line.split(",(?=(([^\"]*\"){2})*[^\"]*$)", -1);
				for (i = 0; i < row.length; i++) {
					if (pattern.matcher(row[i]).find()) {
						row[i] = row[i].substring(1, row[i].length() - 1).replace("\"\"", "\"");
						// row[ i ] = row[ i ].substring( 1, row[ i ].length() - 1 );
						// System.out.println( row[ i ] );

					}
				}
				tmp.add(row);
			}

			ret = new String[tmp.size()][];
			for (i = 0; i < tmp.size(); i++) {
				ret[i] = tmp.get(i);
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}
}