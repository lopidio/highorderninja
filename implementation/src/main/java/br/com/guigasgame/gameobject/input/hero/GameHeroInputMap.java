package br.com.guigasgame.gameobject.input.hero;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlEnum;

import br.com.guigasgame.input.InputController;
import br.com.guigasgame.input.InputListener;


public class GameHeroInputMap
{
	
	public enum HeroInputDevice
	{
		KEYBOARD,
		JOYSTICK
	}

	@XmlEnum
	public enum HeroInputKey
	{
		LEFT, RIGHT, UP, DOWN,

		JUMP, // X
		ROPE, // L1
		SHOOT, // square
		SLIDE, // R1
		STOP, // circle
		ACTION, // R1
	}

	private Map<HeroInputKey, InputController<HeroInputKey>> inputMap;

	public GameHeroInputMap(Map<HeroInputKey, InputController<HeroInputKey>> inputMap)
	{
		this.inputMap = inputMap;

	}

	public void update()
	{
		for( Entry<GameHeroInputMap.HeroInputKey, InputController<HeroInputKey>> map : inputMap
				.entrySet() )
		{
			map.getValue().handleEvent();
		}
	}

	public void setInputListener(InputListener<HeroInputKey> listener)
	{
		for( Entry<GameHeroInputMap.HeroInputKey, InputController<HeroInputKey>> map : inputMap.entrySet() )
		{
			map.getValue().setInputListener(listener);
		}
	}

	public static GameHeroInputMap loadConfigFileFromDevice(GameHeroInputMap.HeroInputDevice device)
	{
		GameHeroInputMap retorno = null;
		retorno = HeroInputConfigFile.parseFile(device);
		if (null == retorno)
		{
			HeroInputConfigFile.createDefaultFileFromDevice(device);
			retorno = HeroInputConfigFile.parseFile(device);
		}

		return retorno;
	}

	public static GameHeroInputMap loadConfigFromFilename(String filename)
	{
		GameHeroInputMap retorno = HeroInputConfigFile.parseFile(filename);
		return retorno;
	}

	public void setDeviceId(int deviceID) 
	{
		// Insere as chaves no controlador de input
		for( Entry<GameHeroInputMap.HeroInputKey, InputController<HeroInputKey>> map : inputMap.entrySet() )
		{
			map.getValue().setDeviceId(deviceID);
		}
	}

}
