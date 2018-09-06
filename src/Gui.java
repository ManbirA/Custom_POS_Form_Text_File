/*Author: Manbir Arora
 * Purpose: Create all GUI elements and set their bounds for use by the main class
 * Date: May 30 2018 
 * Version: 1.2
 */
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class Gui extends JFrame {

	static JFrame frame = new JFrame();			//Initialize all elements being used as public so can be acceses by other classes
	static JPanel panel = new JPanel();
	static JFrame frameLogin = new JFrame();
	static JPanel panelLogin = new JPanel();
	static JFrame framePayment = new JFrame();
	static JPanel panelPayment = new JPanel();
	static JFrame frameLoading = new JFrame();
	static DefaultListModel<String> listData = new DefaultListModel<>();
	static JList<String> lstorder = new JList<String>(listData);
	static JLabel lblAmountPay = new JLabel(" ");
	static JLabel lblName = new JLabel(" ");
	static JLabel lblSumTemp = new JLabel(" ");
	static JLabel lblFloatError = new JLabel(" ");
	static JLabel lblFloat = new JLabel(" ");
	static JLabel lblCouponError = new JLabel(" ");
	static JLabel lblCoupon = new JLabel(" ");
	static JLabel lblChange = new JLabel(" ");
	static JLabel lblError = new JLabel(" ");
	static JLabel lblGiftCardError = new JLabel(" ");
	static JLabel lblLoadingError = new JLabel(" ");
	static JLabel lblSoldOut = new JLabel(" ");
	static JTextField txtName = new JTextField(" ", 10);
	static JTextField txtFloat = new JTextField(" ", 10);
	static JTextField txtCouponCode = new JTextField(" ", 10);
	static JTextField txtPaid = new JTextField(" ", 10);
	static JTextField txtGiftCard = new JTextField(" ", 10);
	static JButton btnComplete = new JButton("Complete");
	static JButton btnDelete = new JButton("Delete");
	static JButton btnCash = new JButton("Cash");
	static JButton btnAddCoupon = new JButton("Add Coupon");
	static JButton btnGiftCard = new JButton("Gift Card");
	static JButton btnNextOrder = new JButton("Next Order");
	static JButton btnLogin = new JButton("Login");
	static JScrollPane listScroller = new JScrollPane(lstorder);
	
	

	public Gui() {

		///////////// BEFORE ORDER //////////////					//add all components to corresponding panels and set location and size and frame layout
		panelLogin.setBackground(Color.WHITE);
		frameLogin.setSize(1920, 1080);
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLogin.setVisible(true);
		frameLogin.getContentPane().add(panelLogin);
		frameLogin.setLocationRelativeTo(null);
		panelLogin.setLayout(null);
		
		panelLogin.add(lblLoadingError);
		
		lblName = new JLabel("Enter Your Name");
		panelLogin.add(lblName);
		lblName.setBounds(760,350,100,25);

		txtName = new JTextField(" ", 10);
		txtName.setBounds(760, 375, 100, 25);
		panelLogin.add(txtName);

		lblFloat = new JLabel("Enter Float ");
		lblFloat.setBounds(960,350,100,25);
		panelLogin.add(lblFloat);

		txtFloat = new JTextField(" ", 10);
		txtFloat.setBounds(960,375,100,25);
		panelLogin.add(txtFloat);

		panelLogin.add(btnLogin);
		btnLogin.setBounds(860, 450, 100, 25);
		
		lblFloatError = new JLabel(" ");
		panelLogin.add(lblFloatError);
		lblFloatError.setBounds(830,485,200,25);

		

		/////////////// DURING ORDER /////////////////////
		panel.setBackground(Color.WHITE);
		frame.setSize(1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(false);
		frame.getContentPane().add(panel);
		frame.setLocationRelativeTo(null);
		panel.setLayout(null);

		lstorder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstorder.setBounds(850, 200, 250, 800);
		lstorder.setLayoutOrientation(JList.VERTICAL);
		lstorder.setVisibleRowCount(-1);
		listScroller.setPreferredSize(new Dimension(250, 80));
		panel.add(lstorder);
		
		lblSumTemp.setBounds(1500, 700, 225, 25);
		panel.add(lblSumTemp);
		
		panel.add(listScroller);
		btnComplete.setBounds(1500, 750, 100, 25);
		panel.add(btnComplete);

		btnDelete.setBounds(850, 165, 100, 25);
		panel.add(btnDelete);

		
		panel.add(txtCouponCode);
		txtCouponCode.setBounds(1200,200,100,25);
		
		panel.add(lblCoupon);
		lblCoupon.setBounds(1200, 175,225,25);
		lblCoupon.setText("Enter Coupon Code: ");

		panel.add(lblCouponError);
		lblCouponError.setBounds(1185, 260, 200, 25);
		
		panel.add(lblSoldOut);
		lblSoldOut.setBounds(600, 260, 300, 25);

		panel.add(btnAddCoupon);
		btnAddCoupon.setBounds(1175, 235, 150, 25);

		/////////////////// AFTER ORDER /////////////////////
		panelPayment.setBackground(Color.WHITE);
		framePayment.setSize(1920, 1080);
		framePayment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framePayment.setVisible(false);
		framePayment.getContentPane().add(panelPayment);
		framePayment.setLocationRelativeTo(null);

		panelPayment.add(lblAmountPay);

		panelPayment.add(lblChange);

		panelPayment.add(txtPaid);

		panelPayment.add(lblError);

		btnCash.setBounds(750, 750, 150, 26);
		panelPayment.add(btnCash);

		panelPayment.add(lblGiftCardError);

		panelPayment.add(txtGiftCard);

		btnGiftCard.setBounds(750, 750, 150, 26);
		panelPayment.add(btnGiftCard);

		btnNextOrder.setBounds(750, 750, 150, 26);
		panelPayment.add(btnNextOrder);

	}																	//end of Gui constructor			

}																		//end of Gui class
