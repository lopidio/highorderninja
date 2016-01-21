package br.com.guigasgame.gameobject.input;

import javax.xml.bind.annotation.XmlElement;

import org.jsfml.window.Joystick;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;

public class InputMapController<T> {
	// https://github.com/SFML/SFML/wiki/Tutorial:-Manage-dynamic-key-binding
	private InputListener<T> inputListener;
	
	public void setInputListener(InputListener<T> inputListener) {
		this.inputListener = inputListener;
	}

	@XmlElement
	private T inputValue;

	@javax.xml.bind.annotation.XmlElement
	private InputType inputType;

	@XmlElement
	private Keyboard.Key keyCode;
	@XmlElement
	private Mouse.Button mouseButton;
	@XmlElement
	private int joystickButtonCode;
	@XmlElement
	private int joystickId;

	private boolean state;
	private boolean prevState;
	
	public InputMapController()
	{
		
	}

	private InputMapController(InputListener<T> keyListener, T inputValue, InputType inputType, Key keyCode,
			Mouse.Button mouseButton, int joystickButtonCode, int joystickId) {
		super();
		this.inputListener = keyListener;
		this.inputValue = inputValue;
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
				inputListener.isPressed(inputValue);
				if (!prevState)
					inputListener.inputPressed(inputValue);
			} else // if (!state)
			{
				if (prevState)
					inputListener.inputReleased(inputValue);
			}
		}
	}

	public T getHeroKey() {
		return inputValue;
	}

	public static <T> InputMapController<T> createKeyboardEvent(Key key, InputListener<T> listener, T inputValue) {
		InputType inputType = InputType.KeyboardInput;
		// Event.Type pressedEventType = Type.KEY_PRESSED;
		// Event.Type releasedEventType = Type.KEY_RELEASED;

		return new InputMapController<T>(listener, inputValue, inputType, key, null, -1, -1);
	}

	public static <T> InputMapController<T> createMouseClickEvent(Mouse.Button button, InputListener<T> listener,
			T inputValue) {
		InputType inputType = InputType.MouseInput;
		// Event.Type pressedEventType = Type.MOUSE_BUTTON_PRESSED;
		// Event.Type releasedEventType = Type.MOUSE_BUTTON_RELEASED;
		return new InputMapController<T>(listener, inputValue, inputType, null, button, -1, -1);
	}

	public static <T> InputMapController<T> createJoystickButtonEvent(int joystickButtonCode, int joystickId,
			InputListener<T> listener, T inputValue) {
		InputType inputType = InputType.JoystickInput;
		// Event.Type pressedEventType = Type.JOYSTICK_BUTTON_PRESSED;
		// Event.Type releasedEventType = Type.JOYSTICK_BUTTON_RELEASED;
		return new InputMapController<T>(listener, inputValue, inputType, null, null, joystickButtonCode, joystickId);
	}

}
