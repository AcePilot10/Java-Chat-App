package com.codygordon.acechat.views.util.conversationslist;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.controllers.UserController;
import com.codygordon.acechat.models.Chat;

public class ConversationsListCellRenderer extends JLabel 
										   implements ListCellRenderer<ConversationsListItem> {

	@Override
	public Component getListCellRendererComponent(JList<? extends ConversationsListItem> list,
			ConversationsListItem value, int index, boolean isSelected, boolean cellHasFocus) {
		
		ConversationsListItem listItem = list.getModel().getElementAt(index);
		Chat chat = listItem.chat;
		
		UserController userController = AceChat.instance.userController;
		String user1Name = userController.getUserById(chat.user1Id).username;
		String user2Name = userController.getUserById(chat.user2Id).username;
		
		String currentUsername = AceChat.instance.user.username;
		
		String textToDisplay = "";
		if(user1Name.equals(currentUsername)) {
			textToDisplay = user2Name;
		} else {
			textToDisplay = user1Name;
		}
		
		setText(textToDisplay);
		setHorizontalAlignment(SwingConstants.CENTER);
		return this;
	}
}