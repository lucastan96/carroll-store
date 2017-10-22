/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

/**
 *
 * @author lizhengxing
 */
public class OrderDetail {

    private int orderId;
    private int productId;
    private int quantity;

    public OrderDetail(int orderId, int productId, int quantity) {
	this.orderId = orderId;
	this.productId = productId;
	this.quantity = quantity;
    }

    public OrderDetail() {
    }

    public int getOrderId() {
	return orderId;
    }

    public void setOrderId(int orderId) {
	this.orderId = orderId;
    }

    public int getProductId() {
	return productId;
    }

    public void setProductId(int productId) {
	this.productId = productId;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    @Override
    public String toString() {
	return "OrderDetail{" + "orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + '}';
    }

}
