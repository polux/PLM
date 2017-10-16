package sort.basic.cocktail;


import plm.core.model.lesson.ExerciseTemplated;

import plm.core.utils.FileUtils;
import plm.universe.sort.SortingWorld;

public class AlgCocktailSort2 extends ExerciseTemplated {
	
	public AlgCocktailSort2(FileUtils fileUtils) {
		super("AlgCocktailSort2", "AlgCocktailSort2");
		
		SortingWorld[] myWorlds = new SortingWorld[2];
		myWorlds[0] = new SortingWorld(fileUtils, "Functional test",10);
		myWorlds[1] = new SortingWorld(fileUtils, "Performance test (150 elms)",150);
		
		setup(myWorlds);
	}
}
