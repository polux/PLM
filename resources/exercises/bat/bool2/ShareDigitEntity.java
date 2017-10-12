/* automatically converted from the Nick Parlante's excellent exercising site http://javabat.com/ */

package bat.bool2;
import plm.core.model.lesson.ExerciseTemplated;
import plm.core.model.lesson.Lesson;
import plm.universe.bat.BatTest;
import plm.universe.bat.BatWorld;

public class ShareDigitEntity extends BatEntity {
	public void run(BatTest t) {
		t.setResult( shareDigit((Integer)t.getParameter(0), (Integer)t.getParameter(1)) ); 
	}

	/* BEGIN TEMPLATE */
	boolean shareDigit(int a, int b) {
		/* BEGIN SOLUTION */
		return (a/10 == b/10 || a/10 == b%10 || a%10 == b/10 || a%10 == b%10);
		/* END SOLUTION */
	}
	/* END TEMPLATE */
}
