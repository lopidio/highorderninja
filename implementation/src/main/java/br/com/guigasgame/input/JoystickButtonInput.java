package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.jsfml.window.Joystick;


@XmlAccessorType(XmlAccessType.NONE)
public class JoystickButtonInput extends InputHandler
{
	@XmlElement
	private int joystickButtonCode;
	
	/**
	 * DO NOT USE
	 */
	public JoystickButtonInput()
	{
		this.joystickButtonCode = -1;
	}	
	
	public JoystickButtonInput(int joystickButtonCode)
	{
		super();
		this.joystickButtonCode = joystickButtonCode;
	}

	@Override
	public boolean handleInput()
	{
		if (getDeviceId() == null)
			return false;
		
		if (Joystick.isButtonPressed(getDeviceId(), joystickButtonCode))
			return true;
		return false;
	}
}
