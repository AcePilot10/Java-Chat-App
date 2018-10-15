package com.codygordon.acechat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUtil {

	private static final String PASSWORD = "Airplane10";
	
	private Connection connection;
	
	public DatabaseUtil() {
		establishConnection();
	}
	
	private void establishConnection() {
		System.out.println("Attempting to establish connection to server...");
		String connectionString = "jdbc:sqlserver://javachatapp.database.windows.net:1433;database=JavaChatApp;user=CodyJG10@javachatapp;password=" + PASSWORD + ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
		try {
			connection = DriverManager.getConnection(connectionString);
			System.out.println("Succesfully connected to the server");
		} catch(Exception e) {
			System.out.println("There was an error establishing a connection to the server: " + e.getMessage());
		}
	}
	
	public ResultSet executeQuery(String query) {
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			return statement.executeQuery();
		} catch(Exception e) {
			System.out.println("Error executing query: " + e.getMessage());
			return null;
		}
	}
	
	public void executeUpdate(String query) {
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.executeUpdate();
			System.out.println("Succesfully executed update!");
		} catch(Exception e) {
			System.out.println("Error executing update: " + e.getMessage());
		}
	}
}