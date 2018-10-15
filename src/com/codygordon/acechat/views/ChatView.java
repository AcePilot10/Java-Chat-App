package com.codygordon.acechat.views;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;

import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.views.util.chatlist.ChatListCellRenderer;
import com.codygordon.acechat.views.util.chatlist.ChatListItem;
import com.codygordon.acechat.views.util.chatlist.ChatListModel;

public class ChatView extends JPanel {

	public Chat chat;
	
	public ChatView(Chat chat) {
		this.chat = chat;
		setLayout(new GridLayout(1, 0, 0, 0));
	 
		ArrayList<ChatListItem> chatMessages = new ArrayList<ChatListItem>();
		chat.messages.forEach(x -> chatMessages.add(new ChatListItem(x)));
		
		JList<ChatListItem> listChat = new JList<ChatListItem>();
		listChat.setModel(new ChatListModel(chatMessages));
		listChat.setCellRenderer(new ChatListCellRenderer());
		add(listChat);
	}
}