package br.com.guigasgame.gameobject.hero.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsfml.window.Joystick;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

public class GameHeroInput {
	public enum HeroKey
	{
		LEFT,
		RIGHT,
		UP,
		DOWN,
		JUMP,
		ROPE,
		SHOOT,
		ACTION,
	}

	private Map<HeroKey, InputController> keyMap;
	
	public GameHeroInput()
	{
		keyMap = new HashMap<GameHeroInput.HeroKey, InputController>();
		InputController jump = new InputController(null, HeroKey.JUMP);
		jump.setEvent(InputType.KeyboardInput, Keyboard.Key.SPACE, Event.Type.KEY_PRESSED);
		keyMap.put(HeroKey.JUMP, jump);
	}
	
	
	public void update()
	{
		for (Entry<HeroKey, InputController> key : keyMap.entrySet()) {
			key.getValue().updateStatus());
		}
	}
	public static void main(String[] args) {
	}
}
