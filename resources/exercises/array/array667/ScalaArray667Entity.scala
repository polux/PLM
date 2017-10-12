package array.array667;
import plm.core.model.lesson.ExerciseTemplated;
import plm.core.model.lesson.Lesson;
import plm.universe.bat.BatTest;
import plm.universe.bat.BatWorld;

public class Array667Entity extends BatEntity {

	/* BEGIN TEMPLATE */
def array667(nums:Array[Int]): Int = {
		/* BEGIN SOLUTION */
  var count=0
  for (i <- 0 to nums.length-2)
    if ((nums(i) == 6) && (nums(i+1)==6 || nums(i+1)==7))
      count += 1
  return count
		/* END SOLUTION */
}
	/* END TEMPLATE */

	override def run(t: BatTest) {
		t.setResult( array667(t.getParameter(0).asInstanceOf[Array[Int]]) );
	}

}
