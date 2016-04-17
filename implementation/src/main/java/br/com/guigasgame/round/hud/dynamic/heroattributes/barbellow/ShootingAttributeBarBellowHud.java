package br.com.guigasgame.round.hud.dynamic.heroattributes.barbellow;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;
import br.com.guigasgame.gameobject.hero.attributes.HeroShootingAttribute;

public class ShootingAttributeBarBellowHud extends HeroAttributeBarBellowHud
{
	public ShootingAttributeBarBellowHud(ColorBlender color, Vector2f offset, Vector2f size)
	{
		super(color, offset, size);
	}
	
	@Override
	public void gotFull(HeroAttribute heroAttribute)
	{
		innerColor.interpolateFromColor(innerColor.getCurrentColor().darken(10), 0.5f);
//		outterColor.interpolateToColor(innerColor.getCurrentColor().darken(2.5f), 0.5f);
	}

	@Override
	public void attributeGotEmpty(HeroAttribute heroAttribute)
	{
//		outterColor.interpolateToColor(outterColor.getCurrentColor().lighten(3f).makeTranslucid(2), 0.5f);
	}
	
	@Override
	public void shootingIsAble(HeroShootingAttribute heroShootingAttribute)
	{
		innerColor.interpolateFromColor(innerColor.getCurrentColor().lighten(2f), 0.5f);
	}

	@Override
	public void changed(HeroAttribute heroAttribute, float value)
	{
		innerColor.interpolateFromColor(innerColor.getCurrentColor().inverse(), 0.1f);
		adjustBarsLength(heroAttribute);
	}

}
