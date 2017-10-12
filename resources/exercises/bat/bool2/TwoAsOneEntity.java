/* automatically converted from the Nick Parlante's excellent exercising site http://javabat.com/ */

package bat.bool2;
import plm.core.model.lesson.ExerciseTemplated;
import plm.core.model.lesson.Lesson;
import plm.universe.bat.BatTest;
import plm.universe.bat.BatWorld;

public class TwoAsOneEntity extends BatEntity {
	public void run(BatTest t) {
		t.setResult( twoAsOne((Integer)t.getParameter(0), (Integer)t.getParameter(1), (Integer)t.getParameter(2)) );
	}

	/* BEGIN TEMPLATE */
	boolean twoAsOne(int a, int b, int c) {
		/* BEGIN SOLUTION */
		return (a + b == c) || (a + c == b) || (b + c == a);
		/* END SOLUTION */
	}
	/* END TEMPLATE */
}
