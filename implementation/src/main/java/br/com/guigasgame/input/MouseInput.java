package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;


@XmlAccessorType(XmlAccessType.NONE)
public class MouseInput extends InputHandler
{
	@XmlElement
	private Mouse.Button mouseButton;


	public MouseInput(Button mouseButton)
	{
		super();
		this.mouseButton = mouseButton;
	}

	@Override
	public boolean handleInput()
	{
		if (Mouse.isButtonPressed(mouseButton))
			return true;
		return false;
	}

}
