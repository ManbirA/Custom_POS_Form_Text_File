/*Manbir Arora
 * Purpose: Create coupon object that will have individual number and discount
 * Date:  May 30 2018
 * Version:1.2
 */
public class Coupon {

	String code;
	double discount;

	public Coupon(String codeInput, double discountInput) { 		//create coupon object constructor taking in the code and the discount amount 
		code = codeInput;
		discount = discountInput;
	}																//end of coupon constructor

	public static double getDiscount(Coupon[] c, String target) {	//method to find how much discount is being given the code, using linear search. 
																	//Inputs are the coupon object array and the string for the target code
		for (int i = 0; i < c.length; i++) {
			if (c[i].code.equals(target)) {
				return (1 - ((c[i].discount) / 100));
			}
		}

		return 1;

	}																//end of get discount method

}																	//end of coupon class
