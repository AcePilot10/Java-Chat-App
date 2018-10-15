package com.codygordon.acechat.views.util.chatlist;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.interfaces.IUpdateListener;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.models.ChatMessage;

public class ChatListModel extends AbstractListModel<ChatListItem> implements IUpdateListener {

	private Chat chat;
	
	private ArrayList<ChatListItem> items = new ArrayList<ChatListItem>();
	
	public ChatListModel(Chat chat) {
		chat.messages.forEach(x -> items.add(new ChatListItem(x)));
		this.chat = chat;
	}
	
	@Override
	public int getSize() {
		return items.size();
	}

	@Override
	public ChatListItem getElementAt(int index) {
		return items.get(index);
	}

	@Override
	public void update() {
		Chat chatFromDatabase = AceChat.instance.conversationsController.getChat(chat.id);
		int serverSize = chatFromDatabase.messages.size();
		if(items.size() != serverSize) {
			ArrayList<ChatListItem> items = new ArrayList<ChatListItem>();
			chatFromDatabase.messages.forEach(x -> items.add(new ChatListItem(new ChatMessage(x.authorId, x.message))));
			this.items = items;
			System.out.println("Updated chats");
		}
	}
}