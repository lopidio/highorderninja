package br.com.guigasgame.input;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class InputController<T>
{

	// https://github.com/SFML/SFML/wiki/Tutorial:-Manage-dynamic-key-binding
	private InputListener<T> inputListener;

	private T key;

	@XmlElement		
	private List<InputHandler> handlers;

	private List<JoystickInputHandler> joystickHandlers;

	private boolean state;
	private boolean prevState;

	public InputController()
	{
		handlers = new ArrayList<InputHandler>();
		joystickHandlers = new ArrayList<JoystickInputHandler>();
		state = false;
		prevState = false;
	}

	public void handleEvent()
	{
		prevState = state;
		for( InputHandler inputHandler : handlers )
		{
			if (inputHandler.handleInput())
				state = true;
		}

		reportToListener();
	}

	private void reportToListener()
	{
		if (inputListener != null)
		{
			if (state)
			{
				inputListener.isPressed(key);
				if (!prevState)
					inputListener.inputPressed(key);
			}
			else
			// if (!state)
			{
				if (prevState)
					inputListener.inputReleased(key);
			}
		}
	}

	public T getKey()
	{
		return key;
	}

	public void setKey(T key)
	{
		this.key = key;
	}

	public void setJoystickId(Integer joystickId)
	{
		for( JoystickInputHandler joystick : joystickHandlers )
		{
			joystick.setJoystickId(joystickId);
		}
	}

	public void setInputListener(InputListener<T> inputListener)
	{
		this.inputListener = inputListener;
	}

	public InputController<T> addInputHandler(InputHandler inputHandler)
	{
		handlers.add(inputHandler);
		return this;
	}

	public InputController<T> addJoystickHandler(JoystickInputHandler joystickInputHandler)
	{
		joystickHandlers.add(joystickInputHandler);
		return addInputHandler(joystickInputHandler);
	}

	// public static <T> InputMapController<T> createKeyboardEvent(List<Key>
	// key)
	// {
	// InputType inputType = InputType.KeyboardInput;
	// // Event.Type pressedEventType = Type.KEY_PRESSED;
	// // Event.Type releasedEventType = Type.KEY_RELEASED;
	// return null;//new InputMapController<T>(inputType, key, null, null,
	// null);
	// }
	//
	// public static <T> InputMapController<T>
	// createMouseClickEvent(List<Mouse.Button> button)
	// {
	// InputType inputType = InputType.MouseInput;
	// // Event.Type pressedEventType = Type.MOUSE_BUTTON_PRESSED;
	// // Event.Type releasedEventType = Type.MOUSE_BUTTON_RELEASED;
	// return null;//return new InputMapController<T>(inputType, null, button,
	// null, null);
	// }
	//
	// public static <T> InputMapController<T>
	// createJoystickButtonEvent(List<Integer> joystickButtonCode)
	// {
	// InputType inputType = InputType.JoystickButtonInput;
	// // Event.Type pressedEventType = Type.JOYSTICK_BUTTON_PRESSED;
	// // Event.Type releasedEventType = Type.JOYSTICK_BUTTON_RELEASED;
	// return null;//return new InputMapController<T>(inputType, null, null,
	// joystickButtonCode, null);
	// }
	//
	// public static <T> InputMapController<T>
	// createJoystickAxisEvent(List<JoystickAxisStruct> joystickAxisCode)
	// {
	// InputType inputType = InputType.JoystickButtonInput;
	// // Event.Type pressedEventType = Type.JOYSTICK_BUTTON_PRESSED;
	// // Event.Type releasedEventType = Type.JOYSTICK_BUTTON_RELEASED;
	// return null;//return new InputMapController<T>(inputType, null, null,
	// null, joystickAxisCode);
	// }

}
