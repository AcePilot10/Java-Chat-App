package com.codygordon.acechat.views;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.controllers.UserController;
import com.codygordon.acechat.enums.Screen;
import com.codygordon.acechat.interfaces.IUpdateListener;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.models.ChatMessage;
import com.codygordon.acechat.models.User;
import com.codygordon.acechat.views.util.chatlist.ChatListCellRenderer;
import com.codygordon.acechat.views.util.chatlist.ChatListItem;
import com.codygordon.acechat.views.util.chatlist.ChatListModel;

public class ChatView extends JPanel implements IUpdateListener {

	public Chat chat;
	private JTextField txtInput;
	private JList<ChatListItem> listChat;
	private JButton btnBack;
	private JLabel lblTitle;
	private UserController controller = AceChat.instance.userController;
	
	public ChatView(Chat chat) {
		AceChat.instance.conversationsController.registerListener(this);
		
		setSize(650, 430);
		setBackground(SystemColor.control);
		this.chat = chat;
	 
		setLayout(null);
		
		listChat = new JList<ChatListItem>();
		listChat.setBackground(SystemColor.controlDkShadow);
		listChat.setBounds(85, 61, 480, 287);
		listChat.setModel(new ChatListModel(chat));
		listChat.setCellRenderer(new ChatListCellRenderer());
		add(listChat);
		
		txtInput = new JTextField();
		txtInput.setBounds(51, 379, 406, 40);
		add(txtInput);
		txtInput.setColumns(10);
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendButtonClicked();
			}
		});
		btnSend.setBounds(508, 379, 89, 40);
		add(btnSend);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AceChat.instance.displayScreen(Screen.CONVERSATIONS);
			}
		});
		btnBack.setBounds(6, 24, 69, 23);
		add(btnBack);
		
		lblTitle = new JLabel("Title");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Ariel", Font.PLAIN, 25));
		lblTitle.setBounds(204, 28, 242, 22);
		
		User user1 = controller.getUserById(chat.user1Id);
		User user2 = controller.getUserById(chat.user2Id);
		int currentUser = AceChat.instance.user.id;
		String title = "";
		if(user1.id == currentUser) {
			title = user2.username;
		} else {
			title = user1.username;
		}
		lblTitle.setText(title);
		add(lblTitle);
	}
	
	private void sendButtonClicked() {
		String text = txtInput.getText();
		if(text.isEmpty()) return;
		int userId = AceChat.instance.user.id;
		ChatMessage msg = new ChatMessage(userId, text);
		chat.messages.add(msg);
		txtInput.setText("");
		AceChat.instance.conversationsController.updateChat(chat);
		update();
	}
	
	public void update() {
		listChat.setModel(new ChatListModel(chat));
	}
}