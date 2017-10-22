/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author lizhengxing
 */
public class Utility {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/carroll_store";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public static void displayAllProduct() {
	Connection conn = null;
	Statement stmt = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    stmt = conn.createStatement();
	    String query = "CALL showAllProduct";
	    ResultSet rs = stmt.executeQuery(query);
	    if (rs.next()) {
		rs.beforeFirst();
		System.out.println("Product ID\tProduct Name\tUnit Price\tQuantity In Stock");

		while (rs.next()) {

		    System.out.printf("%-15s %15s %10.2f %4d %n", rs.getInt("productID"), rs.getString("productName"), rs.getDouble("unitPrice"), rs.getInt("inStock"));
		}
	    } else {
		System.out.println("-> No product found!");
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void searchProductBySetInt(int id, String query) {
	Connection conn = null;
	PreparedStatement stmt = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    stmt = conn.prepareStatement(query);
	    stmt.setInt(1, id);
	    ResultSet rs = stmt.executeQuery();
	    if (rs.next()) {
		rs.beforeFirst();
		System.out.println("Product ID\tProduct Name\tUnit Price\tQuantity In Stock");
		while (rs.next()) {
		    System.out.print(rs.getInt("productID") + "\t");
		    System.out.printf("%15s\t", rs.getString("productName"));
		    System.out.printf("%13.2f\t", rs.getDouble("unitPrice"));
		    System.out.printf("%10d", rs.getInt("inStock"));
		    System.out.println();
		}
	    } else {
		System.out.println("-> No product found!");
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void displayProductByID(int id) {
	String query = "SELECT * FROM product WHERE productID = ?";
	Utility.searchProductBySetInt(id, query);
    }

    public static void displayProductByCategory(int id) {
	String query = "SELECT * FROM product WHERE categoryID = ?";
	Utility.searchProductBySetInt(id, query);
    }

    public static void searchByKeyword(String keyword) {
	Connection conn = null;
	PreparedStatement stmt = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    String query = "SELECT * FROM product WHERE productName LIKE ?";
	    stmt = conn.prepareStatement(query);
	    stmt.setString(1, "%" + keyword + "%");
	    ResultSet rs = stmt.executeQuery();
	    if (rs.next()) {
		rs.beforeFirst();
		System.out.println("Product ID\tProduct Name\tUnit Price\tQuantity In Stock");

		while (rs.next()) {
		    System.out.print(rs.getInt("productID") + "\t");
		    System.out.printf("%15s\t", rs.getString("productName"));
		    System.out.printf("%13.2f\t", rs.getDouble("unitPrice"));
		    System.out.printf("%10d", rs.getInt("inStock"));
		    System.out.println();
		}
	    } else {
		System.out.println("-> No product found!");
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void addNewProduct(int id, String name, float price, int stock) {
	Connection conn = null;
	PreparedStatement stmt = null;
	PreparedStatement stmt1 = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    String querySelectID = "SELECT * FROM category WHERE categoryID = ?";
	    stmt = conn.prepareStatement(querySelectID);
	    stmt.setInt(1, id);
	    ResultSet rs = stmt.executeQuery();

	    if (rs.next()) {
		String queryInsert = "INSERT INTO product(categoryID,productName,unitPrice,inStock) VALUES (?,?,?,?)";
		stmt1 = conn.prepareStatement(queryInsert);
		stmt1.setInt(1, id);
		stmt1.setString(2, name);
		stmt1.setFloat(3, price);
		stmt1.setInt(4, stock);
		stmt1.executeUpdate();
		System.out.println("\n-> Product added. <3");

		rs.close();
		stmt.close();
		conn.close();
	    } else {
		System.out.println("\n-> Category entered does not exist. Please try again.");

	    }
	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void updatePrice(String name, float price) {
	Connection conn = null;
	PreparedStatement stmt = null;
	PreparedStatement stmt1 = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    String queryGetProductID = "SELECT * FROM product WHERE productName = ?";
	    stmt = conn.prepareStatement(queryGetProductID);
	    stmt.setString(1, name);
	    ResultSet rs = stmt.executeQuery();

	    if (rs.next()) {
		int id = rs.getInt("productID");
		String queryUpdatePrice = "CALL UpdatePrice(?,?)";
		stmt1 = conn.prepareStatement(queryUpdatePrice);
		stmt1.setFloat(1, price);
		stmt1.setInt(2, id);

		stmt1.executeUpdate();
		System.out.println("\n-> Price is updated!");

		rs.close();
		stmt.close();
		conn.close();
	    } else {
		System.out.println("\n-> Unable to find product.");
	    }
	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void deleteProduct(String category, String name) {
	Connection conn = null;
	PreparedStatement stmt = null;
	PreparedStatement stmt1 = null;
	PreparedStatement stmt2 = null;
	PreparedStatement stmt3 = null;
	PreparedStatement stmt4 = null;

	int categoryid = 0;
	int productid = 0;

	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    String queryGetCategoryID = "SELECT categoryID FROM category WHERE categoryName = ?";
	    stmt = conn.prepareStatement(queryGetCategoryID);
	    stmt.setString(1, category);
	    ResultSet rs = stmt.executeQuery();

	    if (rs.next()) {
		categoryid = rs.getInt("categoryID");
	    } else {
		System.out.println("\n-> Category does not exist.");
	    }

	    String queryProductID = "SELECT productID FROM product WHERE productName = ?";
	    stmt1 = conn.prepareStatement(queryProductID);
	    stmt1.setString(1, name);
	    ResultSet rs1 = stmt1.executeQuery();

	    while (rs1.next()) {
		productid = rs1.getInt("productID");
	    }

	    String queryCheck = "SELECT * FROM product WHERE productID = ? AND categoryID = ?";
	    stmt2 = conn.prepareStatement(queryCheck);
	    stmt2.setInt(1, productid);
	    stmt2.setInt(2, categoryid);
	    ResultSet rs2 = stmt2.executeQuery();

	    if (rs2.next()) {
		String queryCheckOrderDetail = "SELECT * FROM orderdetail WHERE productID = ?";
		stmt3 = conn.prepareStatement(queryCheckOrderDetail);
		stmt3.setInt(1, productid);
		ResultSet rs3 = stmt3.executeQuery();

		if (rs3.next()) {
		    System.out.println("\n-> Fail to delete this product. Customers might have made orders for this product.");
		} else {
		    String queryDelete = "CALL DeleteProduct(?)";
		    stmt4 = conn.prepareStatement(queryDelete);
		    stmt4.setInt(1, productid);
		    ResultSet rs4 = stmt4.executeQuery();

		    System.out.println("\n-> Product has been deleted successfully. <3");

		    rs.close();
		    stmt.close();
		    conn.close();

		}
	    } else {
		System.out.println("\n-> Category and product does not match.");
	    }
	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void showOrderOfCustomer(int id) {
	Connection conn = null;
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	PreparedStatement stmt = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    String query = "CALL showOrderOfCustomer(?)";
	    stmt = conn.prepareStatement(query);
	    stmt.setInt(1, id);
	    ResultSet rs = stmt.executeQuery();
	    if (rs.next()) {
		rs.beforeFirst();
		System.out.println("Order ID\tProduct Name\tOrder Date\tQuantity\tStaff ID\tTotal Price");
		while (rs.next()) {
		    System.out.print(rs.getInt("orderID") + "\t");
		    System.out.printf("%17s\t", rs.getString("productName"));
		    System.out.printf("%17s\t", rs.getString("orderDate").substring(0, 10));
		    System.out.print(rs.getInt("quantity") + "\t");
		    System.out.print(rs.getInt("staffID") + "\t");
		    System.out.printf("%5.2f", rs.getDouble("totalPrice"));
		    System.out.println();
		}
	    } else {
		System.out.println("-> You haven't placed any order yet!");
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void addNewOrder(int customer_id, int product_id, int quantity, int staff_id) {
	Connection conn = null;
	PreparedStatement stmt1 = null;
	PreparedStatement stmt2 = null;
	PreparedStatement stmt3 = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    String queryInsert = "INSERT INTO orders(customerID, staffID) VALUES (?,?)";
	    stmt1 = conn.prepareStatement(queryInsert);
	    stmt1.setInt(1, customer_id);
	    stmt1.setInt(2, staff_id);
	    stmt1.executeUpdate();

	    String querySelectOrderID = "SELECT orderID FROM orders WHERE customerID = ? AND staffID = ?";
	    stmt2 = conn.prepareStatement(querySelectOrderID);
	    stmt2.setInt(1, customer_id);
	    stmt2.setInt(2, staff_id);
	    ResultSet rs = stmt2.executeQuery();

	    String queryInsert2 = "INSERT INTO orderdetail(orderID, productID, quantity) VALUES (?,?,?)";
	    stmt3 = conn.prepareStatement(queryInsert2);
	    rs.last();
	    stmt3.setInt(1, rs.getInt("orderID"));
	    stmt3.setInt(2, product_id);
	    stmt3.setInt(3, quantity);
	    stmt3.executeUpdate();

	    System.out.println("\n-> Order added. <3");

	    conn.close();

	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void deleteOrder(int id) {
	Connection conn = null;
	PreparedStatement stmt = null;
	PreparedStatement stmt1 = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    String querySelectID = "SELECT * FROM orderdetail WHERE orderID = ?";
	    stmt = conn.prepareStatement(querySelectID);
	    stmt.setInt(1, id);
	    ResultSet rs = stmt.executeQuery();

	    if (rs.next()) {
		System.out.println("\n-> Fail to delete, could not edit details in parent row.");
	    } else {
		String queryDelete = "CALL DeleteOrder(?)";
		stmt1 = conn.prepareStatement(queryDelete);
		stmt1.setInt(1, id);
		ResultSet rs1 = stmt.executeQuery();

		rs.close();
		stmt.close();
		conn.close();
	    }
	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void viewAllStaff() {
	Connection conn = null;
	Statement stmt = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    stmt = conn.createStatement();
	    String query = "CALL ShowAllStaff";
	    ResultSet rs = stmt.executeQuery(query);
	    if (rs.next()) {
		rs.beforeFirst();
		System.out.println("Staff ID\tStaff Name\tStaff Email\t\tStaff Address");

		while (rs.next()) {
		    System.out.print(rs.getInt("staffID") + "\t\t");
		    System.out.print(rs.getString("staffName") + "\t\t");
		    System.out.print(rs.getString("staffEmail") + "\t");
		    System.out.print(rs.getString("staffAddress") + "\t");
		    System.out.println();
		}
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void addStaff(String name, String email, String address) {
	Connection conn = null;
	PreparedStatement stmt = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    String queryAddStaff = "CALL AddStaff(?,?,?)";
	    stmt = conn.prepareStatement(queryAddStaff);
	    stmt.setString(1, name);
	    stmt.setString(2, email);
	    stmt.setString(3, address);
	    stmt.executeUpdate();

	    System.out.println("\n-> Staff Added!");

	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void updateStaffAddress(String name, String email, String address) {
	Connection conn = null;
	PreparedStatement stmt = null;
	PreparedStatement stmt1 = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    String queryCheckStaff = "SELECT staffID FROM staff WHERE staffName = ? AND staffEmail = ?";
	    stmt = conn.prepareStatement(queryCheckStaff);
	    stmt.setString(1, name);
	    stmt.setString(2, email);
	    ResultSet rs = stmt.executeQuery();

	    if (rs.next()) {
		int staffid = rs.getInt("staffID");
		String queryUpdateAddress = "CALL UpdateStaffAddress(?,?)";
		stmt1 = conn.prepareStatement(queryUpdateAddress);
		stmt1.setString(1, address);
		stmt1.setInt(2, staffid);
		stmt1.executeUpdate();
		System.out.println("\n-> Staff address is updated!");

		rs.close();
		stmt.close();
		conn.close();
	    } else {
		System.out.println("\n-> Staff does not exist.");
	    }

	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void viewAllCustomer() {
	Connection conn = null;
	Statement stmt = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    stmt = conn.createStatement();
	    String query = "CALL ShowAllCustomer";
	    ResultSet rs = stmt.executeQuery(query);
	    if (rs.next()) {
		rs.beforeFirst();
		System.out.println("Customer ID\tCustomer Name\tCustomer Email\t\tCustomer Address");

		while (rs.next()) {
		    System.out.print(rs.getInt("customerID") + "\t\t");
		    System.out.print(rs.getString("customerName") + "\t\t");
		    System.out.print(rs.getString("customerEmail") + "\t");
		    System.out.print(rs.getString("customerAddress") + "\t");
		    System.out.println();
		}
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void addCustomer(String name, String email, String address) {
	Connection conn = null;
	PreparedStatement stmt = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    String queryAddCustomer = "CALL AddCustomer(?,?,?)";
	    stmt = conn.prepareStatement(queryAddCustomer);
	    stmt.setString(1, name);
	    stmt.setString(2, email);
	    stmt.setString(3, address);
	    stmt.executeUpdate();

	    System.out.println("\n-> Customer Added!");

	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }

    public static void updateCustomerAddress(String name, String email, String address) {
	Connection conn = null;
	PreparedStatement stmt = null;
	PreparedStatement stmt1 = null;
	try {
	    Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    String queryCheckCustomer = "SELECT customerID FROM customer WHERE customerName = ? AND customerEmail = ?";
	    stmt = conn.prepareStatement(queryCheckCustomer);
	    stmt.setString(1, name);
	    stmt.setString(2, email);
	    ResultSet rs = stmt.executeQuery();

	    if (rs.next()) {
		int customerid = rs.getInt("customerID");
		String queryUpdateAddress = "CALL UpdateCustomerAddress(?,?)";
		stmt1 = conn.prepareStatement(queryUpdateAddress);
		stmt1.setString(1, address);
		stmt1.setInt(2, customerid);
		stmt1.executeUpdate();
		System.out.println("\n-> Customer address is updated!");

		rs.close();
		stmt.close();
		conn.close();
	    } else {
		System.out.println("\n-> Customer does not exist.");
	    }

	} catch (Exception ex) {
	    System.out.print(ex.getMessage());
	}
    }
}
