package com.codygordon.acechat;

import javax.swing.JPanel;

import com.codygordon.acechat.controllers.ConversationsController;
import com.codygordon.acechat.controllers.UserController;
import com.codygordon.acechat.enums.Screen;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.models.User;
import com.codygordon.acechat.util.DatabaseUtil;
import com.codygordon.acechat.views.AceChatWindow;
import com.codygordon.acechat.views.ChatView;
import com.codygordon.acechat.views.ConversationsView;
import com.codygordon.acechat.views.LoginView;
import com.codygordon.acechat.views.RegisterView;

public class AceChat {

	private AceChatWindow mainFrame;
	
	public static AceChat instance;
	public static DatabaseUtil database;
	
	public User user;
	public UserController userController;
	public ConversationsController conconversationsController;
	
	public static void main(String[] args) {
		System.out.println("Initializing...");
		new AceChat();
	}
	
	public AceChat() {
		instance = this;
		database = new DatabaseUtil();
		userController = new UserController();
		initFrame();
	}
	
	private void initFrame() {
		mainFrame = new AceChatWindow();
		LoginView view = new LoginView();
		mainFrame.contentScreen.add(view);
		mainFrame.setVisible(true);
	}
	
	public void displayScreen(Screen screen) {
		mainFrame.contentScreen.removeAll();
		JPanel view = null;
		switch(screen) {
		case LOGIN:
			view = new LoginView();
			break;
		case REGISTER:
			view = new RegisterView();
			break;
		case CONVERSATIONS:
			view = new ConversationsView();
			break;
		case CHAT:
			
			break;
		case CREATE_CHAT:
			
			break;
		}
		mainFrame.contentScreen.add(view);
		mainFrame.revalidate();
	}
	
	public void displayChat(Chat chat) {
		mainFrame.contentScreen.removeAll();
		ChatView view = new ChatView(chat);
		mainFrame.contentScreen.add(view);
		mainFrame.revalidate();
	}

	public void executeLogin(User user) {
		this.user = user;
		conconversationsController = new ConversationsController();
		displayScreen(Screen.CONVERSATIONS);
	}
}