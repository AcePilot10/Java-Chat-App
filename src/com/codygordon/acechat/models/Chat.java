package com.codygordon.acechat.models;

import java.util.ArrayList;

public class Chat {
	
	public int id;
	public int user1Id;
	public int user2Id;
	public ArrayList<ChatMessage> messages;
	
	public Chat() { }
	
	@SuppressWarnings("unchecked")
	public Chat(int id, int user1Id, int user2Id, @SuppressWarnings("rawtypes") ArrayList messages) {
		this.id = id;
		this.user1Id = user1Id;
		this.user2Id = user2Id;
		this.messages = messages;
	}
}