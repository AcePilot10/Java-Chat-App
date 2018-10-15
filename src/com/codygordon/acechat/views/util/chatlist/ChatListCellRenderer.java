package com.codygordon.acechat.views.util.chatlist;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.models.ChatMessage;
import com.codygordon.acechat.models.User;

public class ChatListCellRenderer extends JLabel 
								  implements ListCellRenderer<ChatListItem> {

	@Override
	public Component getListCellRendererComponent(JList<? extends ChatListItem> list, ChatListItem value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		ChatListItem listItem = list.getModel().getElementAt(index);
		ChatMessage chatMessage = listItem.message;
		
		User user = AceChat.instance.userController.getUserById(chatMessage.authorId);
		String message = chatMessage.message;
		
		if(AceChat.instance.user.username.equals(user.username)) {
			//Sent from logged in user
			setHorizontalAlignment(SwingConstants.TRAILING);
			setText(message);
			setBackground(Color.YELLOW);
		} else {
			//Sent from other user
			setHorizontalAlignment(SwingConstants.LEADING);
			setText(user.username + ": " + message);
			setBackground(Color.BLUE);
		}
		
		return this;
	}
}