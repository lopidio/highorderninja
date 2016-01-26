package br.com.guigasgame.input;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.jsfml.window.Joystick;
import org.jsfml.window.Joystick.Axis;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;


public class InputMapController<T>
{

	public static class JoystickAxisStruct
	{
		public static float TOLLERANCE = 60f;
		public Joystick.Axis axis;
		public boolean positive;
		public JoystickAxisStruct(Axis axis, boolean positive)
		{
			this.axis = axis;
			this.positive = positive;
		}

	}

	// https://github.com/SFML/SFML/wiki/Tutorial:-Manage-dynamic-key-binding
	private InputListener<T> inputListener;

	private T inputValue;

	@javax.xml.bind.annotation.XmlElement
	private InputType inputType;

	@XmlElement
	private List<Keyboard.Key> keysCodeList;
	@XmlElement
	private List<Mouse.Button> mouseButtonsList;
	@XmlElement
	private List<Integer> joystickButtonsCodeList;
	@XmlElement
	private List<JoystickAxisStruct> joystickAxisList;

	private Integer joystickId;

	private boolean state;
	private boolean prevState;

	/**
	 * DO NOT USE. Required jaxb feature
	 */
	InputMapController()
	{

	}

	private InputMapController(InputType inputType, List<Key> keysCodeList, List<Button> mouseButtonsList, 
			List<Integer> joystickButtonsCodeList, List<JoystickAxisStruct> joystickAxisList)
	{
		super();
		this.inputType = inputType;
		this.keysCodeList = keysCodeList;
		this.mouseButtonsList = mouseButtonsList;
		this.joystickButtonsCodeList = joystickButtonsCodeList;
		this.joystickAxisList = joystickAxisList;
		this.joystickId = 0;
		state = false;
		prevState = false;
	}

	public void handleEvent()
	{
		prevState = state;

		switch (inputType)
		{
			case MouseInput:
				state = (handleMouseListButton());
				break;
			case KeyboardInput:
				state = (handleKeyboardKeysList());
				break;
			case JoystickButtonInput:
				state = (handleJoystickButtonsList());
				break;
			case JoystickAxisInput:
				state = (handleJoystickAxisList());
				break;
			default:
		}

		reportToListener();
	}

	private boolean handleJoystickAxisList()
	{
		for( JoystickAxisStruct joystick : joystickAxisList )
		{
			float axisValue = Joystick.getAxisPosition(joystickId, joystick.axis);
			if (axisValue > JoystickAxisStruct.TOLLERANCE)
			{
				return joystick.positive;
			}
			else if (axisValue < JoystickAxisStruct.TOLLERANCE)
			{
				return !joystick.positive;
			}
		}
		return false;
	}

	private boolean handleMouseListButton()
	{
		for( Button mouseCode : mouseButtonsList )
		{
			if (Mouse.isButtonPressed(mouseCode))
				return true;
		}
		return false;
	}

	private boolean handleKeyboardKeysList()
	{
		for( Key keyCode : keysCodeList )
		{
			if (Keyboard.isKeyPressed(keyCode))
				return true;
		}
		return false;
	}

	private boolean handleJoystickButtonsList()
	{
		for( Integer joystickButtonCode : joystickButtonsCodeList )
		{
			if (Joystick.isButtonPressed(joystickId, joystickButtonCode))
				return true;
		}
		return false;
	}

	private void reportToListener()
	{
		if (inputListener != null)
		{
			if (state)
			{
				inputListener.isPressed(inputValue);
				if (!prevState)
					inputListener.inputPressed(inputValue);
			}
			else
			// if (!state)
			{
				if (prevState)
					inputListener.inputReleased(inputValue);
			}
		}
	}

	public T getHeroKey()
	{
		return inputValue;
	}

	public void setInputValue(T inputValue)
	{
		this.inputValue = inputValue;
	}
	
	public void setJoystickId(Integer joystickId)
	{
		this.joystickId = joystickId;
	}

	public void setInputListener(InputListener<T> inputListener)
	{
		this.inputListener = inputListener;
	}

	public static <T> InputMapController<T> createKeyboardEvent(List<Key> key)
	{
		InputType inputType = InputType.KeyboardInput;
		// Event.Type pressedEventType = Type.KEY_PRESSED;
		// Event.Type releasedEventType = Type.KEY_RELEASED;
		return new InputMapController<T>(inputType, key, null, null, null);
	}

	public static <T> InputMapController<T> createMouseClickEvent(List<Mouse.Button> button)
	{
		InputType inputType = InputType.MouseInput;
		// Event.Type pressedEventType = Type.MOUSE_BUTTON_PRESSED;
		// Event.Type releasedEventType = Type.MOUSE_BUTTON_RELEASED;
		return new InputMapController<T>(inputType, null, button, null, null);
	}

	public static <T> InputMapController<T> createJoystickButtonEvent(List<Integer> joystickButtonCode)
	{
		InputType inputType = InputType.JoystickButtonInput;
		// Event.Type pressedEventType = Type.JOYSTICK_BUTTON_PRESSED;
		// Event.Type releasedEventType = Type.JOYSTICK_BUTTON_RELEASED;
		return new InputMapController<T>(inputType, null, null, joystickButtonCode, null);
	}

	public static <T> InputMapController<T> createJoystickAxisEvent(List<JoystickAxisStruct> joystickAxisCode)
	{
		InputType inputType = InputType.JoystickButtonInput;
		// Event.Type pressedEventType = Type.JOYSTICK_BUTTON_PRESSED;
		// Event.Type releasedEventType = Type.JOYSTICK_BUTTON_RELEASED;
		return new InputMapController<T>(inputType, null, null, null, joystickAxisCode);
	}

}
