package main.com.ts.common;

/**
 * 定数クラス
 */
public class Constant {
	// 会社情報CSVパス
	public static final String COMPANY_PATH = "resources/company/company.csv";
	// 会社情報のHEADER
	public static String[] COMPANY_HEADER = new String[] { "給与の支払者の名称（氏名)", "給与の支払者の法人（個人）番号", "郵便番号",
			"給与の支払者の所在地（住所）" };
	// PDFファイルの基本パス
	public static final String PDF_BASE_PATH = "resources/pdf/";

}
