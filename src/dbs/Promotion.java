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
public class Promotion {

    private int id;
    private String code;

    public Promotion(int id, String code) {
	this.id = id;
	this.code = code;
    }

    public Promotion() {
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    @Override
    public String toString() {
	return "Promotion{" + "id=" + id + ", code=" + code + '}';
    }

}
