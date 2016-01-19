package br.com.guigasgame.gameobject.hero.input;

import javafx.scene.input.MouseButton;

import org.jsfml.window.Joystick;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.JoystickEvent;
import org.jsfml.window.event.Event.Type;
import org.jsfml.window.event.JoystickButtonEvent;

import br.com.guigasgame.gameobject.hero.input.GameHeroInput.HeroKey;

public class InputController {
	// https://github.com/SFML/SFML/wiki/Tutorial:-Manage-dynamic-key-binding
	// private Boolean status;
	// private Boolean prevStatus;
	private final HeroKey heroKey;
	private InputHeroListener inputListener;

	private InputType inputType;
	private Event.Type pressedEventType;
	private Event.Type releasedEventType;

	private Keyboard.Key keyCode;
	private MouseButton mouseButton;
	private Joystick joystick;

	private InputController(InputHeroListener keyListener, HeroKey heroKey, InputType inputType, Type pressedEventType,
			Type releasedEventType, Key keyCode, MouseButton mouseButton, Joystick joystick) {
		super();
		this.inputListener = keyListener;
		this.heroKey = heroKey;
		this.inputType = inputType;
		this.pressedEventType = pressedEventType;
		this.releasedEventType = releasedEventType;
		this.keyCode = keyCode;
		this.mouseButton = mouseButton;
		this.joystick = joystick;
	}

	public void updateStatus(Event event) {
		// prevStatus = status;
		handleEvent(event);

		// if (status != prevStatus)
		// reportToListener();
	}

	private void handleMouseEvent(Event event) {
		if (mouseButton.equals(event.asMouseButtonEvent().button)) {
			reportToListener(event.type);
		}
	}

	private void handleKeyboardEvent(Event event) {
		if (keyCode == event.asKeyEvent().key)
		{
			reportToListener(event.type);
		}
	}

	private void handleJoystickEvent(Event event) {
		if (joystick.equals(event.asJoystickButtonEvent().type))
		{
			reportToListener(event.type);
		}
	}

	private void handleEvent(Event event) {
		if (pressedEventType == event.type || releasedEventType == event.type) {
			switch (inputType) {
			case MouseInput:
				handleMouseEvent(event);
				break;
			case KeyboardInput:
				handleKeyboardEvent(event);
				break;
			case JoystickInput:
				handleJoystickEvent(event);
				break;
			default:
			}

		}
	}

	private void reportToListener(Event.Type eventType) {
		if (inputListener != null) {
			if (pressedEventType == eventType)
				inputListener.inputPressed(heroKey);
			if (releasedEventType == eventType)
				inputListener.inputReleased(heroKey);
		}

		// if (status && !prevStatus)
		// {
		// keyListener.inputPressed(heroKey);
		// }
		// else if (!status && prevStatus)
		// {
		// keyListener.inputReleased(heroKey);
		// }
	}

	public HeroKey getHeroKey() {
		return heroKey;
	}
	
	public void setInputHeroListener(InputHeroListener listener) {
		inputListener = listener;
		
	}	

	public static InputController createKeyboardEvent(Key key, InputHeroListener listener, HeroKey heroKey) {
		InputType inputType = InputType.KeyboardInput;
		Event.Type pressedEventType = Type.KEY_PRESSED;
		Event.Type releasedEventType = Type.KEY_RELEASED;

		Keyboard.Key keyCode = key;
		return new InputController(listener, heroKey, inputType, pressedEventType, releasedEventType, keyCode, null, null);
	}
	public static InputController createMouseEvent(MouseButton button, InputHeroListener listener, HeroKey heroKey) {
		InputType inputType = InputType.MouseInput;
		Event.Type pressedEventType = Type.MOUSE_BUTTON_PRESSED;
		Event.Type releasedEventType = Type.MOUSE_BUTTON_RELEASED;
		MouseButton mouseButton = button;
		return new InputController(listener, heroKey, inputType, pressedEventType, releasedEventType, null, mouseButton, null);
	}
	public static InputController createJoystickEvent(Joystick joystickCode, InputHeroListener listener, HeroKey heroKey) {
		InputType inputType = InputType.JoystickInput;
		Event.Type pressedEventType = Type.JOYSTICK_BUTTON_PRESSED;
		Event.Type releasedEventType = Type.JOYSTICK_BUTTON_RELEASED;
		Joystick joystick = joystickCode;
		return new InputController(listener, heroKey, inputType, pressedEventType, releasedEventType, null, null, joystick);
	}

}
