package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.jsfml.window.Joystick;
import org.jsfml.window.Joystick.Axis;


@XmlAccessorType(XmlAccessType.NONE)
public class JoystickAxisInput extends JoystickInputHandler
{
	private static final float TOLLERANCE = 60f;
	@XmlElement
	private Joystick.Axis axis;
	@XmlElement
	private boolean positive;
	
	public JoystickAxisInput(Axis axis, boolean positive)
	{
		this.axis = axis;
		this.positive = positive;
	}

	@Override
	public boolean handleInput()
	{
		if (getJoystickId() == -1)
			return false;
		float axisValue = Joystick.getAxisPosition(getJoystickId(), axis);
		if (axisValue > TOLLERANCE)
		{
			return positive;
		}
		else if (axisValue < TOLLERANCE)
		{
			return !positive;
		}
		return false;
	}
	
}
