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
public class Product implements Display {

    private int productId;
    private int categoryId;
    private String name;
    private double unitPrice;
    private int inStock;

    public Product(int productId, int categoryId, String name, double unitPrice, int inStock) {
	this.productId = productId;
	this.categoryId = categoryId;
	this.name = name;
	this.unitPrice = unitPrice;
	this.inStock = inStock;
    }

    public Product() {
    }

    public int getProductId() {
	return productId;
    }

    public void setProductId(int productId) {
	this.productId = productId;
    }

    public int getCategoryId() {
	return categoryId;
    }

    public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public double getUnitPrice() {
	return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
	this.unitPrice = unitPrice;
    }

    public int getInStock() {
	return inStock;
    }

    public void setInStock(int inStock) {
	this.inStock = inStock;
    }

    @Override
    public String toString() {
	return "Product{" + "productId=" + productId + ", categoryId=" + categoryId + ", name=" + name + ", unitPrice=" + unitPrice + ", inStock=" + inStock + '}';
    }

}
