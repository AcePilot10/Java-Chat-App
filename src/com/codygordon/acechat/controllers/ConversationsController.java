package com.codygordon.acechat.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.util.DatabaseUtil;
import com.google.gson.Gson;

public class ConversationsController {

	private DatabaseUtil db = AceChat.database;
	
	private ArrayList<Chat> chats = new ArrayList<Chat>();
	
	public ConversationsController() {
		loadConversations();
	}
	
	@SuppressWarnings("rawtypes")
	private void loadConversations() {
		try {
			int userId = AceChat.instance.user.id;
			ResultSet result = db.executeQuery("SELECT * "
										     + "FROM Conversations "
										     + "WHERE User1Id=" + userId);
			
			ResultSet result2 = db.executeQuery("SELECT * "
				     + "FROM Conversations "
				     + "WHERE User2Id=" + userId);
			
			while(result.next()) {
				int id = result.getInt(1);
				int user1Id = result.getInt(2);
				int user2Id = result.getInt(3);
				String messagesJson = result.getString(4);
				ArrayList<?> messages = null;
				
				messages = new Gson().fromJson(messagesJson, ArrayList.class);
				
				if(messages == null) {
					messages = new ArrayList();
				}
				
				Chat chat = new Chat(id, user1Id, user2Id, messages);
				chats.add(chat);
			}
			
			while(result2.next()) {
				int id = result2.getInt(1);
				int user1Id = result2.getInt(2);
				int user2Id = result2.getInt(3);
				String messagesJson = result2.getString(4);
				ArrayList<?> messages = null;
				
				messages = new Gson().fromJson(messagesJson, ArrayList.class);
				
				if(messages == null) {
					messages = new ArrayList();
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
	
	public ArrayList<Chat> getChats() {
		return chats;
	}
}