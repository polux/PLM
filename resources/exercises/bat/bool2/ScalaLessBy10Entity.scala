/* automatically converted from the Nick Parlante's excellent exercising site http://javabat.com/ */

package bat.bool2;
import plm.core.model.lesson.ExerciseTemplated;
import plm.core.model.lesson.Lesson;
import plm.universe.bat.BatTest;
import plm.universe.bat.BatWorld;

class ScalaLessBy10Entity extends BatEntity {
	/* BEGIN TEMPLATE */
def lessBy10(a:Int, b:Int, c:Int):Boolean =
		/* BEGIN SOLUTION */
	((a - b) >= 10) || ((b - a) >= 10) || ((b - c) >= 10) || ((c - b) >= 10) || ((a - c) >= 10) || ((c - a) >= 10)
		/* END SOLUTION */
	/* END TEMPLATE */

	override def run(t: BatTest) {
		t.setResult( lessBy10((Integer)t.getParameter(0), (Integer)t.getParameter(1), (Integer)t.getParameter(2)) );
	}
}
