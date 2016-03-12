package br.com.guigasgame.round.hud;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;

public class LifeAttributeBarBellowHud extends HeroAttributeBarBellowHud
{
	public LifeAttributeBarBellowHud(ColorBlender color, Vector2f offset)
	{
		super(color, offset);
	}

	@Override
	public void gotFull(HeroAttribute heroAttribute)
	{
		innerColor.interpolateFromColor(innerColor.getCurrentColor().darken(2f), 0.5f);
	}

	@Override
	public void gotEmpty(HeroAttribute heroAttribute)
	{
	}

	@Override
	public void changed(HeroAttribute heroAttribute, float value)
	{
		// TODO Auto-generated method stub
		
	}

}
