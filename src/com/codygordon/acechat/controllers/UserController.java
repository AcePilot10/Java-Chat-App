package com.codygordon.acechat.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.enums.Screen;
import com.codygordon.acechat.models.User;
import com.codygordon.acechat.models.UserCredentials;
import com.codygordon.acechat.util.DatabaseUtil;

public class UserController {

	private DatabaseUtil db = AceChat.database;
	
	private ArrayList<User> users;
	
	public UserController() {
		users = new ArrayList<User>();
		loadUsers();
	}
	
	public void LoginUser(UserCredentials credentials) {
		if(userExists(credentials)) {
			User user = getUserByUsername(credentials.username);
			if(user.password.equals(credentials.password)) {
				AceChat.instance.executeLogin(user);
				System.out.println("Succesfully logged in");
			} else {
				System.out.println("Password used was incorrect!");
				JOptionPane.showMessageDialog(AceChat.instance.getFrame(), "Username or password was incorrect.", "Login Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			System.out.println("Could not locate user");
			JOptionPane.showMessageDialog(AceChat.instance.getFrame(), "Username or password was incorrect.", "Login Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void registerUser(UserCredentials credentials) {
		if(!userExists(credentials)) {
			System.out.println("Attempting to save user to server...");
			saveUser(credentials);
			AceChat.instance.displayScreen(Screen.LOGIN);
			JOptionPane.showMessageDialog(AceChat.instance.getFrame(), "You have succesfully registered! You may now login.", "Registration Succesful", JOptionPane.PLAIN_MESSAGE);
		} else {
			System.out.println("Attempted to register with username that is already in use");
			JOptionPane.showMessageDialog(AceChat.instance.getFrame(), "That username is taken!", "Registration Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public boolean userExists(UserCredentials credentials) {
		for(User user : users) {
			if(user.username.equals(credentials.username)) {
				return true;
			}
		}
		return false;
	}		
	
	public User getUserByUsername(String username) {
		for(User user : users) {
			if(user.username.equals(username)) {
				return user;
			}
		}
		return null;
	}

	public User getUserById(int id) {
		for(User user : users) {
			if(user.id == id) {
				return user;
			}
		}
		return null;
	}
	
	public void loadUsers() {
		users.clear();
		ResultSet result = db.executeQuery("SELECT * FROM Users");  
		try {
			
			while(result.next()) {
				int id = result.getInt(1);
				String username = result.getString(2);
				String password = result.getString(3);
				
				User user = new User();
				user.id = id;
				user.password = password;
				user.username = username;
				
				users.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void saveUser(UserCredentials credentials) {
		db.executeUpdate("INSERT INTO Users (Username, Password) "
				+ "VALUES ('" + credentials.username + "', '" + credentials.password + "')");
		loadUsers();
		System.out.println("Succesfully saved user to server!");
	}
	
	public ArrayList<User> getUsers() {
		return this.users;
	}
}