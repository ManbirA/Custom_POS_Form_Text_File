/*Author: Manbir Arora
 * Purpose: product objects that will have individual names, price and inventory amount
 * Date: May 30 2018 
 * Version: 1.2
 */
public class Product {
	String item = null;
	double price  = 0;
	int inventory = 0;
	
	public Product(String itemName, double itemPrice, int itemInventory) { 			//object constructor for products, hold iteam name string, item price double and inventory integer
		item = itemName;
		price = itemPrice;
		inventory = itemInventory;
	}																				//end of product constructor
	
	public static void sold(Product itemSold) { 					//method to subtract an amount of item's inventory by that that object product has an input
		itemSold.inventory--;
	}																//end of sold method
	


}																	//end of product class
