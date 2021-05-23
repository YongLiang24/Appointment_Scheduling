package model;

/**
 *
 * @author yongl
 */
public class MonthTypeReport {
    private String monthType;
    private int count;

    public MonthTypeReport(String monthType, int count) {
        this.monthType = monthType;
        this.count = count;
    }

    public String getMonthType() {
        return monthType;
    }

    public int getCount() {
        return count;
    }
    
}
