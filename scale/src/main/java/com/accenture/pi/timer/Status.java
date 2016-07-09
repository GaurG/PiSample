package com.accenture.pi.timer;

import com.accenture.pi.MailNotifier;

public class Status {

	private String status = "no status";
	private double weight = 0.0;
	private static final Notifier notify = new Notifier(); 
	
	public static final String EMPTY = "EMPTY";
	public static final String OVERWEIGHT = "OVERWEIGHT";
	public static final String NEGATIVE = "NEGATIVE";

	public String getStatus() {
		return status;
	}

	// TODO: Consider oz as well. currently mail fired based on gm weight
	public void setStatus(String status) {
		if(EMPTY.equals(status) || (weight > 0.0 && weight < 200)) {
			notify.setStatus(this);
			notify.startTimer();
		} else if (weight > 200) {
			Notifier.fireEmail = true;
		}
		
		this.status = status;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
