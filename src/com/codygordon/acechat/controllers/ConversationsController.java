package com.codygordon.acechat.controllers;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.interfaces.IUpdateListener;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.models.ChatMessage;
import com.codygordon.acechat.models.User;
import com.codygordon.acechat.models.UserCredentials;
import com.codygordon.acechat.util.DatabaseUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConversationsController {

	private DatabaseUtil db = AceChat.database;
	
	private ArrayList<Chat> chats = new ArrayList<Chat>();
	private ArrayList<IUpdateListener> updateListeners = new ArrayList<IUpdateListener>();
	
	public ConversationsController() {
		loadConversations();
	}
	
	public void loadConversations() {
		chats.clear();
		try {
			int userId = AceChat.instance.user.id;
			ResultSet result = db.executeQuery("SELECT * "
										     + "FROM Conversations "
										     + "WHERE User1Id=" + userId + " "
										     + "OR "
										     + "User2Id=" + userId);
			while(result.next()) {
				int id = result.getInt(1);
				int user1Id = result.getInt(2);
				int user2Id = result.getInt(3);
				String messagesJson = result.getString(4);
				ArrayList<ChatMessage> messages = null;
				Type listType = new TypeToken<ArrayList<ChatMessage>>(){}.getType();
				
				try {
					messages = new Gson().fromJson(messagesJson, listType);
				} catch(Exception e) {
					messages = new ArrayList<ChatMessage>();
				}
				
				Chat chat = new Chat(id, user1Id, user2Id, messages);
				addChat(chat);
			}	
			raiseUpdateEvent();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateChat(Chat chat) {
		String messagesJson = new Gson().toJson(chat.messages);
		String query = "UPDATE Conversations "
					 + "SET MessagesJson = '" + messagesJson + "' "
			 		 + "WHERE ID = " + chat.id;
		db.executeUpdate(query);
		System.out.println("Chat with id: " + chat.id + " was updated.");	
	}
	
	public void createChat(String username) {
		if(AceChat.instance.userController.userExists(new UserCredentials(username, ""))) {
			User recipient = AceChat.instance.userController.getUserByUsername(username);
			if(!chatExistsWithUser(recipient)) {
				System.out.println("Attempting to create chat...");
				User currentUser = AceChat.instance.user;
				
				String query = "INSERT INTO Conversations "
							 + "(User1ID, User2ID, MessagesJson) "
							 + "values("
							 + currentUser.id + ", " + recipient.id + ", '[]')";
				db.executeUpdate(query);
				loadConversations();
				Chat chat = getChat(username);
				AceChat.instance.displayChat(chat);
				System.out.println("Succesfully created new chat!");
			} else {
				System.out.println("Chat already exists with that user!");
				Chat chat = getChat(username);
				AceChat.instance.displayChat(chat);
			}
		} else {
			System.out.println("Attempted to create chat with user that doesn't exist!");
			JOptionPane.showMessageDialog(AceChat.instance.getFrame(), "Couldn't find user with username: " + username, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Chat getChat(String username) {
		for(Chat chat : chats) {
			User user1 = AceChat.instance.userController.getUserById(chat.user1Id);
			User user2 = AceChat.instance.userController.getUserById(chat.user2Id);
			String currentUsername = AceChat.instance.user.username;
			if(user1.username.equals(username) && user2.username.equals(currentUsername)) {
				return chat;
			} else if(user1.username.equals(currentUsername) && user2.username.equals(username)) {
				return chat;
			}
		}
		return null;
	}
	
	public Chat getChat(int chatId) {
		for(Chat chat : chats) {
			if(chat.id == chatId) {
				return chat;
			}
		}
		return null;
	}
	
	private boolean chatExistsWithUser(User recipient) {
		for(Chat chat : chats) {
			User currentUser = AceChat.instance.user;
			if(chat.user1Id == currentUser.id && chat.user2Id == recipient.id) {
				return true;
			} else if(chat.user2Id == currentUser.id && chat.user1Id == recipient.id) {
				return true;
			}
		}
		return false;
	} 
	
	private void addChat(Chat chat) {
		chats.add(chat);
		raiseUpdateEvent();
	}
	
	public void registerListener(IUpdateListener listener) {
		updateListeners.add(listener);
	}
	
	public void raiseUpdateEvent() {
		for(IUpdateListener listener : updateListeners) {
			if(listener == null) {
				updateListeners.remove(listener);
			}
			listener.update();
		}
	}
	
	public ArrayList<Chat> getChats() {
		return chats;
	}
}