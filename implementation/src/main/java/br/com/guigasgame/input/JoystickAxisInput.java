package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;

import org.jsfml.window.Joystick;
import org.jsfml.window.Joystick.Axis;


@XmlAccessorType(XmlAccessType.NONE)
public class JoystickAxisInput extends InputHandler
{
	private static final float TOLLERANCE = 50f;
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
		if (getDeviceId() == null)
			return false;
		
		float axisValue = Joystick.getAxisPosition(getDeviceId(), axis);
		switch (axisSignal)
		{
			case POSITIVE:
				return (axisValue > TOLLERANCE);
			case NEGATIVE:
				return (axisValue < -TOLLERANCE);
			default:
				return false;
		}
	}
	
}
