package main.com.ts.dto;

import lombok.Data;

/**
 * 社員情報DTO
 *
 */
@Data
public class EmployeeDto {
  // 氏名
  private String name;
  // フリガナ
  private String furigana;
  // 生年月日(yyyy/MM/dd)
  private String birthday;
  // 個人番号
  private String myNum;
  // 郵便番号
  private String postNum;
  // 住所
  private String address;
  // 世代主の氏名
  private String headFamilyName;
  // 世代主との続柄
  private String headFamilyRelationship;
  // 配偶者の有無(有・無)
  private boolean hasSpouse;
}
