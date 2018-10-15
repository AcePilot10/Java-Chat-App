package com.codygordon.acechat.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.enums.Screen;
import com.codygordon.acechat.models.UserCredentials;

public class LoginView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField txtUsername;
	private JTextField txtPassword;
	
	public LoginView() {
		setLayout(null);
		
		JLabel lblTitle = new JLabel("Login");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(230, 39, 92, 21);
		add(lblTitle);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(158, 114, 252, 48);
		add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(244, 173, 64, 14);
		add(lblUsername);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(158, 225, 252, 48);
		add(txtPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(244, 284, 64, 14);
		add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginButtonClicked();
			}
		});
		btnLogin.setBounds(233, 323, 89, 23);
		add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerButtonClicked();
			}
		});
		btnRegister.setBounds(233, 371, 89, 23);
		add(btnRegister);
	}
	
	private void loginButtonClicked() {
		UserCredentials credentials = new UserCredentials(txtUsername.getText(), txtPassword.getText());
		AceChat.instance.userController.LoginUser(credentials);
		System.out.println("Logging in...");
	}
	
	private void registerButtonClicked() {
		AceChat.instance.displayScreen(Screen.REGISTER);
	}
}