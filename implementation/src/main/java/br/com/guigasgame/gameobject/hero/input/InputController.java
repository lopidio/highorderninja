package br.com.guigasgame.gameobject.hero.input;

import org.jsfml.window.Joystick;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;

import br.com.guigasgame.gameobject.hero.input.GameHeroInput.HeroInputKey;

public class InputController {
	// https://github.com/SFML/SFML/wiki/Tutorial:-Manage-dynamic-key-binding
	private final InputHeroListener inputListener;
	private final HeroInputKey heroInputKey;

	private final InputType inputType;

	private final Keyboard.Key keyCode;
	private final Mouse.Button mouseButton;
	private final int joystickButtonCode;
	private final int joystickId;

	private boolean state;
	private boolean prevState;

	private InputController(InputHeroListener keyListener, HeroInputKey heroKey, InputType inputType, Key keyCode,
			Mouse.Button mouseButton, int joystickButtonCode, int joystickId) {
		super();
		this.inputListener = keyListener;
		this.heroInputKey = heroKey;
		this.inputType = inputType;
		this.keyCode = keyCode;
		this.mouseButton = mouseButton;
		this.joystickButtonCode = joystickButtonCode;
		this.joystickId = joystickId;
		state = false;
		prevState = false;
	}

	public void handleEvent() {
		prevState = state;

		switch (inputType) {
		case MouseInput:
			state = (Mouse.isButtonPressed(mouseButton));
			break;
		case KeyboardInput:
			state = (Keyboard.isKeyPressed(keyCode));
			break;
		case JoystickInput:
			state = (Joystick.isButtonPressed(joystickId, joystickButtonCode));
			break;
		default:
		}

		reportToListener();
	}

	private void reportToListener() {
		if (inputListener != null) {
			if (state) {
				inputListener.isPressed(heroInputKey);
				if (!prevState)
					inputListener.inputPressed(heroInputKey);
			} else // if (!state)
			{
				inputListener.isReleased(heroInputKey);
				if (prevState)
					inputListener.inputReleased(heroInputKey);
			}
		}
	}

	public HeroInputKey getHeroKey() {
		return heroInputKey;
	}

	public static InputController createKeyboardEvent(Key key, InputHeroListener listener, HeroInputKey heroKey) {
		InputType inputType = InputType.KeyboardInput;
		// Event.Type pressedEventType = Type.KEY_PRESSED;
		// Event.Type releasedEventType = Type.KEY_RELEASED;

		return new InputController(listener, heroKey, inputType, key, null, -1, -1);
	}

	public static InputController createMouseClickEvent(Mouse.Button button, InputHeroListener listener,
			HeroInputKey heroKey) {
		InputType inputType = InputType.MouseInput;
		// Event.Type pressedEventType = Type.MOUSE_BUTTON_PRESSED;
		// Event.Type releasedEventType = Type.MOUSE_BUTTON_RELEASED;
		return new InputController(listener, heroKey, inputType, null, button, -1, -1);
	}

	public static InputController createJoystickButtonEvent(int joystickButtonCode, int joystickId,
			InputHeroListener listener, HeroInputKey heroKey) {
		InputType inputType = InputType.JoystickInput;
		// Event.Type pressedEventType = Type.JOYSTICK_BUTTON_PRESSED;
		// Event.Type releasedEventType = Type.JOYSTICK_BUTTON_RELEASED;
		return new InputController(listener, heroKey, inputType, null, null, joystickButtonCode, joystickId);
	}

}
