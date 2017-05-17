package traficsim;

import java.util.ArrayList;
import java.util.List;

import traficsim.event.MeteringEventGenerator;
import traficsim.monitorobj.SynchronizedList;

public class Simulation {

	public static final int MAXLANESIZE = 75;

	public static final int MAXRAMPSIZE = 25;

	public static final int NUMRAMPS = 5;

	public static final int TIMETORUN = 10;

	public static final int WAITBETWEENMERGETRIES = 79; // Wait a set period of

	private static final List<Car> highwayCars = new ArrayList<Car>(TIMETORUN
			* MAXRAMPSIZE * NUMRAMPS + MAXLANESIZE); // Hold a list of all

	private static final Timer timer = new Timer(TIMETORUN);

	private static final Thread[] ourThreads = new Thread[5 + 1 + 1 + 1];

	//private static final Reactor reactor = new Reactor();

	public static void addCarToHighway(Car c) {
		highwayCars.add(c);
	}

	public static int getCurrentTime() {
		return TIMETORUN - getTimer().getTimeLeft();

	}

	public static Timer getTimer() {
		return timer;
	}

	public static void main(String[] args) {
		final LaneRamps ramps = new LaneRamps(NUMRAMPS);
		final Lane lane = new Lane();
		ramps.setLane(lane);
		//added new code
		final MeteringEventGenerator eventGenerator = MeteringEventGenerator
				.getInstance();
		final SynchronizedList rampLst = new SynchronizedList(ramps.getRamps());
		eventGenerator.setRampMap(rampLst);
		Thread eventThread = eventGenerator.createThread();

		eventThread.start();

		final Thread laneThread = lane.createThread();
		final Thread[] rampThreads = ramps.createThreadPool();
		final Thread timerThread = timer.createThread();

		//init our threads
		ourThreads[0] = eventThread;
		ourThreads[1] = laneThread;
		ourThreads[2] = timerThread;
		int idx = 3;
		for (Thread thread : rampThreads) {
			ourThreads[idx] = thread;
			idx++;
		}

		timerThread.start();
		laneThread.start();
		for (final Thread thread : rampThreads) {
			thread.start();
		}

		waitForThreadsToFinish();

		// Display a summary of all the cars that tried to travel
		int carsEnterHighway = 0;
		int carsLeftHighway = 0;
		int carsAttemptOnHighway = 0;
		int totalTravelTime = 0;
		for (final Car c : highwayCars) {
			if (c.getTimeEnteredLane() != null) {
				carsEnterHighway++;
			}
			if (c.getTimeLeftLane() != null) {
				carsLeftHighway++;
			}
			if (c.getTransitTime() != Integer.MAX_VALUE) {
				totalTravelTime += c.getTransitTime();
			}
			carsAttemptOnHighway++;

		}

		System.out.println("\nFinal Summary:");
		System.out.println("\tCars attempting to get on Highway:\t"
				+ carsAttemptOnHighway);
		System.out.println("\tCars that got on Highway:\t" + carsEnterHighway);
		System.out.println("\tCars that got off Highway:\t" + carsLeftHighway);
		System.out.println("\tAverage Travel Time:\t"
				+ ((float) totalTravelTime / carsLeftHighway));
		System.out
				.println("\t% of Arrived Cars:\t"
						+ (int) (((float) carsLeftHighway / carsAttemptOnHighway) * 100.0)
						+ "%");

	}

	private static void waitForThreadsToFinish() {
		for (int i = 0; i < ourThreads.length; i++) {
			Thread thread = ourThreads[i];
			while (thread.isAlive()) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.getMessage();
				}
			}

		}
	}
}
