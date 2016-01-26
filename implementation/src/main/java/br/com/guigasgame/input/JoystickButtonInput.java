package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.jsfml.window.Joystick;


@XmlAccessorType(XmlAccessType.NONE)
public class JoystickButtonInput extends JoystickInputHandler
{
	@XmlElement
	private int joystickButtonCode;

	public JoystickButtonInput(int joystickButtonCode)
	{
		super();
		this.joystickButtonCode = joystickButtonCode;
	}

	@Override
	public boolean handleInput()
	{
		if (getJoystickId() == -1)
			return false;
		
		if (Joystick.isButtonPressed(getJoystickId(), joystickButtonCode))
			return true;
		return false;
	}
}
