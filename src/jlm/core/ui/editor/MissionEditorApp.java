package jlm.core.ui.editor;

import jlm.core.model.Game;

public class MissionEditorApp {

	public static void main(String[] args) {
		MissionEditor editor = new MissionEditor();
		Game.getInstance().switchDebug(); // Forces the JlmHTMLEditorKit to also display all blocks marked with a class
		if (args.length>0)
			editor.loadMission(args[0]);
	}

}
