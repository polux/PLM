package recursion.cons.length;

import plm.core.utils.FileUtils;
import plm.universe.bat.BatTest;
import plm.universe.bat.BatWorld;
import plm.universe.cons.ConsExercise;
import plm.universe.cons.ConsWorld;

public class Length extends ConsExercise {

	public Length(FileUtils fileUtils) {
		super("Length");
		
		BatWorld myWorld = new ConsWorld(fileUtils, "length");
		myWorld.addTest(BatTest.VISIBLE,   (new int[]{1, 2, 3}));
		myWorld.addTest(BatTest.VISIBLE,   (new int[]{1, 1, 1}));
		myWorld.addTest(BatTest.VISIBLE,   (new int[]{1, 2, 1, 3})) ;
		myWorld.addTest(BatTest.INVISIBLE, (new int[]{2, 4, 6, 8, 10})) ;
		myWorld.addTest(BatTest.INVISIBLE, (new int[]{})) ;

		setup(myWorld);
	}
}
