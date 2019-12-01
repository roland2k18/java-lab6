
package solution;

import battleship.BattleShip;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import battleship.CellState;

/**
 * 
 * @author mark.yendt
 * 
 * @author Md Forhad Chowdhury, 000773030
 * 
 */
public class BattleBot {
	private int gameSize;
	private BattleShip battleShip;
	private Random random;
	private CellState[][] map;
	private Point lastHit;
	private Point initialHit;
	private Point[] alternatePoints;
	private Point[] importantPoints;
	private ShotDirection shotDirection;
	private boolean allDirectionCompleted;
	private int pointIdx=0;
	private Point lastShot;
	private HashMap<ShotDirection, Boolean> completedDirection;

	/*
	 * The constructor which has the battleship
	 * gameSize,
	 * a random generator,
	 * map of the board,
	 * default shot Direction,
	 * is all Direction Completed,
	 * alternatePoints=>best points where the probabiliy of finding is more
	 * important points => the important points where we need to hit first
	 */
	public BattleBot(BattleShip b) {
		battleShip = b;
		gameSize = b.BOARDSIZE;
		random = new Random(); // Needed for random shooter - not required for more systematic approaches
		map = new CellState[b.BOARDSIZE][b.BOARDSIZE];
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {
				map[i][j] = CellState.Empty;
			}
		}
		
		shotDirection = ShotDirection.Left;
		completedDirection = new HashMap<ShotDirection, Boolean>();
		allDirectionCompleted=false;
		for (ShotDirection e : ShotDirection.values()) {
			completedDirection.put(e, false);
		}
		completedDirection.put(shotDirection, true);
		alternatePoints = MyUtils.alternatePoints;
		importantPoints = MyUtils.firstHitPoints;
	}

	/*
	 * Is the point valid i.e. it is within the boundary of the board
	 */
	private boolean isValidPoint(Point p) {
		if (p!=null && p.getX() >= 0 && p.getX() < gameSize && p.getY() >= 0 && p.getY() < gameSize) {
			return true;
		}
		return false;
	}
        
	/*
	 * is the point valid and Empty also
	 */
	private boolean isValidEmpty(Point p) {
		if (p!=null && p.getX() >= 0 && p.getX() < gameSize && p.getY() >= 0 && p.getY() < gameSize) {
			if (map[p.x][p.y] == CellState.Empty) {
				return true;
			}
			return false;
		}
		return false;
	}
        
	/*
	 * Is the point Empty
	 */
	private boolean isEmpty(Point p) {
		if (map[p.x][p.y] == CellState.Empty) {
			return true;
		}
		return false;
	}
        
	/*
	 * find the best point from list or random
	 */
	private Point findBestPoint() {

		Point shot=alternatePoints[0];
		while (isValidEmpty(shot) == false && pointIdx < alternatePoints.length) {
			shot = alternatePoints[pointIdx];
			pointIdx+=1;
		}
		
		if (isValidEmpty(shot)) {
			
			return shot;
		} else {
			int count = 0;
			while (isValidEmpty(shot) == false && count < importantPoints.length) {
				shot = importantPoints[count];
				count++;
			}
			if (isValidEmpty(shot)) {
				return shot;
			} else {

				int x = random.nextInt(gameSize);
				int y = random.nextInt(gameSize);
				shot = new Point(x, y);
				while (isValidEmpty(shot) == false) {

					x = random.nextInt(gameSize);
					y = random.nextInt(gameSize);
					shot = new Point(x, y);	
				}
				return shot;
			}
		}
	}
        
	/*
	 * find the point in left direction
	 */
	private Point findLeft(Point pt) {
		if(pt.x>=0 && pt.x<gameSize && pt.y-1>=0 && pt.y-1<gameSize) {
			return new Point(pt.x,pt.y-1);
		}
		return null;
	}

	/*
	 * find the point in right direction
	 */
	private Point findRight(Point pt) {

		if(pt.x>=0 && pt.x<gameSize && pt.y+1>=0 && pt.y+1<gameSize) {
			return new Point(pt.x,pt.y+1);
		}
		return null;
	}

	/*
	 * find the point in top direction
	 */
	private Point findTop(Point pt) {

		if(pt.x-1>=0 && pt.x-1<gameSize && pt.y>=0 && pt.y<gameSize) {
			return new Point(pt.x-1,pt.y);
		}
		return null;
	}

	/*
	 * find the point in bottom direction
	 */
	private Point findBottom(Point pt) {
		if(pt.x+1>=0 && pt.x+1<gameSize && pt.y>=0 && pt.y<gameSize) {
			return new Point(pt.x+1,pt.y);
		}
		return null;
	}

	/*
	 * is the point has already a hit
	 */
	public boolean isHit(Point pt) {

		if(isValidPoint(pt) && map[pt.x][pt.y]==CellState.Hit) {
			return true;
		}
		return false;
	}
        
	/*
	 * find the next point to shot
	 */
	private Point findNextPoint(Boolean hit) {

		 Point nextPt;
		if (hit) {
			if (shotDirection == ShotDirection.Left) {
				Point p = findLeft(lastHit);
				while(isHit(p)){
					p = findLeft(p);		
				}
				nextPt = p;
			}else if (shotDirection == ShotDirection.Right) {
				Point p = findRight(lastHit);
				while(isHit(p)){
					p = findRight(p);		
				}
				nextPt = p;
			}else if (shotDirection == ShotDirection.Top) {
				Point p = findTop(lastHit);
				while(isHit(p)){
					p = findTop(p);		
				}
				nextPt = p;
			}else if (shotDirection == ShotDirection.Bottom) {
				Point p = findBottom(lastHit);
				while(isHit(p)){
					p = findBottom(p);	
				}
				nextPt = p;
			}else {

				return findBestPoint();
			}
			if(nextPt!=null) {
				return nextPt;
			}else {
				return findBestPoint();
			}
			
		} else {

			return findBestPoint();
		}
	}
        
	/*
	 * print utility for the battlefield
	 */
	public void printMap() {
		System.out.println("------------------------------");
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {
				if(i==lastShot.x && j==lastShot.y) {
					System.out.print(" # ");
				}else {
					System.out.print(" " + map[i][j] + " ");					
				}
			}
			System.out.println();
		}
		System.out.println("------------------------------");

	}
        
	/*
	 * Change the shoting direction
	 */
	private void changeShotDirection() {
		if (completedDirection.get(ShotDirection.Left) == false) {
			shotDirection = ShotDirection.Left;
			completedDirection.put(shotDirection, true);
		} else if (completedDirection.get(ShotDirection.Right) == false) {
			shotDirection = ShotDirection.Right;
			completedDirection.put(shotDirection, true);
		} else if (completedDirection.get(ShotDirection.Top) == false) {
			shotDirection = ShotDirection.Top;
			completedDirection.put(shotDirection, true);
		} else if (completedDirection.get(ShotDirection.Bottom) == false) {
			shotDirection = ShotDirection.Bottom;
			completedDirection.put(shotDirection, true);
		} else {
			allDirectionCompleted= true;
			shotDirection = ShotDirection.Left;
		}
	}
        
	/*
	 * we found a hit and we need to destroy the whole ship
	 */
	private boolean distoryTheShip() {
		int numberOfShips = battleShip.numberOfShipsSunk();
		while(numberOfShips==battleShip.numberOfShipsSunk() && numberOfShips!=battleShip.shipSizes().length) {
			Point shot = findNextPoint(true);
			lastShot=shot;
			boolean hit = battleShip.shoot(shot);
			map[shot.x][shot.y] = CellState.Miss;
			if(hit) {
				lastHit=shot;
				map[shot.x][shot.y] = CellState.Hit;
				if(numberOfShips!=battleShip.numberOfShipsSunk()) {
						shotDirection = ShotDirection.Left;
						allDirectionCompleted=false;
						for (ShotDirection e : ShotDirection.values()) {
							completedDirection.put(e, false);
						}
						completedDirection.put(shotDirection, true);
						return true;
				}
			}else {
				changeShotDirection();
				lastHit=initialHit;
				if(allDirectionCompleted) {
					break;
				}
			}
		}
		return false;
	}
        
	/**
	 * Create a random shot and calls the battleship shoot method
	 * 
	 * @return true if a Ship is hit, false otherwise
	 */
	public boolean fireShot() {
		shotDirection = ShotDirection.Left;
		allDirectionCompleted=false;
		for (ShotDirection e : ShotDirection.values()) {
			completedDirection.put(e, false);
		}
		completedDirection.put(shotDirection, true);

		Point shot = findNextPoint(false);
		while (!isValidPoint(shot)) {
			shot = findNextPoint(false);
		}
		boolean hit = battleShip.shoot(shot);
		
		lastShot = shot;
		map[shot.x][shot.y] = CellState.Miss;
		if(hit) {
			lastHit=shot;
			initialHit=shot;
			map[shot.x][shot.y] = CellState.Hit;
			distoryTheShip();
		}
		return hit;
	}
}
