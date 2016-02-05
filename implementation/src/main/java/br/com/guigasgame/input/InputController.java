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

	private boolean state;
	private boolean prevState;

	public InputController()
	{
		handlers = new ArrayList<InputHandler>();
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
				inputListener.isPressing(userData);
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

	public void setDeviceId(Integer deviceId)
	{
		for( InputHandler inputHandler : handlers )
		{
			inputHandler.setDeviceId(deviceId);
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
}
