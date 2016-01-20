package br.com.guigasgame.gameobject.hero.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse.Button;

public class GameHeroInput implements InputHeroListener {
	public enum HeroInputKey {
		LEFT, RIGHT, UP, DOWN, JUMP, ROPE, SHOOT, ACTION,
	}

	InputHeroListener inputHeroListener;

	private Map<HeroInputKey, InputController> keyMap;

	public GameHeroInput() {
		keyMap = new HashMap<GameHeroInput.HeroInputKey, InputController>();
		InputController jump = InputController.createKeyboardEvent(Key.SPACE, this, HeroInputKey.JUMP);
		InputController left = InputController.createKeyboardEvent(Key.LEFT, this, HeroInputKey.LEFT);
		InputController right = InputController.createKeyboardEvent(Key.RIGHT, this, HeroInputKey.RIGHT);
		InputController action = InputController.createMouseClickEvent(Button.LEFT, this, HeroInputKey.ACTION);
		keyMap.put(jump.getHeroKey(), jump);
		keyMap.put(right.getHeroKey(), right);
		keyMap.put(left.getHeroKey(), left);
		keyMap.put(action.getHeroKey(), action);
	}

	public void update() {
		for (Entry<GameHeroInput.HeroInputKey, InputController> map : keyMap.entrySet()) {
			map.getValue().handleEvent();
		}
	}

	public void setInputListener(InputHeroListener listener) {
		this.inputHeroListener = listener;
	}

	@Override
	public void isPressed(HeroInputKey key) {
		if (null != inputHeroListener)
			inputHeroListener.isPressed(key);
	}
	
	@Override
	public void isReleased(HeroInputKey key) {
		if (null != inputHeroListener)
			inputHeroListener.isReleased(key);
	}
	
	@Override
	public void inputReleased(HeroInputKey key) {
		if (null != inputHeroListener)
			inputHeroListener.inputReleased(key);
	}

	@Override
	public void inputPressed(HeroInputKey key) {
		if (null != inputHeroListener)
			inputHeroListener.inputPressed(key);
	}
}
