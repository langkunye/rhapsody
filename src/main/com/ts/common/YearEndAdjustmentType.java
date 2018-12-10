package main.com.ts.common;

/**
 * 年末調整種別
 */
public enum YearEndAdjustmentType {
  insurance("保険料控除申告書"), support("扶養控除申告書"), spouse("配偶者控除等申告書"),;
  private final String text;

  private YearEndAdjustmentType(final String text) {
    this.text = text;
  }

  public String getString() {
    return this.text;
  }
}
