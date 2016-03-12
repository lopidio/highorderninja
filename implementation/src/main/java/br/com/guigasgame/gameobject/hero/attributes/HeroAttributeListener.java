package br.com.guigasgame.gameobject.hero.attributes;


public interface HeroAttributeListener
{

	void isFull(HeroAttribute heroAttribute);

	void gotEmpty(HeroAttribute heroAttribute);

	void changed(HeroAttribute heroAttribute, float value);

	void shootingIncrement(HeroShootingAttribute heroShootingAttribute, float value);

	void shootingIsAble(HeroShootingAttribute heroShootingAttribute);

}
