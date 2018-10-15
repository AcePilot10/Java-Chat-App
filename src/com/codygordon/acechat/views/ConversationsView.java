package com.codygordon.acechat.views;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.views.util.conversationslist.ConversationsListCellRenderer;
import com.codygordon.acechat.views.util.conversationslist.ConversationsListItem;
import com.codygordon.acechat.views.util.conversationslist.ConversationsListModel;

public class ConversationsView extends JPanel {
	
	private JList<ConversationsListItem> listConversations;
	
	public ConversationsView() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 430, 278);
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		ArrayList<Chat> chats = AceChat.instance.conconversationsController.getChats();
		
		listConversations = new JList<ConversationsListItem>();
		listConversations.setModel(new ConversationsListModel(chats));
		listConversations.setCellRenderer(new ConversationsListCellRenderer());
		
		listConversations.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				itemSelected();
			}
		});
		
		panel.add(listConversations);
	}
	
	private void itemSelected() {
		try {
			int selectedIndex = listConversations.getSelectedIndex();
			Chat chat = listConversations.getModel().getElementAt(selectedIndex).chat;
			AceChat.instance.displayChat(chat);
		} catch(Exception e) { }
	}
}