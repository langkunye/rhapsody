package main.com.ts.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 会社情報を格納するDTO
 * 
 */
@Data
public class CompanyDto {
  // 給与の支払者の名称（氏名）
  private String name;
  // 給与の支払者の法人（個人）番号
  private String corporationNumber;
  // 郵便番号
  private String postNumber;
  // 給与の支払者の所在地（住所）
  private String address;
  // 社員リスト
  private List<EmployeeDto> employeeList = new ArrayList<EmployeeDto>();
}
