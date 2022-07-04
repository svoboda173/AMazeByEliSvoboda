package edu.wm.cs.cs301.EliSvoboda.gui;

import edu.wm.cs.cs301.EliSvoboda.generation.CardinalDirection;
import edu.wm.cs.cs301.EliSvoboda.gui.Constants.UserInput;

/**
 * 
 * Responsibilities:
 * Turns, moves, rotates position in maze
 * Manages distance sensors and robot drivers
 * 
 * Collaborators:
 * Control (feeds BasicRobot info about maze from MazeContainer and StatePlaying objects)
 * BasicSensors
 * WallFollower and Wizard (affected by)
 * 
 * @author Eli Svoboda
 *
 */

public class BasicRobot implements Robot {
	
	final float FULLROTATIONENERGYUSED = 12;
	final float STEPFORWARDENERGYUSED = 4;
	final float INITIALBATTERYLEVEL = 2000;
	
	DistanceSensor leftSensor;
	DistanceSensor rightSensor;
	DistanceSensor forwardSensor;
	DistanceSensor backwardSensor;

	State currentState;
	
	float[] batteryLevel = {INITIALBATTERYLEVEL};
	
	private boolean hasStopped = false;

	public void setState(State state) {
		// Store Control parameter into instance variable
		currentState = state;
	}

	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
		// Store DistanceSensor reference in the corresponding sensor instance variable
		switch (mountedDirection) {
		case LEFT:
			leftSensor = sensor;
			leftSensor.setSensorDirection(Direction.LEFT);
			leftSensor.setMaze(GeneratingActivity.maze);
			break;
		case FORWARD:
			forwardSensor = sensor;
			forwardSensor.setSensorDirection(Direction.FORWARD);
			forwardSensor.setMaze(GeneratingActivity.maze);
			break;
		}
		
	}

	@Override
	public int[] getCurrentPosition() {
		/*
		 * Return result of getCurrentPosition() called on Control instance variable
		 */
		return ((StatePlaying)currentState).getCurrentPosition();	}

	@Override
	public CardinalDirection getCurrentDirection() {
		/*
		 *  Return result of getCurrentDirection() called on Control instance variable
		 */
		return ((StatePlaying)currentState).getCurrentDirection();
	}

	@Override
	public float getBatteryLevel() {
		// Return value of batteryLevel instance variable
		return batteryLevel[0];
	}

	@Override
	public void setBatteryLevel(float level) {
		// Set element of batteryLevel instance variable to value of level parameter
		batteryLevel[0] = level;
	}

	@Override
	public float getEnergyForFullRotation() {
		// Return value of constant instance variable FULLROTATIONENERGYUSED
		return FULLROTATIONENERGYUSED;
	}

	@Override
	public float getEnergyForStepForward() {
		// Return value of constant instance variable STEPFORWARDENERGYUSED
		return STEPFORWARDENERGYUSED;
	}

	@Override
	public int getOdometerReading() {
		// Return value of odometer instance variable
		return ((StatePlaying)currentState).getOdomoterReading();
	}

	@Override
	public void resetOdometer() {
		// Set value of odometer instance variable to zero
		((StatePlaying)currentState).resetOdometer();
	}

	@Override
	public void rotate(Turn turn) {
		/*
		 * If the robot hasn't stopped (!hasStopped()):
		 * 
		 *		switch {turn}
		 * 			if current battery level < {energy required for rotation}:
		 * 				Stop the robot! 
		 * 				Break out of switch statement
		 * 			
		 * 			call StatePlaying.handleUserInput({x}, 0), changing parameter x 
		 * 			depending on value of turn parameter
		 * 			
		 * 			{battery level decreases by proportional amount of FULLROTATIONENERGYUSED}
		 */
		
		if (!hasStopped()) {
			switch (turn) {
			case LEFT:
				if (batteryLevel[0] < getEnergyForFullRotation() * 0.25) {
					hasStopped = true;
					break;
				}
				currentState.handleUserInput(UserInput.RIGHT, 0);
				System.out.println("rotated left");
				batteryLevel[0] -= FULLROTATIONENERGYUSED * 0.25;
				break;
			case RIGHT:
				if (batteryLevel[0] < getEnergyForFullRotation() * 0.25) {
					hasStopped = true;
					break;
				}
				currentState.handleUserInput(UserInput.LEFT, 0);
				System.out.println("rotated right");
				batteryLevel[0] -= FULLROTATIONENERGYUSED * 0.25;
				break;
			case AROUND:
				if (batteryLevel[0] < getEnergyForFullRotation() * 0.5) {
					hasStopped = true;
					break;
				}
				currentState.handleUserInput(UserInput.LEFT, 0);
				currentState.handleUserInput(UserInput.LEFT, 0);
				System.out.println("rotated around");
				batteryLevel[0] -= FULLROTATIONENERGYUSED * 0.5;
				break;
			}
		}
	}

	@Override
	public void move(int distance) {
		/*
		 * Do the following a number of times equal to distance parameter:
		 * 
		 * 		If the robot hasn't stopped:
		 * 
		 * 			If the way is clear (StatePlaying.wayIsClear() ) AND
		 * 			the battery level >= energy required for moving 1 unit:
		 * 
		 * 				call StatePlaying.handleUserInput(UP, 0)
		 * 
		 * 				increase odometer by one (recording movement)
		 * 				
		 * 				battery level decreases by energy required for moving 1 unit
		 * 
		 * 			Else:
		 * 
		 * 				The robot has stopped! (update instance variable)
		 * 
		 */
		for (int i = 0; i < distance; i++) {
			if (!hasStopped()) {
				if (((StatePlaying) currentState).wayIsClear(1)
						&& getBatteryLevel() >= STEPFORWARDENERGYUSED) {
					currentState.handleUserInput(UserInput.UP, 0);
					System.out.println("moved forward 1 unit. Battery level: " + getBatteryLevel());
					batteryLevel[0] -= STEPFORWARDENERGYUSED;
				} else {
					hasStopped = true;
				}
			}
		}
	}

	@Override
	public void jump() {
		/*
		 * If the robot hasn't stopped:
		 * 
		 * 		If the battery level >= energy required for moving 1 unit AND 
		 * 		we are jumping to a cell inside the maze (Mazecontainer.isValidPosition() ):
		 * 
		 * 			call StatePlaying.handleUserInput(JUMP, 0)
		 * 
		 * 			increase odometer by one (recording movement)
		 * 			
		 * 			battery level decreases by energy required for moving 1 unit
		 *
		 * 		Else:
		 * 
		 * 			The robot has stopped! (update instance variable)
		 */
		
		if (!hasStopped()) {
			CardinalDirection cDirection = ((StatePlaying)currentState).getCurrentDirection();
			int[] arr = cDirection.getDxDyDirection();
			int[] cur = getCurrentPosition();
			cur[0] += arr[0];
			cur[1] += arr[1];
			if (getBatteryLevel() >= STEPFORWARDENERGYUSED && GeneratingActivity.maze.isValidPosition(cur[0],cur[1])) {
				currentState.handleUserInput(UserInput.JUMP, 0);
				batteryLevel[0] -= STEPFORWARDENERGYUSED;
			} else {
				hasStopped = true;
			}
		}

	}

	@Override
	public boolean isAtExit() {
		// Return result of checking if MazeContainer.getDistanceToExit() of the current position equals 1
		int[] cur = getCurrentPosition();
		return GeneratingActivity.maze.getDistanceToExit(cur[0], cur[1]) == 1;
	}

	@Override
	public boolean isInsideRoom() {
		// Return MazeContainer.isInRoom() for current position
		int[] cur = getCurrentPosition();
		return GeneratingActivity.maze.isInRoom(cur[0], cur[1]);
	}

	@Override
	public boolean hasStopped() {
		// Return value of boolean instance variable hasStopped
		return hasStopped;
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		/* If the robot doesn't have a sensor in the given direction:
		 * 		Throw Unsupported OperationException
		 * 
		 * Try: 
		 * 		Return distanceSensor.distanceToObstacle()
		 * Catch PowerFailure:
		 * 		the robot has stopped!
		 * 		throw UnsupportedOperationException 
		 * Catch IllegalArgumentException:
		 * 		Log to user that arguments were invalid
		 * 		throw UnsupportedOperationException 
		 */
		DistanceSensor sensor = null;
		switch (direction) {
		case LEFT:
			if (leftSensor == null) {
				throw new UnsupportedOperationException("Robot doesn't have a left sensor!");
			}
			sensor = leftSensor;
			break;
		case FORWARD:
			if (forwardSensor == null) {
				throw new UnsupportedOperationException("Robot doesn't have a forward sensor!");
			}
			sensor = forwardSensor;
			break;
		}
		
		try {
			return sensor.distanceToObstacle(getCurrentPosition(), getCurrentDirection(), batteryLevel);
		} catch (IllegalArgumentException e1) {
			throw new UnsupportedOperationException("Arguments were invalid for distance to Obstacle. Either a parameter was null, or currentPosition was out of range");
		} catch (Exception e2) {
			hasStopped = true;
			throw new UnsupportedOperationException("Not enough power left to sense.");
		}
	}

	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException  {
		/* If the robot doesn't have a sensor in the given direction:
		 * 		Throw Unsupported OperationException
		 * 
		 * Try: 
		 * 		Return whether distanceSensor.distanceToObstacle() equals Integer.MaxValue
		 * Catch PowerFailure:
		 * 		the robot has stopped!
		 * 		throw UnsupportedOperationException 
		 * Catch IllegalArgumentException:
		 * 		Log to user that arguments were invalid
		 * 		throw UnsupportedOperationException 
		 */
		return distanceToObstacle(direction) == Integer.MAX_VALUE;
	}

	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}
