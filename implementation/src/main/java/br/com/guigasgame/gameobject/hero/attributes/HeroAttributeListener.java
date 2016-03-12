package br.com.guigasgame.gameobject.hero.attributes;


public interface HeroAttributeListener
{

	default void gotFull(HeroAttribute heroAttribute)
	{
		
	}

	default void gotEmpty(HeroAttribute heroAttribute)
	{
		
	}

	default void changed(HeroAttribute heroAttribute, float value)
	{
		
	}

	default void shootingIncrement(HeroShootingAttribute heroShootingAttribute, float value)
	{
		
	}

	default void shootingIsAble(HeroShootingAttribute heroShootingAttribute)
	{
		
	}

}
