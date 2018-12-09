package main.com.ts.common;

/**
 * 定数クラス
 */
public class Constant {
	// 会社情報のHEADER
	public static final String[] COMPANY_HEADER = new String[] { "給与の支払者の名称（氏名)", "給与の支払者の法人（個人）番号", "郵便番号",
			"給与の支払者の所在地（住所）" };
	/** パス変数群 **/
	// 取込用PDFファイルの基本パス
	public static final String READ_PDF_BASE_PATH = "resources/pdf/";
	// TTFファイルパス:XANO明朝U32
	public static final String XANO_MINCHO_U32 = "resources/font/XANO-mincho-U32.ttf";
	public static final String MSPRGOT = "resources/font/MSPRGOT.TTF";
	// 会社情報CSVパス
	public static final String COMPANY_PATH = "resources/company/company.csv";

}
