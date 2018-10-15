package com.codygordon.acechat.updating;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateBackgroundTask extends Thread {

	public static final int REPEAT_RATE = 1000;
	
	private Timer timer;
	private UpdateChecker checker;
	
	@Override
	public void run() {
		checker = new UpdateChecker();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				checker.update();
			}
		}, REPEAT_RATE, REPEAT_RATE);
	}
}