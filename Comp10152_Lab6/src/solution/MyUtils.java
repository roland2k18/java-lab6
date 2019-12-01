package solution;

import java.awt.Point;

/**
 * 
 * @author mark.yendt
 * 
 * @author Md Forhad Chowdhury, 000773030
 * 
 */
public class MyUtils {
	public static  Point[] firstHitPoints = { new Point(5, 5), new Point(5, 3), new Point(6, 4), new Point(6, 6),
			new Point(4, 4), new Point(4, 6), new Point(3, 3), new Point(3, 7), new Point(7, 4), new Point(7, 6) }; // These are the center pieces which we should be hitting first. 

	public static Point[] alternatePoints = { new Point(4, 5), new Point(5, 4),

			new Point(5, 6), new Point(6, 5), new Point(6, 3), new Point(4, 3), new Point(3, 4), new Point(3, 6),

			new Point(3, 2), new Point(5, 2), new Point(7, 2), new Point(7, 4), new Point(7, 6), new Point(6, 7),
			new Point(4, 7), new Point(2, 7), new Point(2, 5), new Point(2, 3),

			new Point(2, 1), new Point(4, 1), new Point(6, 1), new Point(8, 1), new Point(8, 3), new Point(8, 5),
			new Point(8, 7), new Point(7, 8), new Point(5, 8), new Point(3, 8), new Point(1, 8), new Point(1, 6),
			new Point(1, 4), new Point(1, 2),

			new Point(1, 0), new Point(3, 0), new Point(5, 0), new Point(7, 0), new Point(9, 0), new Point(0, 9),
			new Point(2, 9), new Point(4, 9), new Point(6, 9), new Point(8, 9), new Point(0, 7), new Point(0, 5),
			new Point(0, 3), new Point(0, 1), new Point(9, 8), new Point(9, 6), new Point(9, 4), new Point(9, 2) };

}
