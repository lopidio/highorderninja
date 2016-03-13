package br.com.guigasgame.round.hud.barbellow;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;

public class LifeAttributeBarBellowHud extends HeroAttributeBarBellowHud
{
	public LifeAttributeBarBellowHud(ColorBlender color, Vector2f offset, Vector2f size)
	{
		super(color, offset, size);
	}

	@Override
	public void gotFull(HeroAttribute heroAttribute)
	{
		innerColor.interpolateFromColor(ColorBlender.YELLOW, 0.5f);
	}

	@Override
	public void changed(HeroAttribute heroAttribute, float value)
	{
		adjustBarsLength(heroAttribute);
		if (value < 0)
		{
			innerColor.interpolateFromColor(innerColor.getCurrentColor().darken(2f), 0.5f);
		}
	}

}
