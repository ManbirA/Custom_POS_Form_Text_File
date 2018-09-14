/*Manbir Arora
 * Date: May 30 2018
 * Purpose: Control objects in a POS environment to which 
 * 	employees interacting with customers can input their orders for tracking purposes.
 * 	Create GUI which can be edited by people who have access the text files 
 * 	allowing them to add new products with price and inventory numbers. 
 *	Coupon codes can also be added with unique strings 
 * 	that can give certain percent off. The text files can also
 * 	be edited in order to add gift cards, their numbers and 
 * 	amounts in specific. Floats and users are also kept in track of 
 *  cash transactions and can be accessed in the text files
 * 	Both gift cards and cash can be used in order to pay 
 * 	for the entire order. At the end of the order a 
 * "reciept" is printed using the serial monitor
 * 	Version : 1.5 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.*;
import java.io.*;

public class Main {

	static String[][] order = new String[2][15];					//Variables for readers, arrays, objects and numbers
	static int orderSize = 0;
	static FileWriter fw;
	static PrintWriter write;
	static FileReader fr;
	static BufferedReader read;
	static FileReader frC;
	static BufferedReader readC;
	static FileReader frC1;
	static BufferedReader readC1;
	static FileReader frG;
	static BufferedReader readG;
	static FileReader frG1;
	static BufferedReader readG1;
	static FileReader fr1;
	static BufferedReader read1;
	static FileWriter fw1;
	static PrintWriter writeFloats;
	static FileWriter fw2;
	static PrintWriter writeFloats2;
	static FileWriter fw3;
	static PrintWriter writeGiftCards;
	static Graphics graph;
	static Product[] products;
	static JButton[] productButtons;
	static Coupon[] arrayCodes;
	static GiftCard[] arrayCards;
	static int num;
	static int y;
	static int currentX = 100;
	static int currentY = 100;
	static int changeY = 0;
	static double discountPercent = 1;
	static boolean discountCheck = false;
	static double floatMoney = 0;
	static double finalCost = 0;
	static String userName = null;
	static double finalAmountPaid = 0;
	static double finalCostTemp = finalCost;
	static double sumTemp = 0;
	static double amountInCard = 0;
	static boolean giftCardUsed = false;
	static boolean loadingCorrect = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) { 

		SwingUtilities.invokeLater(new Runnable() {						//Method to run the GUI class when main is run
			@Override
			public void run() {
				new Gui();
			}
		});

		try {															//Try loop when opening readers and files to ensure no crashes

			frC = new FileReader("codes.txt");
			readC = new BufferedReader(frC);
			frC1 = new FileReader("codes.txt");
			readC1 = new BufferedReader(frC1);
			frG = new FileReader("gift cards.txt");
			readG = new BufferedReader(frG);
			frG1 = new FileReader("gift cards.txt");
			readG1 = new BufferedReader(frG1);
			fr = new FileReader("stock.txt");
			read = new BufferedReader(fr);
			fr1 = new FileReader("stock.txt");
			read1 = new BufferedReader(fr1);
			String str;
			int productStringArray = 0;
			int numCodes = 0;
			int numGiftCard = 0;

			while ((str = readC.readLine()) != null) {					//go through each text file and count number of lines for each
				numCodes++;
			}
			while ((str = read.readLine()) != null) {
				productStringArray++;
			}
			while ((str = readG.readLine()) != null) {
				numGiftCard++;
			}

			products = new Product[productStringArray];					//create the object arrays sized based on number of lines gathered before
			productButtons = new JButton[productStringArray];
			arrayCodes = new Coupon[numCodes];
			arrayCards = new GiftCard[numGiftCard];

			int codeNum = 0;
			try {														//try/catch to ensure formatting for txt files is proper

				while ((str = readC1.readLine()) != null) {				//go through each file and create objects based on information gathered from txt file
					createCoupon(str, codeNum);
					codeNum++;
				}
				int productNumber = 0;
				while ((str = read1.readLine()) != null) {
					createProduct(str, productNumber);
					productNumber++;
				}

				int cardNum = 0;
				while ((str = readG1.readLine()) != null) {
					createGiftCard(str, cardNum);
					cardNum++;

				}
			} catch (ArrayIndexOutOfBoundsException e) { 				//catch the error for txt formatting letting user know to tell manager something is wrong
				loadingCorrect = false;
			}

		} catch (IOException e) { 										//if files are not found or missing exit
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		Gui.btnLogin.addActionListener(new ActionListener() { 			//create a button action listener that will run once login is clicked
			public void actionPerformed(ActionEvent e) {
				if(loadingCorrect == true) {
					try { 													//try/catch to ensure float number user is inputing is number
						floatMoney = Double.parseDouble(Gui.txtFloat.getText()); //get the information user inputed about name and float and store as variable
						userName = Gui.txtName.getText();
						Gui.frameLogin.setVisible(false);
						Gui.frame.setVisible(true);
					} catch (NumberFormatException e1) {
						Gui.lblFloatError.setText("PLEASE ENTER VALID FLOAT");
					}
					try {  													//try/catch to ensure file is not missing, write to file about user's name and floats

						fw1 = new FileWriter("users.txt");
						writeFloats = new PrintWriter(fw1);

						writeFloats.println(userName + " : " + floatMoney);

						writeFloats.close();

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else
				Gui.lblLoadingError.setText("LOADING ERROR CONTACT MANAGER");
			}
		});

		Gui.btnCash.addActionListener(new ActionListener() {			//add action listener for cash button
			public void actionPerformed(ActionEvent e) {
				finalCostTemp = finalCost;								//store the final cost in temporary variables
				finalAmountPaid = 0;
				try {													//try/catch to ensure cash amount entered is actual double 
					finalCost = finalCost - Double.parseDouble(Gui.txtPaid.getText());
					finalAmountPaid += Double.parseDouble(Gui.txtPaid.getText());
					Gui.txtPaid.setText(" ");
					Gui.lblAmountPay.setText("Amount: "+priceWithDecimal((finalCost)));
				} catch (NumberFormatException e1) {
					Gui.lblError.setText("Please Enter Real Cash Amount");
				}
				if (finalCost <= 0) {									//if all money is paid display how much change to give back
					Gui.lblAmountPay.setText(" $0 ");
					Gui.lblChange.setText("Change: " + priceWithDecimal(finalAmountPaid - finalCostTemp));
					if(giftCardUsed == true) {
					System.out.println("There is " + amountInCard + " left on the gift card");
					}
				}

			}
		});

		Gui.btnGiftCard.addActionListener(new ActionListener() {	//add action listener for gift card button
			public void actionPerformed(ActionEvent e) {
				try { 												//ensure number inputed is actual long
					amountInCard = GiftCard.getBalance(arrayCards, Long.parseLong(Gui.txtGiftCard.getText()));
					finalCostTemp = finalCost;
					if (amountInCard == 0) {                        //if card is empty let user know
						Gui.lblGiftCardError.setText("INVALID CARD");
					} else if (amountInCard > finalCost) { 			//if card balance is more than final cost take out only the final cost from card
						giftCardUsed = true;
						amountInCard = amountInCard - finalCost;
						floatMoney = floatMoney - finalCost;		//when complete button clicked final cost is added to float, subtract gift card value since it will not be in float
						finalCost = 0;
						for (int g = 0; g < arrayCards.length; g++) { //go through entire gift card array and change value of how much money in card
							if (arrayCards[g].cardNum == Long.parseLong(Gui.txtGiftCard.getText())) {
								arrayCards[g].cardBalance = amountInCard;
							}
						}
					} else if (amountInCard < finalCost) { 			//if amount in card is less than final cost only take out the amount in card
						giftCardUsed = true;
						finalCost = finalCost - amountInCard;
						floatMoney = floatMoney - amountInCard;		//set float money to number taken out from final cost
						amountInCard = 0;
						for (int g = 0; g < arrayCards.length; g++) { //go through entire gift card array and change value of how much money in card
							if (arrayCards[g].cardNum == Long.parseLong(Gui.txtGiftCard.getText())) {
								arrayCards[g].cardBalance = amountInCard;
							}
						}
						Gui.lblAmountPay.setText("Amount: "+ priceWithDecimal(finalCost));
					}
				} catch (NumberFormatException e1) {
					Gui.lblGiftCardError.setText("INVALID CODE");
				}
				try {
					fw3 = new FileWriter("gift cards.txt");
					writeGiftCards = new PrintWriter(fw3);
					for (int g = 0; g < arrayCards.length; g++) {
						writeGiftCards
								.println(arrayCards[g].cardNum + " : " + priceWithNoDollar(arrayCards[g].cardBalance));
					}
					writeGiftCards.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				if (finalCost <= 0) {								//if when using gift card final cost becomes 0 no change given so set total amount to 0
					Gui.lblAmountPay.setText(" $0 ");
					if(giftCardUsed == true) {
						System.out.println("There is: " + priceWithDecimal(amountInCard) + " left of the gift card");
					}
				}

			}
		});
		Gui.btnNextOrder.addActionListener(new ActionListener() {	//add action listener for next order button
			public void actionPerformed(ActionEvent e) {
				if (finalCost <= 0) {								//make sure payment has gone through
					try {

						fw2 = new FileWriter("users.txt");			//print out to text file about updated float amounts
						writeFloats2 = new PrintWriter(fw2);

						writeFloats2.println(userName + " : " + priceWithDecimal(floatMoney));

						writeFloats2.close();

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					finalCost = 0;									//reset all label variables to prepare for next order
					Gui.lblChange.setText(" ");
					Gui.txtCouponCode.setText(" ");
					Gui.txtGiftCard.setText(" ");
					Gui.txtPaid.setText(" ");
					Gui.lblCouponError.setText(" ");
					Gui.lblSumTemp.setText(" ");
					sumTemp = 0;
					Gui.framePayment.setVisible(false);
					Gui.frame.setVisible(true);
					finalAmountPaid = 0;
					giftCardUsed = false;

				}

			}
		});
		Gui.btnAddCoupon.addActionListener(new ActionListener() { 	//action listener for add coupon button 
			public void actionPerformed(ActionEvent e) {
				discountPercent = Coupon.getDiscount(arrayCodes, Gui.txtCouponCode.getText()); //get discount from coupon object to see what percent discount is given
				if (discountPercent == 1 || discountPercent == 0) { //if coupon code has no effect(invalid) let user know
					Gui.lblCouponError.setText("INVALID COUPON CODE");
					discountCheck = false;
				} else {											//if valid let user know how much percent discount is being given
					String priceOut = (priceWithPercent((1 - discountPercent)));
					Gui.lblCouponError.setText("Adding Discount of " + " " + priceOut + " off");
					discountCheck = true;
				}
			}
		});
		Gui.btnDelete.addActionListener(new ActionListener() {		//action listener for delete button
			public void actionPerformed(ActionEvent e) {
				if(orderSize > 0 ) {
				deleteFromOrder(Gui.lstorder.getSelectedValue());   //run delete from order method
				Gui.listData.removeElementAt(Gui.lstorder.getSelectedIndex()); //remove element at the selected list value
				}
			}
		});
		Gui.btnComplete.addActionListener(new ActionListener() {   //action listener for complete order button
			public void actionPerformed(ActionEvent e) {
				orderCompelete();								   // run order complete method
			}
		});

	}																//end of main method

	public static void createProduct(String stringInput, int num) { //method to create product, taking inputs for string from txt file and num(to see which object is being created)
		try {														//ensure proper formating for txt file
			String getInfo[] = stringInput.split(" : ");			//split given string by : 
			int inventory = Integer.parseInt(getInfo[2]);			//store the values with the corresponding split string
			double price = Double.parseDouble(getInfo[1]);		
			String name = getInfo[0];
			products[num] = new Product(name, price, inventory); 	//using gathered information create product object at product array of num
			productButtons[num] = new JButton(name+" ,$"+price);				//create button with name of product
			changeY++;
			if(currentY+(50 * changeY) > 900) {						//positioning for buttons
				currentX = 300;
				currentY = 100;
				changeY = 1;
			}
			productButtons[num].setBounds(currentX, currentY+(50 * changeY), 225, 25);	//place buttons is subsequent locations
			productButtons[num].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addToOrder(products[num]);											//action listener for when button is clicked add the product create to the order
				}
			});

			Gui.panel.add(productButtons[num]);
		} catch (NumberFormatException e) {
			Gui.lblLoadingError.setText("LOADING ERROR CONTACT MANAGER");
		}

	}																				//end of create product method

	public static void createCoupon(String stringInput, int num) {						//method to create coupon objects, stirng input from txt file and num for which coupon in the array is created
		try {
			String getInfo[] = stringInput.split(" : ");								//store values in array for creating coupon object
			String code = getInfo[0];
			double discount = Double.parseDouble(getInfo[1]);
			arrayCodes[num] = new Coupon(code, discount);
		} catch (NumberFormatException e) {
			Gui.lblLoadingError.setText("LOADING ERROR CONTACT MANAGER");
		}
	}																				//end of create coupon method

	public static void createGiftCard(String stringInput, int num) {					//method to create gift card object with inputs of string form txt file and 
		try {																			//ensure proper formating for txt files
			String getInfo[] = stringInput.split(" : ");
			long cardNum = Long.parseLong(getInfo[0]);
			double balance = Double.parseDouble(getInfo[1]);
			arrayCards[num] = new GiftCard(cardNum, balance);
		} catch (NumberFormatException e) {
			Gui.lblLoadingError.setText("LOADING ERROR CONTACT MANAGER");
		}
	}																			//end of create gift card method

	public static void addToOrder(Product p) {											//method to add product into the order inputs are product object that is being sold
		if (p.inventory < 1) {															//if items inventory is sold out dont all product to be added 
			Gui.lblSoldOut.setText("ITEM IS SOLD OUT CONTACT MANAGER");
		} else {																		//if product available add to order array and its price
			Gui.lblSoldOut.setText(" ");
			sumTemp = sumTemp + p.price;
			Gui.lblSumTemp.setText("Order Total: "+ sumTemp);
			order[0][orderSize] = p.item;
			order[1][orderSize] = Double.toString(p.price);
			orderSize++;
			Gui.listData.addElement(p.item);

			if (order[1].length - orderSize < 5) {										//if the size of the order array is approaching to be fill increase the size by 5 columns
				int row = order.length;
				order = (String[][]) resizeArray(order, row);

				int col = order[1].length + 5;
				for (int i = 0; i < order.length; i++) {
					if (order[i] == null) {
						order[i] = new String[col];
					} else {
						order[i] = (String[]) resizeArray(order[i], col);
					}
				}
			}
		}
	}																				//end of add to order method

	public static void deleteFromOrder(String productName) {							//delete from order method taking in string for product being removed
		boolean found = false;										
		for (int i = 0; i < orderSize; i++) {											//go through entire array 
			if (productName == (order[0][i]) && found == false) {						//if string exists and first items deleted(boolean check)
				found = true;
				sumTemp = sumTemp - Double.parseDouble(order[1][i]);
				Gui.lblSumTemp.setText("Order Total: "+ sumTemp);
				order[0][i] = null;
				order[1][i] = null;
				for (int x = 0; x < (orderSize - i); x++) {								//where the string was found start there and move each following element within the array forward one spot
					String temp = null;
					String temp1 = null;
					temp = order[0][i + x];
					temp1 = order[1][i + x];
					order[0][i + x] = order[0][i + x + 1];
					order[1][i + x] = order[1][i + x + 1];
					order[0][i + x + 1] = temp;
					order[1][i + x + 1] = temp1;
				}
			}
		}
		orderSize--;
	}																			//end of delete from order method

	public static void orderCompelete() {												//method to complete order

		int min = 0;
		for (int i = 0; i < orderSize; i++) {											//sort order array using selection sort to ascending order

			min = i;
			for (int j = i + 1; j < orderSize; j++) {
				if (Double.parseDouble(order[1][j]) < Double.parseDouble(order[1][min])) {
					min = j;
				}
			}
			String temp1 = order[0][i];
			String temp2 = order[1][i];
			order[0][i] = order[0][min];
			order[1][i] = order[1][min];
			order[0][min] = temp1;
			order[1][min] = temp2;
		}
		
		

		double[] sumArray = new double[orderSize];								//array to assist in finding sum of array
		double sum = 0;

		for (int y = 0; y < orderSize; y++) {									//copy all the prices into sum array
			sumArray[y] = Double.parseDouble(order[1][y]);
		}
		sum = findSum(sumArray, sumArray.length);
		
		for (int i = 0; i < orderSize; i++) {									//go through entire array search for items in order to products name
			for (int x = 0; x < products.length; x++) {
				if (order[0][i].contains(products[x].item)) {
					Product.sold(products[x]);									//sell product object depending on which product is sold
				}
			}
		}

		for(int i = 0; i < orderSize;i++) {
			System.out.println(order[0][i] + "\t" + order[1][i]);
		}
		finalCost = sum * 1.13 * discountPercent;								//find final price my using sum adding tax and the discount
		floatMoney = floatMoney + finalCost;
		Gui.lblAmountPay.setText(("Amount: " + " " + priceWithDecimal(finalCost)));
		
		System.out.println("\nTax: " + priceWithDecimal(sum * 0.13));			//"print" a receipt

		if (discountCheck == true) {
			System.out.println("Discount: " + priceWithDecimal(sum * 1.13 * (1 - discountPercent)));
		}
		System.out.println("Final Cost: " + priceWithDecimal(sum * 1.13 * discountPercent));
		System.out.println("You Were Served by " + userName + "\n" + "Thank You and See you Soon!");

		Gui.frame.setVisible(false);
		Gui.framePayment.setVisible(true);

		try {																	//re-write to txt files with updated stock counts

			fw = new FileWriter("stock.txt");
			write = new PrintWriter(fw);

			for (int r = 0; r < products.length; r++) {
				write.println(products[r].item + " : " + products[r].price + " : " + products[r].inventory);
			}
			write.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int t = 0; t < orderSize; t++) { 									//clear array and list to prepare for next order
			order[0][t] = null;
			order[1][t] = null;
			Gui.listData.removeAllElements();
			discountPercent = 1;
			discountCheck = false;
		}
		orderSize = 0;

	}																		//end of order complete method

	static double findSum(double[] sumArray, int length) {					//method of double to return the sum of numbers within in array by taking in double array
		if (length <= 0) {
			return 0;
		}
		return (findSum(sumArray, length - 1) + sumArray[length - 1]);
	}																		//end of find sum method

	public static String priceWithDecimal(Double price) {				//method taking in double price and formatting with dollar sign and 2 decimals
		DecimalFormat formatter = new DecimalFormat("$###,###,###.00");
		return formatter.format(price);
	}																		//end of format with dollar method

	public static String priceWithNoDollar(Double price) {			//method taking in double price and formatting with 2 decimals
		DecimalFormat formatter = new DecimalFormat("###,###,###.00");
		return formatter.format(price);
	}																		//end of format with no dollar method

	public static String priceWithPercent(Double price) {
		DecimalFormat formatter = new DecimalFormat("###,###,###.00%"); //method taking in double price and formatting with percent sign and 2 decimals
		return formatter.format(price);
	}																		//end of price with percent format method

	private static Object resizeArray(Object oldArray, int newSize) {	//method taking in array and new size for it to resize the array
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
		int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0)
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		return newArray;
	}																	//end of resize array method

}																		//end of main class