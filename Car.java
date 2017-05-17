package traficsim;

public class Car extends Object {

	private String ID;

	private String rampEntered;

	private Double speed;

	private Integer timeEnteredLane;

	private Integer timeEnteredRamp;

	private Integer timeLeftLane;

	public Car(String ramp, int index, int timeEnteredRamp) {
		this.ID = ramp + "C" + index;
		this.rampEntered = ramp;
		this.speed = 0.0;
		this.timeEnteredRamp = timeEnteredRamp;
		Simulation.addCarToHighway(this);
	}

	public String getID() {
		return this.ID;
	}

	public String getRampEntered() {
		return this.rampEntered;
	}

	public Double getSpeed() {
		return this.speed;
	}

	public Integer getTimeEnteredLane() {
		return this.timeEnteredLane;
	}

	public Integer getTimeEnteredRamp() {
		return this.timeEnteredRamp;
	}

	public Integer getTimeLeftLane() {
		return this.timeLeftLane;
	}

	public Integer getTransitTime() {
		if (this.timeLeftLane == null) {
			return Integer.MAX_VALUE;
		}
		return this.timeLeftLane - this.timeEnteredRamp;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setRampEntered(String rampEntered) {
		this.rampEntered = rampEntered;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public void setTimeEnteredLane(Integer timeEnteredLane) {
		this.timeEnteredLane = timeEnteredLane;
	}

	public void setTimeEnteredRamp(Integer timeEnteredRamp) {
		this.timeEnteredRamp = timeEnteredRamp;
	}

	public void setTimeLeftLane(Integer timeLeftLane) {
		this.timeLeftLane = timeLeftLane;
	}

	@Override
	public String toString() {
		return "\tCAR:" + this.getID() + "\tSpeed="
				+ this.getSpeed().intValue() + "\tRampEntered="
				+ this.getRampEntered() + "\tTimeEnteredRamp="
				+ this.getTimeEnteredRamp() + "\tTimeEnteredLane="
				+ this.getTimeEnteredLane();
	}

}
