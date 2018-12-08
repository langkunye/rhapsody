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
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class YearEndAdjustmentService {
	// 会社情報の設定
	public void writeCompanyInfo() throws IOException, URISyntaxException {
		// 会社CSVのパスを指定する
		URL comUrl = Resources.getResource(Constant.COMPANY_PATH);
		File comFile = new File(comUrl.toURI());

		URL fontUrl = Resources.getResource(Constant.XANO_MINCHO_U32);
		File fontFile = new File(fontUrl.toURI());
		CompanyDto companyInfo = null;
		companyInfo = comCsvToBean(comFile);
		if (companyInfo == null) {
			return;
		}
		URL pdfUrl = Resources.getResource(Constant.READ_PDF_BASE_PATH + "2018/h30_給与所得者の保険料控除申告書.pdf");
		File pdfFile = new File(pdfUrl.toURI());

		setCompanyInfo(companyInfo, pdfFile, fontFile);
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

	private void setCompanyInfo(CompanyDto companyInfo, File pdfFile, File fontFile) {
		try {
			PDDocument doc = PDDocument.load(pdfFile);
			PDFont font = PDType0Font.load(doc, fontFile);
			PDPage page = doc.getPage(0);
			PDPageContentStream contentStream = new PDPageContentStream(doc, page,
					PDPageContentStream.AppendMode.APPEND, false);
			// 内容を書き込み
			contentStream.beginText();
			// TODO 会社名称の長さにより、字体を調整するように
			contentStream.setFont(font, 10);
			contentStream.newLineAtOffset(170, 525);
			contentStream.showText(companyInfo.getName());

			contentStream.endText();
			contentStream.close();
			// TODO put key into file name
			doc.save("/Users/langkunye/git/rhapsody/pdf/h30_給与所得者の保険料控除申告書.pdf");
			doc.close();
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
