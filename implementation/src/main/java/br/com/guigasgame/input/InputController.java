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
	private static final float DOUBLE_TAP_INTERVAL = 0.2f; //seconds
	
	// https://github.com/SFML/SFML/wiki/Tutorial:-Manage-dynamic-key-binding
	private InputListener<T> inputListener;

	private T userData;

	@XmlElement		
	private List<InputHandler> handlers;

	private boolean state;
	private boolean prevState;
	private float doubleTapCounter;

	public InputController()
	{
		handlers = new ArrayList<InputHandler>();
		state = false;
		prevState = false;
		doubleTapCounter = 2*DOUBLE_TAP_INTERVAL;
	}

	public void handleEvent(float deltaTime)
	{
		if (null == inputListener)
			return;
		
		incrementDoubleTapCounter(deltaTime);
		prevState = state;
		boolean hasSomePressed = false;
		for( InputHandler inputHandler : handlers )
		{
			if (inputHandler.handleInput())
			{
				hasSomePressed = true;
			}
		}
		state = hasSomePressed;

		reportToListener();
	}

	private void reportToListener()
	{
		if (state)
		{
			activate();
		}
		else // if (!state)
		{
			deactivate();
		}
	}

	private void deactivate()
	{
		if (prevState)
		{
			inputListener.inputReleased(userData);
		}
	}

	private void activate()
	{
		inputListener.isPressing(userData);
		if (!prevState)
		{
			inputListener.inputPressed(userData);
			if (checkDoubleTap())
				inputListener.doubleTapInput(userData);
			doubleTapCounter = 0;
		}
	}

	private void incrementDoubleTapCounter(float deltaTime)
	{
		if (doubleTapCounter < 2*DOUBLE_TAP_INTERVAL)
			doubleTapCounter += deltaTime;
	}

	private boolean checkDoubleTap()
	{
		return doubleTapCounter <= DOUBLE_TAP_INTERVAL;
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
