package br.com.guigasgame.gameobject.hero.attributes;


public interface HeroAttributeListener
{

	void isFull(HeroAttribute heroAttribute);

	void gotEmpty(HeroAttribute heroAttribute);

	void changed(HeroAttribute heroAttribute, float value);

	default void shootingIncrement(HeroShootingAttribute heroShootingAttribute, float value)
	{
		
	}

	default void shootingIsAble(HeroShootingAttribute heroShootingAttribute)
	{
		
	}

}
