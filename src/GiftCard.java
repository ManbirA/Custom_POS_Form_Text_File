/*Author: Manbir Arora
 * Purpose: gift card objects that will have individual codes and balances
 * Date: May 30 2018 
 * Version: 1.2
 */
public class GiftCard {
	long cardNum;
	double cardBalance;
	
	public GiftCard(long num, double amount) {						//object constructor for gift card that will contain the card number as long and amount as double
		cardNum = num;
		cardBalance = amount;
		
	}																//end of gift card constructor
	
	
	public static double getBalance(GiftCard[] gc, long cardLong) {  //method to get the balance for the gift card by linear search for that card
		for (int i = 0; i < gc.length; i++) {						//inputs are the gift card object array and the card number that needs the balance to be found
			if (gc[i].cardNum == cardLong) {
				return gc[i].cardBalance;
			}
		}

		return 0;
		
	}																//end of get balance method
}																	//end of gift card class
