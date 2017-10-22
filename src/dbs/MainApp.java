/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author lizhengxing
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	MainApp theApp = new MainApp();
	theApp.start();
    }

    private void start() {
	Scanner s = new Scanner(System.in);

	boolean programRun = true;
	while (programRun == true) {
	    System.out.println("\n** MAIN MENU ******************************************************");
	    System.out.println("-> Welcome to Caroll's Book Store! What would you like to do today?");
	    System.out.println("\n(1) Products\n(2) Orders\n(3) Staff\n(4) Customers\n(5) Exit Program");
	    System.out.print("\n-> Please enter your option (1-4): ");
	    int option = getInt();

	    if (option == 1) {
		boolean productMenu = true;
		while (productMenu == true) {
		    System.out.println("\n** PRODUCTS *******************************************************");
		    System.out.println("\n(1) View Products");
		    System.out.println("(2) Manage Products");
		    System.out.println("(3) Go back to Main Menu");
		    System.out.print("\n-> Please enter your option (1-3): ");
		    int productOption = getInt();

		    if (productOption == 1) {
			boolean viewMenu = true;
			while (viewMenu == true) {
			    System.out.println("\n** VIEW PRODUCTS **************************************************");
			    System.out.println("\n(1) View all products");
			    System.out.println("(2) View product by ID");
			    System.out.println("(3) View products by category");
			    System.out.println("(4) View products using keywords");
			    System.out.println("(5) Go back to Products Menu");
			    System.out.print("\n-> Please enter your option (1-5): ");
			    int viewOption = getInt();

			    if (viewOption == 1) {
				System.out.println("\n-> All products are listed below:\n");
				Utility.displayAllProduct();
			    } else if (viewOption == 2) {
				System.out.print("\n-> Please enter the product ID: ");
				int productID = getInt();
				System.out.println("\n-> Product #" + productID + " is listed below:\n");
				Utility.displayProductByID(productID);
			    } else if (viewOption == 3) {
				System.out.println("\n-> Please select the product category:");
				System.out.println("(1) Book\n(2) Music\n(3) Video");
				System.out.print("\n-> Please enter your option (1-3): ");
				int catID = 0;
				while (catID != 1 && catID != 2 && catID != 3) {
				    catID = getInt();
				    if (catID != 1 && catID != 2 && catID != 3) {
					System.out.println("-> Error: Invalid option input, please try again.");
				    }
				}
				int categoryID = getInt();
				System.out.println("\n-> Products from category #" + categoryID + " are listed below:\n");
				Utility.displayProductByCategory(categoryID);
			    } else if (viewOption == 4) {
				System.out.print("\n-> Please enter the search keyword: ");
				String keyword = s.nextLine();
				System.out.println("\n-> Products with the keyword " + keyword + " are listed below:\n");
				Utility.searchByKeyword(keyword);
			    } else if (viewOption == 5) {
				break;
			    } else {
				System.out.println("-> Error: Invalid option input, please try again.");
			    }
			}
		    } else if (productOption == 2) {
			boolean manageMenu = true;
			while (manageMenu == true) {
			    System.out.println("\n** MANAGE PRODUCTS ************************************************");
			    System.out.println("\n(1) Add a new product");
			    System.out.println("(2) Modify product price");
			    System.out.println("(3) Delete a product");
			    System.out.println("(4) Go back to Products Menu");
			    System.out.print("\n-> Please enter your option (1-4): ");
			    int manageOption = getInt();

			    if (manageOption == 1) {
				System.out.println("\n-> Please select the category:");
				System.out.println("(1) Book\n(2) Music\n(3) Video");
				System.out.print("\n-> Please enter your option (1-3): ");
				int catID = 0;
				while (catID != 1 && catID != 2 && catID != 3) {
				    catID = getInt();
				    if (catID != 1 && catID != 2 && catID != 3) {
					System.out.println("-> Error: Invalid option input, please try again.");
				    }
				}
				System.out.println("\n-> Please fill in the product details below:\n");
				System.out.print("Product Name: ");
				String name = s.nextLine();
				System.out.print("Price: ");
				float price = s.nextFloat();
				System.out.print("Stock Available: ");
				int stock = getInt();

				System.out.println("\n-> Are you sure you want to add this product?");
				System.out.println("(1) Yes\n(2) No");
				System.out.print("\n-> Please enter your option (1/2): ");
				int addOption = getInt();

				if (addOption == 1) {
				    Utility.addNewProduct(catID, name, price, stock);
				} else {
				    System.out.println("\n-> Process cancelled.");
				}
				s.nextLine();
			    } else if (manageOption == 2) {
				System.out.print("\n-> Please enter the product name: ");
				String productName = s.nextLine();
				System.out.print("\n-> Please enter the new price: ");
				float productPrice = s.nextFloat();
				Utility.updatePrice(productName, productPrice);
			    } else if (manageOption == 3) {
				System.out.print("\n-> Please enter the product name: ");
				String productName = s.nextLine();
				System.out.println("\n-> Please select the category:");
				System.out.println("(1) Book\n(2) Music\n(3) Video");
				System.out.print("\n-> Please enter your option (1-3): ");

				int catOption = 0;
				String categoryName = "";
				while (catOption != 1 && catOption != 2 && catOption != 3) {
				    catOption = getInt();
				    if (catOption == 1) {
					categoryName = "Book";
				    } else if (catOption == 2) {
					categoryName = "Music";
				    } else if (catOption == 3) {
					categoryName = "Video";
				    } else {
					System.out.println("-> Error: Invalid option input, please try again.");
				    }
				}
				Utility.deleteProduct(categoryName, productName);
			    } else if (manageOption == 4) {
				break;
			    } else {
				System.out.println("-> Error: Invalid option input, please try again.");
			    }
			}
		    } else if (productOption == 3) {
			break;
		    } else {
			System.out.println("-> Error: Invalid option input, please try again.");
		    }
		}
	    } else if (option == 2) {
		boolean orderMenu = true;
		while (orderMenu == true) {
		    System.out.println("\n** ORDERS *********************************************************");
		    System.out.println("\n(1) Display Orders By Customer ID");
		    System.out.println("(2) Manage Orders");
		    System.out.println("(3) Go back to Main Menu");
		    System.out.print("\n-> Please enter your option (1-3): ");
		    int orderOption = getInt();

		    if (orderOption == 1) {
			System.out.print("\n-> Please enter your customer ID: ");
			int customerID = getInt();
			System.out.println("\n-> Orders of customer #" + customerID + " are listed below:\n");
			Utility.showOrderOfCustomer(customerID);
		    } else if (orderOption == 2) {
			boolean manageMenu = true;
			while (manageMenu == true) {
			    System.out.println("\n** MANAGE ORDERS **************************************************");
			    System.out.println("\n(1) Add a new order");
			    System.out.println("(2) Delete a order");
			    System.out.println("(3) Go back to Orders Menu");
			    System.out.print("\n-> Please enter your option (1-3): ");
			    int manageOption = getInt();

			    if (manageOption == 1) {
				System.out.println("\n-> Please fill in the order details below:");
				System.out.print("\n-> Please enter the customer ID: ");
				int customerID = getInt();
				System.out.print("\n-> Please enter the product ID: ");
				int productID = getInt();
				System.out.print("\n-> Please enter the product quantity: ");
				int quantity = getInt();
				System.out.print("\n-> Please enter the staff ID: ");
				int staffID = getInt();

				System.out.println("\n-> Are you sure you want to add this product?");
				System.out.println("(1) Yes\n(2) No");
				System.out.print("\n-> Please enter your option (1/2): ");
				int addOption = getInt();

				if (addOption == 1) {
				    Utility.addNewOrder(customerID, productID, quantity, staffID);
				} else {
				    System.out.println("\n-> Process cancelled.");
				}
			    } else if (manageOption == 2) {
				System.out.print("\n-> Please enter the order ID: ");
				int orderID = getInt();
				Utility.deleteOrder(orderID);
			    } else if (manageOption == 3) {
				break;
			    } else {
				System.out.println("-> Error: Invalid option input, please try again.");
			    }
			}
		    } else if (orderOption == 3) {
			break;
		    } else {
			System.out.println("-> Error: Invalid option input, please try again.");
		    }
		}
	    } else if (option == 3) {
		boolean staffMenu = true;
		while (staffMenu == true) {
		    System.out.println("\n** STAFF **********************************************************");
		    System.out.println("\n(1) View all staff");
		    System.out.println("(2) Add a new staff");
		    System.out.println("(3) Modify staff address");
		    System.out.println("(4) Go back to Main Menu");
		    System.out.print("\n-> Please enter your option (1-4): ");
		    int staffOption = getInt();

		    if (staffOption == 1) {
			System.out.println("\n-> All staff are listed below:\n");
			Utility.viewAllStaff();
		    } else if (staffOption == 2) {
			System.out.println("\n-> Please fill in the staff details below:");
			System.out.print("\nStaff name: ");
			String staffName = s.nextLine();
			System.out.print("Email: ");
			String email = s.nextLine();
			System.out.print("Address: ");
			String address = s.nextLine();
			Utility.addStaff(staffName, email, address);
		    } else if (staffOption == 3) {
			System.out.println("\n-> Please fill in the staff details below:");
			System.out.print("\nStaff name: ");
			String staffName = s.nextLine();
			System.out.print("Email: ");
			String email = s.nextLine();
			System.out.print("New Address: ");
			String address = s.nextLine();
			Utility.updateStaffAddress(staffName, email, address);
		    } else if (staffOption == 4) {
			break;
		    } else {
			System.out.println("-> Error: Invalid option input, please try again.");
		    }
		}
	    } else if (option == 4) {
		boolean customerMenu = true;
		while (customerMenu == true) {
		    System.out.println("\n** CUSTOMERS ******************************************************");
		    System.out.println("\n(1) View all customers");
		    System.out.println("(2) Add a new customer");
		    System.out.println("(3) Modify customer address");
		    System.out.println("(4) Go back to Main Menu");
		    System.out.print("\n-> Please enter your option (1-4): ");
		    int customerOption = getInt();

		    if (customerOption == 1) {
			System.out.println("\n-> All customers are listed below:\n");
			Utility.viewAllCustomer();
		    } else if (customerOption == 2) {
			System.out.println("\n-> Please fill in the customer details below:");
			System.out.print("\nCustomer name: ");
			String customerName = s.nextLine();
			System.out.print("Email: ");
			String email = s.nextLine();
			System.out.print("Address: ");
			String address = s.nextLine();
			Utility.addCustomer(customerName, email, address);
		    } else if (customerOption == 3) {
			System.out.println("\n-> Please fill in the customer details below:");
			System.out.print("\nCustomer name: ");
			String staffName = s.nextLine();
			System.out.print("Email: ");
			String email = s.nextLine();
			System.out.print("New Address: ");
			String address = s.nextLine();
			Utility.updateCustomerAddress(staffName, email, address);
		    } else if (customerOption == 4) {
			break;
		    } else {
			System.out.println("-> Error: Invalid option input, please try again.");
		    }
		}
	    } else if (option == 5) {
		System.out.println("\n-> Program exited.");
		System.exit(0);
	    } else {
		System.out.println("-> Error: Invalid option input, please try again.");
	    }
	}
    }

    public static int getInt() {
	Scanner s = new Scanner(System.in);
	try {
	    int option = s.nextInt();
	    s.nextLine();
	    return option;
	} catch (InputMismatchException e) {
	    System.out.println("-> Error: Input must be an integer.");
	    System.out.print("\n-> Please input again: ");
	    int option = getInt();
	    return option;
	}
    }
}
