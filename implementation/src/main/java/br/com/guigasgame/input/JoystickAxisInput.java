package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;

import org.jsfml.window.Joystick;
import org.jsfml.window.Joystick.Axis;


@XmlAccessorType(XmlAccessType.NONE)
public class JoystickAxisInput extends JoystickInputHandler
{
	private static final float TOLLERANCE = 60f;
	@XmlElement
	private Joystick.Axis axis;
	
	@XmlEnum
	public enum AxisSignal
	{
		POSITIVE,
		NEGATIVE
	}

	@XmlElement
	private AxisSignal axisSignal;

	/**
	 * DO NOT USE
	 */
	public JoystickAxisInput()
	{
		this.axis = null;
		this.axisSignal = null;
	}	
	
	public JoystickAxisInput(Axis axis, AxisSignal axisSignal)
	{
		this.axis = axis;
		this.axisSignal = axisSignal;
	}

	@Override
	public boolean handleInput()
	{
		if (getJoystickId() == -1)
			return false;
		float axisValue = Joystick.getAxisPosition(getJoystickId(), axis);
		if (axisValue > TOLLERANCE)
		{
			return axisSignal == AxisSignal.POSITIVE;
		}
		else if (axisValue < TOLLERANCE)
		{
			return axisSignal == AxisSignal.NEGATIVE;
		}
		return false;
	}
	
}
