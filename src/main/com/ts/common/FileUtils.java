package main.com.ts.common;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import main.com.ts.dto.PrintInfoDto;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import com.google.common.io.Resources;

public class FileUtils {
  public static final String PDF = ".PDF";
  public static final String SLASH = "/";

  public static void printListToPDF(List<PrintInfoDto> printList, File pdfFile, File fontFile,
      String outPutPath) throws InvalidPasswordException, IOException {
    // PDFファイルをロード
    PDDocument doc = PDDocument.load(pdfFile);
    // 字体ファイルをロード
    PDFont font = PDType0Font.load(doc, fontFile);
    // 書き込対象ページを設定
    PDPage page = doc.getPage(0);
    PDPageContentStream contentStream =
        new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);
    // 内容を書き込み開始
    contentStream.beginText();
    printList.forEach(info -> {
      try {
        // 字体を設定
        contentStream.setFont(font, info.getFontSize());
        // 文字の位置を設定
        contentStream.newLineAtOffset(info.getX(), info.getY());
        // 文字の内容を設定
        contentStream.showText(info.getContent());
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException();
      }
    });
    // 内容を書き込み終了
    contentStream.endText();
    contentStream.close();
    doc.save(outPutPath);
    doc.close();

  }

  /**
   * クラスパスを元にファイルを取得
   * 
   * @param classpath 対象ファイルのクラスパス
   * @return 対象ファイル
   * @throws URISyntaxException
   */
  public static File getFileByClasspath(String classpath) throws URISyntaxException {
    return new File(Resources.getResource(classpath).toURI());
  }
}
