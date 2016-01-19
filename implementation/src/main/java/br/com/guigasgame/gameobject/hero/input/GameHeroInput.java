package br.com.guigasgame.gameobject.hero.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsfml.window.Keyboard.Key;

public class GameHeroInput {
	public enum HeroKey {
		LEFT, RIGHT, UP, DOWN, JUMP, ROPE, SHOOT, ACTION,
	}

	InputHeroListener inputHeroListener;

	private Map<HeroKey, InputController> keyMap;

	public GameHeroInput() {
		keyMap = new HashMap<GameHeroInput.HeroKey, InputController>();
		InputController jump = InputController.createKeyboardEvent(Key.SPACE, inputHeroListener, HeroKey.JUMP);
		keyMap.put(jump.getHeroKey(), jump);
	}

	public void setInputListener(InputHeroListener listener) {
		for (Entry<HeroKey, InputController> key : keyMap.entrySet()) {
			key.getValue().setInputHeroListener(listener);
		}

	}
}
