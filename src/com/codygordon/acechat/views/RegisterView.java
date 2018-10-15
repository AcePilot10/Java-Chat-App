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

public class RegisterView extends JPanel {

private static final long serialVersionUID = 1L;
	
	private JTextField txtUsername;
	private JTextField txtPassword;

	public RegisterView() {
		setLayout(null);
		setSize(650, 430);
		
		JLabel lblTitle = new JLabel("Register");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(279, 29, 92, 21);
		add(lblTitle);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(199, 79, 252, 48);
		add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(293, 156, 64, 14);
		add(lblUsername);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(199, 199, 252, 48);
		add(txtPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(293, 276, 64, 14);
		add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(280, 371, 89, 23);
		
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginButtonClicked();
			}
		});
		
		add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(280, 319, 89, 23);
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registerButtonClicked();
			}
		});
		add(btnRegister);
	}
	
	private void registerButtonClicked() {
		System.out.println("Attempting to register user...");
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		UserCredentials credentials = new UserCredentials(username, password);
		AceChat.instance.userController.registerUser(credentials);
	}
	
	private void loginButtonClicked() {
		AceChat.instance.displayScreen(Screen.LOGIN);
	}
}