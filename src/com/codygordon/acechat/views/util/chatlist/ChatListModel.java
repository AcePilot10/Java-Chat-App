package com.codygordon.acechat.views.util.chatlist;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class ChatListModel extends AbstractListModel<ChatListItem>{

	private ArrayList<ChatListItem> items;
	
	public ChatListModel(ArrayList<ChatListItem> items) {
		this.items = items;
	}
	
	@Override
	public int getSize() {
		return items.size();
	}

	@Override
	public ChatListItem getElementAt(int index) {
		return items.get(index);
	}
}