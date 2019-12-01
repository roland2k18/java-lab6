package solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import battleship.BattleShip;

/**
 * Starting code for Comp10152 - Lab#6 
 * @author mark.yendt
 * 
 * @author Md Forhad Chowdhury, 000773030
 * 
 */
public class COMP10152_Lab6
{
   static final int NUMBEROFGAMES = 10000;
   public static void startingSolution()
  {
    int totalShots = 0;
    System.out.println(BattleShip.version());
    for (int game = 0; game < NUMBEROFGAMES; game++) {

      BattleShip battleShip = new BattleShip();
      BattleBot sampleBot = new BattleBot(battleShip);
      System.out.println("Game Size:"+battleShip.BOARDSIZE);
      
      System.out.println("Ships:"+Arrays.toString(battleShip.shipSizes()));
      
      // Call SampleBot Fire randomly - You need to make this better!
      while (!battleShip.allSunk()) {
        sampleBot.fireShot();
      }
      int gameShots = battleShip.totalShotsTaken();
      totalShots += gameShots;
    }
    System.out.printf("SampleBot - The Average # of Shots required in %d games to sink all Ships = %.2f\n", NUMBEROFGAMES, (double)totalShots / NUMBEROFGAMES);
    
  }
  public static void main(String[] args)
  {
    startingSolution();
  }
}
