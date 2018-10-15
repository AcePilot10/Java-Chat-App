package com.codygordon.acechat.models;

public class User {

	public String username;
	public String password;
	public int id;
	
	public User() { }
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}