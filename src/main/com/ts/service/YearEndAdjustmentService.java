package main.com.ts.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import main.com.ts.common.Constant;
import main.com.ts.dto.CompanyDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class YearEndAdjustmentService {
	// 会社情報の設定
	public void writeCompanyInfo() throws IOException, URISyntaxException {
		// 会社CSVのパスを指定する
		URL url = Resources.getResource(Constant.COMPANY_PATH);
		File file = new File(url.toURI());
		CompanyDto companyInfo = null;
		companyInfo = comCsvToBean(file);
		if (companyInfo == null) {
			return;
		}
//		if (CollectionUtils.isEmpty(lines)) {
//			return;
//		}

	}

	/**
	 * 会社情報CSVを取り込みする
	 * 
	 * @param file 会社情報CSVファイル
	 * @return 会社情報obj
	 */
	private CompanyDto comCsvToBean(File file) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8));
			CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(Constant.COMPANY_HEADER).withSkipHeaderRecord();
			CSVParser parse = new CSVParser(br, csvFormat);
			CompanyDto company = new CompanyDto();
			for (CSVRecord line : parse) {
				// 給与の支払者の名称（氏名)
				company.setName(line.get(Constant.COMPANY_HEADER[0]));
				// 給与の支払者の法人（個人）番号
				company.setCorporationNumber(line.get(Constant.COMPANY_HEADER[1]));
				// 郵便番号
				company.setPostNumber(line.get(Constant.COMPANY_HEADER[2]));
				// 給与の支払者の所在地（住所）
				company.setAddress(line.get(Constant.COMPANY_HEADER[3]));
				break;
			}
			return company;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void setCompanyInfo(CompanyDto companyInfo, File file) {
		try {
			PDDocument doc = PDDocument.load(file);
			PDFont font = PDType1Font.HELVETICA_BOLD;
			PDPage page = doc.getPage(0);
			PDPageContentStream pdfSteam = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND,
					false);
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
