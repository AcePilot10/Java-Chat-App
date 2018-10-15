package com.codygordon.acechat.views;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.enums.Screen;
import com.codygordon.acechat.interfaces.IUpdateListener;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.views.util.conversationslist.ConversationsListCellRenderer;
import com.codygordon.acechat.views.util.conversationslist.ConversationsListItem;
import com.codygordon.acechat.views.util.conversationslist.ConversationsListModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ConversationsView extends JPanel implements IUpdateListener {
		
	private JList<ConversationsListItem> listConversations;
	
	public ConversationsView() {
		AceChat.instance.conversationsController.registerListener(this);
		
		setLayout(null);
		setSize(650, 430);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(72, 95, 506, 324);
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		ArrayList<Chat> chats = AceChat.instance.conversationsController.getChats();
		ArrayList<ConversationsListItem> items = new ArrayList<ConversationsListItem>();
		for(Chat chat : chats) {
			ConversationsListItem item = new ConversationsListItem();
			item.chat = chat;
			items.add(item);
		}
		
		listConversations = new JList<ConversationsListItem>();
		listConversations.setModel(new ConversationsListModel(items));
		listConversations.setCellRenderer(new ConversationsListCellRenderer());
		
		listConversations.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				itemSelected();
			}
		});
		
		panel.add(listConversations);
		
		JButton btnNewChat = new JButton("New Chat");
		btnNewChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newChatButtonClicked();
			}
		});
		btnNewChat.setBounds(280, 61, 89, 23);
		add(btnNewChat);
		
		JLabel lblTitle = new JLabel("Conversations");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(237, 11, 176, 38);
		add(lblTitle);
	}
	
	private void itemSelected() {
		try {
			int selectedIndex = listConversations.getSelectedIndex();
			Chat chat = listConversations.getModel().getElementAt(selectedIndex).chat;
			AceChat.instance.displayChat(chat);
		} catch(Exception e) { }
	}
	
	private void newChatButtonClicked() {
		AceChat.instance.displayScreen(Screen.CREATE_CHAT);
	}
	
	public void update() {
		ArrayList<Chat> chats = AceChat.instance.conversationsController.getChats();
		ArrayList<ConversationsListItem> items = new ArrayList<ConversationsListItem>();
		for(Chat chat : chats) {
			ConversationsListItem item = new ConversationsListItem();
			item.chat = chat;
			items.add(item);
		}
		listConversations.setModel(new ConversationsListModel(items));
	}
}