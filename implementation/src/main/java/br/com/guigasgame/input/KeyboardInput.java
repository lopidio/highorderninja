package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

@XmlAccessorType(XmlAccessType.NONE)
public class KeyboardInput extends InputHandler
{

	@XmlElement
	private Keyboard.Key keysCode;

	public KeyboardInput(Key keysCode)
	{
		super();
		this.keysCode = keysCode;
	}

	@Override
	public boolean handleInput()
	{
		if (Keyboard.isKeyPressed(keysCode))
			return true;
		return false;
	}

}
