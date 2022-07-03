package edu.wm.cs.cs301.EliSvoboda.gui;

import edu.wm.cs.cs301.EliSvoboda.generation.CardinalDirection;
import edu.wm.cs.cs301.EliSvoboda.generation.Maze;
import edu.wm.cs.cs301.EliSvoboda.gui.Robot.Direction;

/**
 * 
 * Responsibilities:
 * Determines distance to wall in some direction
 * 
 * Collaborators:
 * BasicRobot (used by)
 * MazeContainer(for wall checking)
 * StatePlaying (for position + MazeContainer)
 * Control, which feeds it StatePlaying and MazeContainer
 * 
 * @author Eli Svoboda
 *
 */

public class BasicSensor implements DistanceSensor {	
	
	private final float SENSINGENERGYREQUIRED = 1;
	
	Maze maze;
	Direction sensorDirection;
	
	 /*Index Explanation:
		 * 
		 * directionIndex serves as an index indicating the CardinalDirection in which we are sensing 
		 * walls when we call distanceToObstacle(). Rows indicate the sensorDirection; columns indicate 
		 * the currentDirection which the robot is facing. The intersection of the corresponding row
		 * and column for a given state indicates the CardinalDirection in which we will sense the
		 * distance to the obstacle. 
		 * 
		 * Mapping of indices for Directions and CardinalDirections are as shown below:
		 * 
		 * 0 = FORWARD
		 * 1 = LEFT
		 * 2 = BACKWARD
		 * 3 = RIGHT
		 * 
		 * 0 = North
		 * 1 = West
		 * 2 = South
		 * 3 = East
		 */
	
	protected CardinalDirection[][] directionIndex = {
			{CardinalDirection.North, CardinalDirection.West, CardinalDirection.South, CardinalDirection.East},
			{CardinalDirection.West, CardinalDirection.South, CardinalDirection.East, CardinalDirection.North},
			{CardinalDirection.South, CardinalDirection.East, CardinalDirection.North, CardinalDirection.West},
			{CardinalDirection.East, CardinalDirection.North, CardinalDirection.West, CardinalDirection.South},
	};	

	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		/* 
		 * If currentBattery < energy required for sensing:
		 * 		throw PowerFailure Exception
		 * 
		 * If any of currentPosition, currentDirection, or powersupply parameters are NULL, OR
		 * if currentPosition is not within the maze (MazeContainer.isValidPosition() ):
		 * 		throw IllegalArgumentException		 
		 * 
		 * Determine direction using directionIndex  
		 * Set position modifier array to CardinalDirection.getDxDyDirection()
		 * Initialize a counter to 0
		 * set x to current x position of robot
		 * set y to current y position of robot
		 * Repeat the following infinitely: (until break statement is executed)
		 * 		If position x, y is outside the maze:
		 * 			set counter to Integer.MaxValue
		 * 			Break out of while loop
		 * 		If there's an adjacent wall in the chosen CardinalDirection (MazeContainer.hasWall() ):
		 *			Break out of while loop
		 *		Else:
		 *			Increment counter, keeping track of number of movements
		 *			x increases by value of first element in position modifier array
		 *			y increases by value of second element in position modifier array
		 *			
		 * Return value of counter 
		 * 
		 */		
		if (currentPosition == null || currentDirection == null || powersupply == null 
				|| !maze.isValidPosition(currentPosition[0], currentPosition[1])
				) {
			throw new IllegalArgumentException("oopsies");
		} if (powersupply[0] < SENSINGENERGYREQUIRED) {
			throw new Exception("PowerFailure!");
		} 
		int xInd = -1;
		int yInd = -1;
		switch (sensorDirection) {
		case FORWARD:
			xInd = 0;
			break;
		case LEFT:
			xInd = 1;
			break;
		}
		switch (currentDirection) {
		case North:
			yInd = 0;
			break;
		case West:
			yInd = 1;
			break;
		case South:
			yInd = 2;
			break;
		case East:
			yInd = 3;
			break;
		}
		CardinalDirection targetDirection = directionIndex[xInd][yInd];
		int[] positionModifierArray = targetDirection.getDxDyDirection();
		int count = 0;
		int x = currentPosition[0];
		int y = currentPosition[1];
		while(true) {
			if(!maze.isValidPosition(x, y)) {
				count = Integer.MAX_VALUE;
				break;
			}
			if (maze.hasWall(x, y, targetDirection)) {
				break;
			} else {
				count++;
				x += positionModifierArray[0];
				y += positionModifierArray[1];
			}
		}
		return count;
	}

	@Override
	public void setMaze(Maze maze) {
		// Set instance variable maze to passed parameter maze
		this.maze = maze;
	}

	@Override
	public void setSensorDirection(Direction mountedDirection) {
		// Set instance variable sensorDirection to indicated direction
		sensorDirection = mountedDirection;
	}

	@Override
	public float getEnergyConsumptionForSensing() {
		// Return value of constant SENSINGENERGYREQUIRED
		return SENSINGENERGYREQUIRED;
	}

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();

	}

}
