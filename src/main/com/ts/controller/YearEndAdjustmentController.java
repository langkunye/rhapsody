package main.com.ts.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import main.com.ts.common.PDFParser;
import main.com.ts.common.YearEndAdjustmentType;

import main.com.ts.dto.CompanyDto;
import main.com.ts.service.YearEndAdjustmentService;

/**
 * 年末調整コントローラー
 *
 */
public class YearEndAdjustmentController {

	public static void main(String[] args) {
		YearEndAdjustmentController action = new YearEndAdjustmentController();
		// 引数をチェックする
		if (action.checkArgs(args) == false) {
			return;
		}
		YearEndAdjustmentService service = new YearEndAdjustmentService();
		try {
			service.writeCompanyInfo();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//		// 入力内容により、出力内容を実装
//		String csvFilePath = "";
//		CompanyDto company = new CompanyDto();
		// System.out.println(company.getPostNumber());
		// company.
//		// para check
//		if (args.length == 1) {
//			csvFilePath = args[0];
//			System.out.println("csvFilePath=" + ch);
//		} else {
//			// para error
//			System.out.println("incorrect number of parameter:" + args.length);
//			return;
//		}

//		// parse CSV
//		String[][] csvData = PDFParser.parse(csvFilePath);
//		if (csvData.length < 4) {
//			System.out.println("csv data is invalid. csvData.length=" + csvData.length);
//			return;
//		}
//
//		String inpuPdfPath = csvData[0][0];
//		String outputDir = csvData[1][0];
//		String fontPath = csvData[2][0];
//		String[] commonInfo = csvData[3];
//		List<StringInfo> strInfoListCompany = null;
//		StringInfo info = null;
//
//		if (!commonInfo[0].equals("CMN") || (commonInfo.length - 1) % 4 != 0) {
//			System.out.println("csv data is invalid(Company). csvData=" + commonInfo[0]);
//			return;
//		} else {
//			// strInfoListCompany
//			strInfoListCompany = new ArrayList<StringInfo>();
//			int count = (commonInfo.length - 1) / 4;
//			for (int i = 0; i < count; i++) {
//				// System.out.println("commonInfo[1 + i * 4]=" + commonInfo[1 + i * 4]);
//				// System.out.println("commonInfo[1 + i * 4 + 1]=" + commonInfo[1 + i * 4 + 1]);
//				// System.out.println("commonInfo[1 + i * 4 + 2]=" + commonInfo[1 + i * 4 + 2]);
//				// System.out.println("commonInfo[1 + i * 4 + 3]=" + commonInfo[1 + i * 4 + 3]);
//
//				info = new StringInfo();
//				info.str = commonInfo[1 + i * 4];
//				info.x = Float.parseFloat(commonInfo[1 + i * 4 + 1]);
//				info.y = Float.parseFloat(commonInfo[1 + i * 4 + 2]);
//				info.fontSize = Float.parseFloat(commonInfo[1 + i * 4 + 3]);
//				strInfoListCompany.add(info);
//				System.out.println("info.str-" + i + ":" + info.str);
//				System.out.println("info.x-" + i + ":" + info.x);
//				System.out.println("info.y-" + i + ":" + info.y);
//				System.out.println("info.fontSize-" + i + ":" + info.fontSize);
//			}
//		}
//
//		List<StringInfo> strInfoListPerson = null;
//		for (int i = 4; i < csvData.length; i++) {
//			strInfoListPerson = null;
//
//			if (csvData[i][0].equals("CMN") || (csvData[i].length - 1) % 4 != 0) {
//				System.out.println("csv data is invalid(Person). csvData=" + csvData[i]);
//				return;
//			} else {
//				int count = (csvData[i].length - 1) / 4;
//				strInfoListPerson = new ArrayList<StringInfo>();
//				for (int j = 0; j < count; j++) {
//					info = new StringInfo();
//					info.str = csvData[i][1 + j * 4];
//					info.x = Float.parseFloat(csvData[i][1 + j * 4 + 1]);
//					info.y = Float.parseFloat(csvData[i][1 + j * 4 + 2]);
//					info.fontSize = Float.parseFloat(csvData[i][1 + j * 4 + 3]);
//					strInfoListPerson.add(info);
//					System.out.println("info.str-" + j + ":" + info.str);
//					System.out.println("info.x-" + j + ":" + info.x);
//					System.out.println("info.y-" + j + ":" + info.y);
//					System.out.println("info.fontSize-" + j + ":" + info.fontSize);
//				}
//			}
//
//			try {
//				PDDocument doc = PDDocument.load(new File(inpuPdfPath));
//				// PDFont font = PDType0Font.load(doc, new
//				// File("C:/Windows/Fonts/MSPRGOT.TTF"));
//				PDFont font = PDType0Font.load(doc, new File(fontPath));
//				PDPage page = doc.getPage(0);
//				PDPageContentStream pdfSteam = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND,
//						false);
//				// strInfoListCompany
//				YearEndAdjustmentController.printLine(doc, pdfSteam, strInfoListCompany, font);
//				pdfSteam.close();
//				doc.setAllSecurityToBeRemoved(true);
//				// TODO put key into file name
//				doc.save(outputDir + "/" + csvData[i][0] + "_" + new File(inpuPdfPath).getName());
//				doc.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	// insert line
//	private static void printLine(PDDocument doc, PDPageContentStream stream, List<StringInfo> strInfoList, PDFont font)
//			throws IOException {
//		// PDRectangle rectangle = PDRectangle.A4;
//		// System.out.println("rectangle.getWidth()" + rectangle.getWidth());
//		// System.out.println("rectangle.getHeight()" + rectangle.getHeight());
//
//		stream.beginText();
//
//		strInfoList.forEach(info -> {
//			try {
//				stream.setFont(font, info.fontSize);
//				stream.moveTextPositionByAmount(info.x, info.y);
//				// stream.moveTo(info.x, info.y);
//				// stream.newLineAtOffset(200, 550 - fontSize);
//				// stream.newLineAtOffset(info.x, info.y);
//				stream.showText(info.str);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
//		stream.endText();
//	}
//
//	// inner class: String information
//	static class StringInfo {
//		String str;
//		float x;
//		float y;
//		float fontSize;
//	}

	/**
	 * 入力した引数を確認
	 * 
	 * @return boolean
	 */
	private boolean checkArgs(String[] args) {
		if (args.length == 2) {
			return true;
		}
		// フォーマットチェック YYYYMMDD
		//
		return false;
	}

}
