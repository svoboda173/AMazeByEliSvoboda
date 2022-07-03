package edu.wm.cs.cs301.EliSvoboda.generation;

import java.util.Random;

public class MazeBuilderKruskal extends MazeBuilder implements Runnable {

	public MazeBuilderKruskal () {
	}
	
	/**
	 * Create maze with internal walls in every valid location a wall can
	 * be (between every cell pair)
	 * 
	 * Make a list of internal walls
	 * 
	 * Assign all cells to nodes of a tree using DefaultMutableTreeNode;
	 * 
	 * While the list contains walls:
	 * 		Pick an element of the list at random
	 * 		If the two cells don't have the same root: 
	 * 			Combine them into a tree together using insert() 
	 * 			Remove the corresponding wall from the maze
	 * 		Remove the element from the list
	 * 
	 */
	@Override
	protected void generatePathways() {
		int wallInd = 0;
		int randInt;
		TreeNode[][] cells = new TreeNode[width][height];
		Wallboard chosenWall;
		Wallboard[] wallList = new Wallboard [(height-1)*(width)+ (height)*(width-1)];
		
		for (int x = 1; x < width; x++) {		//start x from one to avoid counting border walls
			for (int y = 0; y < height; y++) {
				if (!floorplan.isInRoom(x, y)) {
					Wallboard newWall = new Wallboard(x, y, CardinalDirection.West);
					floorplan.addWallboard(newWall, true);
					wallList[wallInd] = newWall;
					wallInd++;
				}
			}
		}
		for (int x = 0; x < width; x++) {		//start y from one to avoid counting border walls
			for (int y = 1; y < height; y++) {
				if (!floorplan.isInRoom(x, y)) {
					Wallboard newWall = new Wallboard(x, y, CardinalDirection.North);
					floorplan.addWallboard(newWall, true);
					wallList[wallInd] = newWall;
					wallInd++;
				}
			}
		}
		for (int x = 0; x < width; x++) {		
			for (int y = 0; y < height; y++) {
				cells[x][y] = new TreeNode();
			}
		}
		
		while (wallInd > 0) {
			randInt = random.nextIntWithinInterval(0, wallInd-1);
			chosenWall = wallList[randInt];
			if (!(cells[chosenWall.getX()][chosenWall.getY()].getRoot()).equals((cells[chosenWall.getNeighborX()][chosenWall.getNeighborY()].getRoot()))) {
				cells[chosenWall.getX()][chosenWall.getY()].insert((TreeNode)(cells[chosenWall.getNeighborX()][chosenWall.getNeighborY()].getRoot()));
				floorplan.deleteWallboard(chosenWall);
			}
			wallList[randInt] = wallList[wallInd-1];
			wallInd --;
		}
	}
}
