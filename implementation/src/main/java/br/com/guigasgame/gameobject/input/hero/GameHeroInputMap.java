package br.com.guigasgame.gameobject.input.hero;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlEnum;

import br.com.guigasgame.input.InputListener;
import br.com.guigasgame.input.InputController;


public class GameHeroInputMap
{

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

	public GameHeroInputMap(
			Map<HeroInputKey, InputController<HeroInputKey>> inputMap)
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
		for( Entry<GameHeroInputMap.HeroInputKey, InputController<HeroInputKey>> map : inputMap
				.entrySet() )
		{
			map.getValue().setInputListener(listener);
		}
	}

	public static GameHeroInputMap loadFromConfigFile(int playerID)
	{
		GameHeroInputMap retorno = null;
		retorno = HeroInputConfigFile.parseFile(playerID);
		if (null == retorno)
		{
			HeroInputConfigFile.createDefaultFileFromPlayerID(playerID);
			retorno = HeroInputConfigFile.parseFile(playerID);
		}

		return retorno;
	}

}
