package com.example.c482software1project;

/**
 * sub class for InHouse parts inherits class Part.
 *
 * @author daddy
 */

public class InHousePart extends Part {

    /**
     * declares a int that will be used as the machine ID for in house parts.
     */
    private int machineId;

    /**
     * constructor for in house parts.
     * @param id part id.
     * @param name is the name of the part.
     * @param price is the price of the part per unit.
     * @param stock is the quantity of the part in stock.
     * @param min is the minimum quantity allowed in stock of the part.
     * @param max is the maximum quantity allowed in stock of the part.
     * @param machineId is the parts machine ID.
     */
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * getter for the machine ID.
     * @return the parts machine ID.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * setter for the machine ID.
     * @param machineId sets the parts machine ID.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
