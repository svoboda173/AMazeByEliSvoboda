package edu.wm.cs.cs301.EliSvoboda.gui;

import edu.wm.cs.cs301.EliSvoboda.generation.Maze;
import edu.wm.cs.cs301.EliSvoboda.gui.Robot.Direction;
import edu.wm.cs.cs301.EliSvoboda.gui.Robot.Turn;

/**
 * 
 * Responsibilities:
 * Tells BasicRobot where to move, given simple wall-following algorithm
 * 
 * Collaborators:
 * BasicRobot (WallFollower directs BasicRobot where to move)
 * Floorplan (for marking cells as visited)
 * MazeContainer (for maze info)
 * StatePlaying (for MazeContainer)
 * Control (for StatePlaying)
 * 
 * @author Eli Svoboda
 *
 */

public class WallFollower implements RobotDriver {
	
	Robot robot;
	
	Maze maze;
	
	@Override
	public void setRobot(Robot r) {
		// Set instance variable robot to r
		this.robot = r;
	}

	@Override
	public void setMaze(Maze maze) {
		// Set instance variable maze to parameter maze
		this.maze = maze;
	}

	@Override
	public boolean drive2Exit() throws Exception {
		/*
		 * Move toward the exit until we can't anymore (while drive1Step2Exit() ):
		 * 
		 * We've reached the exit! return true
		 * 
		 */
		while (drive1Step2Exit()) {
		}
		return true;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		/*
		 * if distance to exit > 0: (!robot.isAtExit)
		 * 
		 * 		If the distance to the wall to the left isn't zero:
		 * 			rotate the robot left.
		 * 		
		 * 		Else:
		 * 			if the distance to the wall in front is zero:
		 * 				rotate the robot right.
		 * 
		 * 		Move robot forward one step (robot.move() )
		 * 
		 * 
		 * 		return true
		 * 
		 * else:
		 * 		Rotate the robot until it's looking toward the exit (robot.can
		 * 		seeThroughtheExitintoEternity(currentDirection) == true)
		 * 
		 * 		return false
		 *  
		 */
		if (!robot.isAtExit()) {
			if (robot.distanceToObstacle(Direction.LEFT) != 0) {
				robot.rotate(Turn.LEFT);
			} else {
				while (robot.distanceToObstacle(Direction.FORWARD) == 0) {
					robot.rotate(Turn.RIGHT);
				}
			}
			robot.move(1);
			return true;
		} else {
			while (!robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) {
				robot.rotate(Turn.LEFT);
			}
			return false;
		}
	}

	@Override
	public float getEnergyConsumption() {
		// Return robot.INITIALBATTERYLEVEL - robot.getBatteryLevel()
		return ((BasicRobot)robot).INITIALBATTERYLEVEL - robot.getBatteryLevel();
	}

	@Override
	public int getPathLength() {
		// Return robot.getOdometerReading()
		return robot.getOdometerReading();
	}

}
