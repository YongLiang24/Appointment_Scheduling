package model;

/**
 *
 * @author yongl
 */
public class CountryCustomerReport {
    private String country;
    private String division;
    private int customer_count;

    public CountryCustomerReport(String country, String division, int customer_count) {
        this.country = country;
        this.division = division;
        this.customer_count = customer_count;
    }

    public String getCountry() {
        return country;
    }

    public String getDivision() {
        return division;
    }

    public int getCustomer_count() {
        return customer_count;
    }
    
    
}
