package com.codygordon.acechat.views.util.conversationslist;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import com.codygordon.acechat.AceChat;
import com.codygordon.acechat.interfaces.IUpdateListener;
import com.codygordon.acechat.models.Chat;

public class ConversationsListModel extends AbstractListModel<ConversationsListItem> implements IUpdateListener  {

	public static ConversationsListModel singleton;
	
	private ArrayList<ConversationsListItem> items = new ArrayList<ConversationsListItem>();

	public ConversationsListModel(ArrayList<ConversationsListItem> items) {
		this.items = items;
	}
	
	@Override
	public int getSize() {
		return items.size();
	}

	@Override
	public ConversationsListItem getElementAt(int index) {
		return items.get(index);
	}

	@Override
	public void update() {
		ArrayList<ConversationsListItem> items = new ArrayList<ConversationsListItem>();
		for(Chat chat : AceChat.instance.conversationsController.getChats()) {
			ConversationsListItem item = new ConversationsListItem();
			item.chat = chat;
			items.add(item);
		}
		System.out.println("Updating conversations!");
		this.items = items;
	}
}