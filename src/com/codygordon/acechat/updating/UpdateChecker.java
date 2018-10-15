package com.codygordon.acechat.updating;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.controllers.ConversationsController;
import com.codygordon.acechat.controllers.UserController;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.models.ChatMessage;
import com.codygordon.acechat.models.User;
import com.codygordon.acechat.util.DatabaseUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UpdateChecker {

	private DatabaseUtil db = AceChat.database;
	private UserController userController = AceChat.instance.userController;
	private ConversationsController chatController = AceChat.instance.conversationsController;
	
	public void update() {
		checkForUserChanges();
		checkForConversationChanges();
		checkForConversationMessageChanges();
	}

	private void checkForUserChanges() {
		 ArrayList<User> users = getUsersFromDatabase();
		 if(users == null) return;
		 if(users.size() != userController.getUsers().size()) {
			 userController.loadUsers();
		 }
	}
	
	private void checkForConversationMessageChanges() {
		try {
			if(AceChat.instance.user == null) return;
			if(AceChat.instance.conversationsController == null) return;
			ArrayList<Chat> chats = AceChat.instance.conversationsController.getChats();
			for(Chat chat : chats) {
				ArrayList<ChatMessage> messages = getMessagesFromDatabase(chat);
				int clientSize = messages.size();
				int serverSize = chat.messages.size();
				if(clientSize != serverSize) {
					chat.messages = messages;
					AceChat.instance.conversationsController.raiseUpdateEvent();
				}
			}
		}  catch(Exception e) { }
	}

	private void checkForConversationChanges() {
		if(chatController == null) return;
		ArrayList<Chat> chats = getConversationsFromDatabase();
		try {
			if(chats.size() != chatController.getChats().size()) {
				chatController.loadConversations();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<User> getUsersFromDatabase() {
		ArrayList<User> users = new ArrayList<User>();
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
		return users;
	}
	
	private ArrayList<Chat> getConversationsFromDatabase() {
		if(AceChat.instance.user == null) return null;
		ArrayList<Chat> chats = new ArrayList<Chat>();
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
				chats.add(chat);
			}	
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return chats;
	}
	
	private ArrayList<ChatMessage> getMessagesFromDatabase(Chat chat) {
		try {
			Type listType = new TypeToken<ArrayList<ChatMessage>>(){}.getType();
			ResultSet result = db.executeQuery("SELECT MessagesJson FROM Conversations WHERE ID = " + chat.id);
			result.next();
			String json = result.getString(1);
			return new Gson().fromJson(json, listType);
		} catch(Exception e) { 
			return null;
		}
	}
}