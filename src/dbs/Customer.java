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
public class Customer {

    private String name;
    private int id;
    private String email;
    private String address;

    public Customer(String name, int id, String email, String address) {
	this.name = name;
	this.id = id;
	this.email = email;
	this.address = address;
    }

    public Customer() {
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

}
