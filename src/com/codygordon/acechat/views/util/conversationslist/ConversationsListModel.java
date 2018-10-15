package com.codygordon.acechat.views.util.conversationslist;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import com.codygordon.acechat.models.Chat;

public class ConversationsListModel extends AbstractListModel<ConversationsListItem> {

	ArrayList<ConversationsListItem> items = new ArrayList<ConversationsListItem>();

	public ConversationsListModel(ArrayList<Chat> chats) {
		for(Chat chat : chats) {
			ConversationsListItem item = new ConversationsListItem();
			item.chat = chat;
			items.add(item);
		}
	}
	
	@Override
	public int getSize() {
		return items.size();
	}

	@Override
	public ConversationsListItem getElementAt(int index) {
		return items.get(index);
	}
}