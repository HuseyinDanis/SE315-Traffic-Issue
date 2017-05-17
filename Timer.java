package traficsim;

public class Timer implements Runnable {

	private int timer;

	public Timer(int timer) {
		this.timer = timer;
	}

	public Thread createThread() {
		return new Thread(this, "Timer");
	}

	public int getTimeLeft() {
		return this.timer;
	}

	public void run() {

		while (this.timer >= 0) {
			try {
				Thread.sleep(1000);

			} catch (final InterruptedException e) {
				e.getMessage();
			}

			this.timer -= 1;
		}
		Thread.yield();
	}

}
