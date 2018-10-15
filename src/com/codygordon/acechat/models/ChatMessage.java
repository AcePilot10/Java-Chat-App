package com.codygordon.acechat.models;

public class ChatMessage {

	public int authorId;
	public String message;
	
	public ChatMessage(int authorId, String message) {
		this.authorId = authorId;
		this.message = message;
	}
}