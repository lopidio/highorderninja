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

	private T userData;

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
		boolean hasSomePressed = false;
		for( InputHandler inputHandler : handlers )
		{
			if (inputHandler.handleInput())
				hasSomePressed = true;
		}
		state = hasSomePressed;

		reportToListener();
	}

	private void reportToListener()
	{
		if (inputListener != null)
		{
			if (state)
			{
				inputListener.isPressed(userData);
				if (!prevState)
					inputListener.inputPressed(userData);
			}
			else // if (!state)
			{
				if (prevState)
					inputListener.inputReleased(userData);
			}
		}
	}

	public T getUserData()
	{
		return userData;
	}

	public void setUserData(T userData)
	{
		this.userData = userData;
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

	public InputController<T> addKeyboardHandler(KeyboardInput keyboardInput)
	{
		handlers.add(keyboardInput);
		return this;
	}
	public InputController<T> addMouseHandler(MouseInput mouseInput)
	{
		handlers.add(mouseInput);
		return this;
	}

	public InputController<T> addJoystickHandler(JoystickInputHandler joystickInputHandler)
	{
		joystickHandlers.add(joystickInputHandler);
		handlers.add(joystickInputHandler);
		return this;
	}

}
