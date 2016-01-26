package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public abstract class InputHandler
{
	private Integer deviceId;
	
	public InputHandler()
	{
		deviceId = null;
	}

	public InputHandler(Integer deviceId)
	{
		this.deviceId = deviceId;
	}

	public Integer getDeviceId()
	{
		return deviceId;
	}
	
	public void setDeviceId(Integer joystickId)
	{
		this.deviceId = joystickId;
	}
	
	abstract boolean handleInput();
}
