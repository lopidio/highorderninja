package br.com.guigasgame.gameobject.input;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.jsfml.window.Joystick;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;

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
	private List<Keyboard.Key> keysCodeList;
	@XmlElement
	private List<Mouse.Button> mouseButtonsList;
	@XmlElement
	private List<Integer>joystickButtonsCodeList;
	@XmlElement
	private int joystickId;

	private boolean state;
	private boolean prevState;
	
	/**
	 * DO NOT USE. Required jaxb feature
	 */
	public InputMapController()
	{
		
	}

	private InputMapController(InputListener<T> keyListener, T inputValue, InputType inputType, List<Key> keysCodeList,
			List<Button> mouseButtonsList, List<Integer> joystickButtonsCodeList, int joystickId) {
		super();
		this.inputListener = keyListener;
		this.inputValue = inputValue;
		this.inputType = inputType;
		this.keysCodeList = keysCodeList;
		this.mouseButtonsList = mouseButtonsList;
		this.joystickButtonsCodeList = joystickButtonsCodeList;
		this.joystickId = joystickId;
		state = false;
		prevState = false;
	}

	public void handleEvent() {
		prevState = state;

		switch (inputType) {
		case MouseInput:
			state = (handleMouseListButton());
			break;
		case KeyboardInput:
			state = (handleKeyboardKeysList());
			break;
		case JoystickInput:
			state = (handleJoystickButtonsList());
			break;
		default:
		}

		reportToListener();
	}
	
	private boolean handleMouseListButton()
	{
		for (Button mouseCode : mouseButtonsList) {
			if (Mouse.isButtonPressed(mouseCode))
				return true;
		}
		return false;
	}

	private boolean handleKeyboardKeysList()
	{
		for (Key keyCode : keysCodeList) {
			if (Keyboard.isKeyPressed(keyCode))
				return true;
		}
		return false;
	}

	private boolean handleJoystickButtonsList()
	{
		for (Integer joystickButtonCode : joystickButtonsCodeList) {
			if (Joystick.isButtonPressed(joystickId, joystickButtonCode))
			return true;
		}
		return false;
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

	public static <T> InputMapController<T> createKeyboardEvent(List<Key> key, InputListener<T> listener, T inputValue) {
		InputType inputType = InputType.KeyboardInput;
		// Event.Type pressedEventType = Type.KEY_PRESSED;
		// Event.Type releasedEventType = Type.KEY_RELEASED;
		return new InputMapController<T>(listener, inputValue, inputType, key, null, null, -1);
	}

	public static <T> InputMapController<T> createMouseClickEvent(List<Mouse.Button> button, InputListener<T> listener,
			T inputValue) {
		InputType inputType = InputType.MouseInput;
		// Event.Type pressedEventType = Type.MOUSE_BUTTON_PRESSED;
		// Event.Type releasedEventType = Type.MOUSE_BUTTON_RELEASED;
		return new InputMapController<T>(listener, inputValue, inputType, null, button, null, -1);
	}

	public static <T> InputMapController<T> createJoystickButtonEvent(List<Integer> joystickButtonCode, int joystickId,
			InputListener<T> listener, T inputValue) {
		InputType inputType = InputType.JoystickInput;
		// Event.Type pressedEventType = Type.JOYSTICK_BUTTON_PRESSED;
		// Event.Type releasedEventType = Type.JOYSTICK_BUTTON_RELEASED;
		return new InputMapController<T>(listener, inputValue, inputType, null, null, joystickButtonCode, joystickId);
	}

}
