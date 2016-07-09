package com.accenture.pi.timer;

import java.util.Timer;
import java.util.TimerTask;

import static com.accenture.pi.timer.Status.EMPTY;

public class Notifier {
	private Timer timer;
	int seconds = 15 * 1000; // millis
	private Status status;

	/*public Notifier() {
		timer = new Timer();
		timer.schedule(new Reminder(), seconds);
	}*/

	public Notifier(Status status) {
		timer = new Timer();
		this.status = status;
		timer.schedule(new Reminder(), seconds);
	}

	class Reminder extends TimerTask {
		public void run() {
			if (EMPTY.equals(status.getStatus())) {
				System.out.println("Fire an email");
			}

			timer.cancel();
		}
	}

	// public static void main(String args[]) { new Notifier(); }

}
