package org.eclipse.concierge.example.parking_lot.simulation;

import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {

	Timer timer;

	public TimerDemo() {
		timer = null;
	}

	public void startTimer() {
		
		System.out.println("Inside StartTimer.");
		if (timer == null) {
			System.out.println("Starting Timer.");
			timer = new Timer(true);
			timer.schedule(new RemindTask(),
					0,        //initial delay
					5*1000);  //subsequent rate
			System.out.println("Timer Started.");
		} else {
			System.out.println("Timer already started.");
		}
		
	}
	
	public void stopTimer() {
		System.out.println("Inside Stop Timer");
		if (timer != null) {
			System.out.println("Stoppin Timer");
			timer.cancel();
			timer = null;
			System.out.println("Timer Stopped.");
		} else {
			System.out.println("Timer not started.");
		}	
		
	}

	class RemindTask extends TimerTask {
		public void run() {
			System.out.format("Timer Event\n");
		}
	}   

}