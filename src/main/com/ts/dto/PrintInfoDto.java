package main.com.ts.dto;

import lombok.Data;

/**
 * 印刷情報を保存用DTO
 * 
 */
@Data
public class PrintInfoDto {
	// 内容
	private String content;
	// X軸
	private float x;
	// Y軸
	private float y;
	// 文字サイズ
	private float fontSize;
}
