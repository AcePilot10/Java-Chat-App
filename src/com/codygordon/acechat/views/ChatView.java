package com.codygordon.acechat.views;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.models.ChatMessage;
import com.codygordon.acechat.views.util.chatlist.ChatListCellRenderer;
import com.codygordon.acechat.views.util.chatlist.ChatListItem;
import com.codygordon.acechat.views.util.chatlist.ChatListModel;

public class ChatView extends JPanel {

	public Chat chat;
	private JTextField txtInput;
	private JList<ChatListItem> listChat;
	
	public ChatView(Chat chat) {
		setBackground(Color.BLACK);
		this.chat = chat;
	 
		setLayout(null);
		
		listChat = new JList<ChatListItem>();
		listChat.setBackground(SystemColor.controlDkShadow);
		listChat.setBounds(10, 11, 430, 227);
		updateList();
		listChat.setCellRenderer(new ChatListCellRenderer());
		add(listChat);
		
		txtInput = new JTextField();
		txtInput.setBounds(10, 249, 315, 40);
		add(txtInput);
		txtInput.setColumns(10);
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendButtonClicked();
			}
		});
		btnSend.setBounds(351, 249, 89, 40);
		add(btnSend);
	}
	
	private void sendButtonClicked() {
		String text = txtInput.getText();
		if(text.isEmpty()) return;
		int userId = AceChat.instance.user.id;
		ChatMessage msg = new ChatMessage(userId, text);
		chat.messages.add(msg);
		updateList();
		AceChat.instance.conconversationsController.updateChat(chat);
	}
	
	private void updateList() {
		ArrayList<ChatListItem> chatMessages = new ArrayList<ChatListItem>();
		chat.messages.forEach(x -> chatMessages.add(new ChatListItem(x)));
		listChat.setModel(new ChatListModel(chatMessages));
	}
}