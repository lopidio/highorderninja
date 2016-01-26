package br.com.guigasgame.input;

import javax.xml.bind.annotation.XmlEnum;


@XmlEnum
public enum InputType
{
	KeyboardInput,
	MouseInput,
	JoystickButtonInput,
	JoystickAxisInput
}
