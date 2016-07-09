package com.accenture.pi.timer;

import java.util.Timer;
import java.util.TimerTask;

import com.accenture.pi.MailNotifier;

import static com.accenture.pi.timer.Status.EMPTY;
import static com.accenture.pi.timer.Status.WEIGHlIMITS;

public class Notifier {
	private Timer timer;
	int seconds = 15 * 1000; // millis
	private Status status;
	public static boolean fireEmail = true;

	public void startTimer() {
		timer = new Timer();
		timer.schedule(new Reminder(), seconds);
	}

	class Reminder extends TimerTask {
		public void run() {
			if (shouldFireEmail()) {
				System.out.println("Fire an email");
				fireEmail = false;
				MailNotifier.mail(status.getStatus(), status.getWeight());
			} else {
				System.out.println("Not gonna fire an email now");
			}

			timer.cancel();
		}
	}

	private boolean shouldFireEmail() {
		return ((EMPTY.equals(status.getStatus()) || 
				(status.getWeight() > 0.0 && status.getWeight() < WEIGHlIMITS)) &&
				fireEmail);
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
