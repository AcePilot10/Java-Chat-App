package com.codygordon.acechat.controllers;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.models.ChatMessage;
import com.codygordon.acechat.util.DatabaseUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConversationsController {

	private DatabaseUtil db = AceChat.database;
	
	private ArrayList<Chat> chats = new ArrayList<Chat>();
	
	public ConversationsController() {
		loadConversations();
	}
	
	private void loadConversations() {
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
	}

	public void openChat(Chat chat) {
		System.out.println("Opening chat...");	
	}
	
	public void updateChat(Chat chat) {
		String messagesJson = new Gson().toJson(chat.messages);
		String query = "UPDATE Conversations "
					 + "SET MessagesJson = '" + messagesJson + "' "
			 		 + "WHERE ID = " + chat.id;
		db.executeUpdate(query);
		System.out.println("Chat with id: " + chat.id + " was updated.");	
	}
	
	public ArrayList<Chat> getChats() {
		return chats;
	}
}