package com.example.c482software1project;

/**
 * class for outsourced parts subclass of parts class.
 *
 * @author Anthony Collins.
 */
public class OutsourcedPart extends Part{

    /**
     * declares a string that will be used as the companies name.
     */
    private String companyName;

    /**
     * constructor for in house parts.
     * @param id part id.
     * @param name is the name of the part.
     * @param price is the price of the part per unit.
     * @param stock is the quantity of the part in stock.
     * @param min is the minimum quantity allowed in stock of the part.
     * @param max is the maximum quantity allowed in stock of the part.
     * @param companyName is the company that manufactured the part.
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * getter for the company name.
     * @return the company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * setter for the company name.
     * @param companyName sets the companies name to this String.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
