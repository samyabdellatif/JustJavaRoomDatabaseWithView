package com.example.android.justjava;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * defining the database table
 * table name: orders_table
 * column1: oid INT PRIMARY KEY
 * column2: costumer_name STRING
 * column3: total_price INT
 */
@Entity (tableName = "orders_table")
public class CoffeeOrder {
    /**
     * fields to store in the database
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "oid")
    private int orderID;
    @ColumnInfo(name = "costumer_name")
    private String costumerName;
    @ColumnInfo(name = "total_price")
    private int totalPrice;
    /**
     * @Ignore
     * Ignore those field and don't store them in the database
     */
    @Ignore
    private int quantity;
    @Ignore
    private boolean hasWhippedCream;
    @Ignore
    private boolean hasChocolate;
    /**
     * constructor
     * grab the context on creating the objects to use some of the methods like getString()
     */
    public CoffeeOrder(){
    }
    /**
     * methods
     *
     * in this section we'll build the setters
     *
     * a method to set the order values. takes the values as inputs for  name, quantity,
     * hasWhippedCream, hasChocolate respectively. and calculates the totalPrice.
     */
    public void setOrder(String name,int quantity, boolean hasWhippedCream,boolean hasChocolate) {
        this.costumerName = name;
        this.hasWhippedCream = hasWhippedCream;
        this.hasChocolate = hasChocolate;
        this.quantity= quantity;
        int price = 3;
        if(hasWhippedCream) {
            int whippedCreamPrice = 1;
            price+= whippedCreamPrice;
        }
        if(hasChocolate){
            int chocolatePrice = 2;
            price+= chocolatePrice;
        }
        this.totalPrice= this.quantity * price;
    }

    /**
     * setting the order ID number
     * @param orderID : int
     */
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * in this section we'll build the getters
     */
    public int getOrderID() {
        return this.orderID;
    }
    public String getCostumerName() {
        return this.costumerName;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public int getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * this method returns a string to be viewed and stored in the database
     * @return String
     */
    public String getOrderSummary(){
        String summary = "OrderNo:" + orderID;
        summary += "\n" +  MainActivity.context.getString(R.string.order_summary_name)+costumerName;
        summary += "\n" +  MainActivity.context.getString(R.string.order_summary_quantity) + quantity;
        summary += "\n" +  MainActivity.context.getString(R.string.order_summary_whipped_cream)+hasWhippedCream;
        summary += "\n" +  MainActivity.context.getString(R.string.order_summary_chocolate)+hasChocolate;
        summary += "\n" +  MainActivity.context.getString(R.string.order_summary_price) + NumberFormat.getCurrencyInstance(Locale.US).format(totalPrice);
        summary += "\n" +  MainActivity.context.getString(R.string.thank_you);
        return summary;
    }
}
