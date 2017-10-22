/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author lizhengxing
 */
public class Order {

    private int orderId;
    private int customerId;
    private Date datePlaced;
    private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public Order(int orderId, int customerId, Date datePlaced) {
	this.orderId = orderId;
	this.customerId = customerId;
	this.datePlaced = datePlaced;
    }

    public Order() {
    }

    public int getOrderId() {
	return orderId;
    }

    public void setOrderId(int orderId) {
	this.orderId = orderId;
    }

    public int getCustomerId() {
	return customerId;
    }

    public void setCustomerId(int customerId) {
	this.customerId = customerId;
    }

    public Date getDatePlaced() {
	return datePlaced;
    }

    public void setDatePlaced(Date datePlaced) {
	this.datePlaced = datePlaced;
    }

    @Override
    public String toString() {
	return "Order{" + "orderId=" + orderId + ", customerId=" + customerId + ", datePlaced=" + datePlaced + '}';
    }

}
