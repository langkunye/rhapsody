package main.com.ts.service;

import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class YearEndAdjustmentService {
	public void setCompany() throws IOException {
		// 会社CSVのパスを指定する

		String comCsvPath = Resources.toString(Resources.getResource("resources/company/test1.csv"), Charsets.UTF_8);
	}
}
