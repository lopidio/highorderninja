package br.com.guigasgame.gameobject.hero.input;

import javafx.scene.input.MouseButton;

import org.jsfml.window.Joystick;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;

import br.com.guigasgame.gameobject.hero.input.GameHeroInput.HeroKey;

public class InputController 
{
//	https://github.com/SFML/SFML/wiki/Tutorial:-Manage-dynamic-key-binding
//	private Boolean status;
//	private Boolean prevStatus;
	private final InputHeroListener keyListener;
	private final HeroKey heroKey;

	private InputType inputType;
	private Event.Type pressedEventType;
	private Event.Type releasedEventType;
	
	private Keyboard.Key keyCode;
	private MouseButton mouseButton;
	private Joystick joystick;


	public InputController(InputHeroListener keyListener, HeroKey heroKey) {
		super();
		this.keyListener = keyListener;
		this.heroKey = heroKey;
	}


	public void updateStatus(Event event)
	{
//		prevStatus = status;
		handleEvent(event);

//		if (status != prevStatus)
//			reportToListener();
	}

	private void handleMouseEvent(Event event)
	{
		if (mouseButton.equals(event.asMouseButtonEvent().button))
		{
			reportToListener(event.type);
		}
	}
	private void handleKeyboardEvent(Event event)
	{
		
	}
	private void handleJoystickEvent(Event event)
	{
		
	}

	private void handleEvent(Event event) 
	{
		if (pressedEventType == event.type || releasedEventType == event.type)
		{
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

	
	private void reportToListener(Event.Type eventType)
	{
		if (keyListener != null)
		{
			if (pressedEventType == eventType)
				keyListener.inputPressed(heroKey);
			if (releasedEventType == eventType)
				keyListener.inputReleased(heroKey);
		}
			
//			if (status && !prevStatus)
//			{
//				keyListener.inputPressed(heroKey);
//			}
//			else if (!status && prevStatus)
//			{
//				keyListener.inputReleased(heroKey);
//			}
	}
	
	public void setEvent(InputType keyboardinput, Key space) {
		//Automaticamente cadastra o press e o release
	}
}
