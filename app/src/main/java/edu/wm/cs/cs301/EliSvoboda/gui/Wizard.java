package edu.wm.cs.cs301.EliSvoboda.gui;

import edu.wm.cs.cs301.EliSvoboda.generation.Maze;

/**
 * 
 * Responsibilities:
 * Tells BasicRobot where to move, taking the path of max efficiency to exit
 * 
 * Collaborators:
 * BasicRobot (Wizard directs BasicRobot where to move)
 * MazeContainer (for maze info)
 * StatePlaying (for MazeContainer)
 * Control (for StatePlaying)
 * 
 * @author Eli Svoboda
 *
 */

public class Wizard extends WallFollower implements RobotDriver {

	/*@Override
	public boolean drive1Step2Exit() throws Exception {
		/*
		 * Prepare to choose a direction
		 * 
		 * if distance to exit > 0: 
		 * 
		 * 		For Each Direction of 4 (create array with Direction.values() ):
		 * 		
			 * 		if there is no wall in the given direction (!hasWall(x,y,robot.getCurrentDirection) AND
			 * 		the distance to exit from the cell directly in front is one less than the
			 * 		current distance to the exit: 
		 * 
		 * 		Move robot forward one step (robot.move() )
		 * 
		 * 		return true
		 * 
		 * else:
		 * 		Rotate the robot until it's looking toward the exit (robot.can
		 * 		seeThroughtheExitintoEternity(currentDirection) == true)
		 * 
		 * 		return false
		 *  
		 *
		return false;
	}
*/

}
