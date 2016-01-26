package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.NONE)
public abstract class JoystickInputHandler extends InputHandler
{
	private int joystickId;
	
	public JoystickInputHandler()
	{
		joystickId = -1;
	}

	public int getJoystickId()
	{
		return joystickId;
	}
	
	public void setJoystickId(int joystickId)
	{
		this.joystickId = joystickId;
	}
	
}
