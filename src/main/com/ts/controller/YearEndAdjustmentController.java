package main.com.ts.controller;

import java.io.IOException;
import java.net.URISyntaxException;
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
      service.writeCompanyInfo("2018", "/Users/langkunye/git/rhapsody/pdf/h30_給与所得者の保険料控除申告書.pdf");
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

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
