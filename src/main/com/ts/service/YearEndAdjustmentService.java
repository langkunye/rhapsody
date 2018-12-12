package main.com.ts.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Charsets;
import main.com.ts.common.ChangeUtils;
import main.com.ts.common.Constant;
import main.com.ts.common.FileUtils;
import main.com.ts.common.YearEndAdjustmentType;
import main.com.ts.dto.CompanyDto;
import main.com.ts.dto.EmployeeDto;
import main.com.ts.dto.PrintInfoDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class YearEndAdjustmentService {
  // 会社情報の設定
  public void writeCompanyInfo(String year, String outputPath)
      throws IOException, URISyntaxException {
    // 会社ファイル(.csv)
    File comFile = FileUtils.getFileByClasspath(Constant.COMPANY_PATH);
    // 社員CSV(.csv)
    File employeeFile = FileUtils.getFileByClasspath(Constant.EMPLOYEE_PATH);
    // 字体ファイル(.ttf)
    File fontFile = FileUtils.getFileByClasspath(Constant.MSPRGOT);
    // PDFファイル(.pdf)
    // 保険料控除申告書
    File pdfFile = FileUtils.getFileByClasspath(Constant.READ_PDF_BASE_PATH + year + FileUtils.SLASH
        + YearEndAdjustmentType.insurance.getString() + FileUtils.PDF);
    List<CompanyDto> companyInfoList = comCsvToBean(comFile);
    List<EmployeeDto> employeeInfoList = employeeCsvToBean(employeeFile);
    // 会社情報が空の場合、戻す
    if (CollectionUtils.isEmpty(companyInfoList)) {
      return;
    }
    List<PrintInfoDto> printInfoList = setPrintInfoList(companyInfoList);
    FileUtils.printListToPDF(printInfoList, pdfFile, fontFile, outputPath);
    // setCompanyInfo(companyInfo, pdfFile, fontFile);
  }

  /**
   * 会社情報CSVを取り込みする
   * 
   * @param file 会社情報CSVファイル
   * @return 会社情報リスト
   */
  private List<CompanyDto> comCsvToBean(File file) {
    try {
      BufferedReader br =
          new BufferedReader(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8));
      CSVFormat csvFormat =
          CSVFormat.DEFAULT.withHeader(Constant.COMPANY_HEADER).withSkipHeaderRecord();
      CSVParser parse = new CSVParser(br, csvFormat);
      CompanyDto company = new CompanyDto();
      List<CompanyDto> companyList = new ArrayList<CompanyDto>();
      for (CSVRecord line : parse) {
        // 給与の支払者の名称（氏名)
        company.setName(line.get(Constant.COMPANY_HEADER[0]));
        // 給与の支払者の法人（個人）番号
        company.setCorporationNumber(line.get(Constant.COMPANY_HEADER[1]));
        // 郵便番号
        company.setPostNumber(line.get(Constant.COMPANY_HEADER[2]));
        // 給与の支払者の所在地（住所）
        company.setAddress(line.get(Constant.COMPANY_HEADER[3]));
        companyList.add(company);
        break;
      }
      return companyList;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<EmployeeDto> employeeCsvToBean(File file) {
    try {
      BufferedReader br =
          new BufferedReader(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8));
      CSVFormat csvFormat =
          CSVFormat.DEFAULT.withHeader(Constant.EMPLOYEE_HEADER).withSkipHeaderRecord();
      CSVParser parse = new CSVParser(br, csvFormat);
      EmployeeDto employee = new EmployeeDto();
      List<EmployeeDto> employeeList = new ArrayList<EmployeeDto>();
      for (CSVRecord line : parse) {
        employee.setName(line.get(Constant.EMPLOYEE_HEADER[0]));
        employeeList.add(employee);
      }
      return employeeList;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 会社出力情報を設定する
   * 
   * @param companyList 会社情報リスト
   * @return 会社出力情報リスト
   */
  private List<PrintInfoDto> setPrintInfoList(List<CompanyDto> companyList) {
    List<PrintInfoDto> printInfoList = new ArrayList<PrintInfoDto>();
    companyList.forEach(company -> {
      // 会社名を設定する
      printInfoList.add(setCompanyName(company));
      // 会社番号を設定する
      printInfoList.addAll(setCorporationNumber(company));
      // 郵便番号を設定する
      printInfoList.add(setCompanyPostNum(company));
      // 会社の住所を設定する
      printInfoList.add(setCompanyAddr(company));
    });

    return printInfoList;
  }

  /**
   * 出力会社名を設定する
   * 
   * @param company 会社情報
   * @return 会社名を出力する情報
   */
  private PrintInfoDto setCompanyName(CompanyDto company) {
    PrintInfoDto printInfoDto = new PrintInfoDto();
    // TODO 会社名称の長さにより、字体を調整するように
    printInfoDto.setContent(company.getName());
    printInfoDto.setFontSize(10);
    printInfoDto.setX(175);
    printInfoDto.setY(525);
    return printInfoDto;
  }

  /**
   * 会社番号印刷情報を設定する TODO 印刷の位置の調整が必要
   * 
   * @param company 会社情報
   * @return 会社番号印刷情報リスト
   */
  private List<PrintInfoDto> setCorporationNumber(CompanyDto company) {
    // x軸
    float x = -20;
    // Y軸
    float y = -32;
    // 番号インデックス
    int numIndex = 0;
    List<PrintInfoDto> printInfoDtoList = new ArrayList<PrintInfoDto>();
    String[] numArray = ChangeUtils.stringToArray(company.getCorporationNumber());
    for (String num : numArray) {
      PrintInfoDto printInfoDto = new PrintInfoDto();
      // 一周後x,y軸の値を変更する
      if (numIndex == 1) {
        x = 19;
        y = 0;
      }
      printInfoDto.setContent(num);
      printInfoDto.setFontSize(10);
      printInfoDto.setX(x);
      printInfoDto.setY(y);
      printInfoDtoList.add(printInfoDto);
      numIndex++;
    }

    return printInfoDtoList;
  }

  /**
   * 郵便番号の印刷情報を設定する
   * 
   * @param company 会社情報
   * @return 印刷情報DTO
   */
  private PrintInfoDto setCompanyPostNum(CompanyDto company) {
    PrintInfoDto printInfoDto = new PrintInfoDto();
    printInfoDto.setContent(company.getPostNumber());
    printInfoDto.setFontSize(8);
    printInfoDto.setX(-230);
    printInfoDto.setY(-12);
    return printInfoDto;
  }

  private PrintInfoDto setCompanyAddr(CompanyDto company) {
    PrintInfoDto printInfoDto = new PrintInfoDto();
    printInfoDto.setContent(company.getAddress());
    printInfoDto.setFontSize(10);
    printInfoDto.setX(0);
    printInfoDto.setY(-10);
    return printInfoDto;
  }
}
