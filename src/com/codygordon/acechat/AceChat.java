package com.codygordon.acechat;

import java.util.Stack;

import javax.swing.JPanel;

import com.codygordon.acechat.controllers.ConversationsController;
import com.codygordon.acechat.controllers.UserController;
import com.codygordon.acechat.enums.Screen;
import com.codygordon.acechat.models.Chat;
import com.codygordon.acechat.models.User;
import com.codygordon.acechat.updating.UpdateBackgroundTask;
import com.codygordon.acechat.util.DatabaseUtil;
import com.codygordon.acechat.views.AceChatWindow;
import com.codygordon.acechat.views.ChatView;
import com.codygordon.acechat.views.ConversationsView;
import com.codygordon.acechat.views.CreateChatView;
import com.codygordon.acechat.views.LoginView;
import com.codygordon.acechat.views.RegisterView;

public class AceChat {

	private AceChatWindow mainFrame;
	private UpdateBackgroundTask backgroundTask;
	
	public static AceChat instance;
	public static DatabaseUtil database;
	
	public User user;
	public UserController userController;
	public ConversationsController conversationsController;
	public Stack<JPanel> viewHistory;
	
	public static void main(String[] args) {
		System.out.println("Initializing...");
		new AceChat();
	}
	
	public AceChat() {
		instance = this;
		database = new DatabaseUtil();
		userController = new UserController();
		backgroundTask = new UpdateBackgroundTask();
		backgroundTask.run();
		viewHistory = new Stack<JPanel>();
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
		case CREATE_CHAT:
			view = new CreateChatView();
			break;
		}
		mainFrame.contentScreen.add(view);
		mainFrame.contentScreen.repaint();
		mainFrame.revalidate();
	}
	
	public void displayScreen(JPanel view) {
		mainFrame.contentScreen.removeAll();
		viewHistory.push(view);
		mainFrame.contentScreen.add(view);
		mainFrame.contentScreen.repaint();
		mainFrame.revalidate();
	}
	
	public void displayChat(Chat chat) {
		mainFrame.contentScreen.removeAll();
		ChatView view = new ChatView(chat);
		viewHistory.push(view);
		mainFrame.contentScreen.add(view);
		mainFrame.contentScreen.repaint();
		mainFrame.revalidate();
	}

	public void executeLogin(User user) {
		this.user = user;
		conversationsController = new ConversationsController();
		displayScreen(Screen.CONVERSATIONS);
	}

	public AceChatWindow getFrame() {
		return this.mainFrame;
	}
}