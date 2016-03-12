package br.com.guigasgame.gameobject.hero.attributes.playable;

import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttributeListener;
import br.com.guigasgame.gameobject.hero.attributes.HeroShootingAttribute;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class RoundHeroAttributes implements UpdatableFromTime
{
	protected HeroAttribute life;
	protected HeroShootingAttribute shurikens;
	protected HeroShootingAttribute smokeBomb;

	public RoundHeroAttributes(HeroAttribute life, HeroShootingAttribute shurikens, HeroShootingAttribute smokeBomb)
	{
		this.life = life;
		this.shurikens = shurikens;
		this.smokeBomb = smokeBomb;
	}

	public HeroAttribute getLife()
	{
		return life;
	}

	public HeroShootingAttribute getShurikens()
	{
		return shurikens;
	}
	
	public HeroShootingAttribute getSmokeBomb()
	{
		return smokeBomb;
	}

	@Override
	public void update(float deltaTime)
	{
		life.update(deltaTime);
		shurikens.update(deltaTime);
		smokeBomb.update(deltaTime);
	}

	public void addListener(HeroAttributeListener listener)
	{
		life.addListener(listener);
		shurikens.addListener(listener);
		smokeBomb.addListener(listener);
	}
	
	public RoundHeroAttributes clone()
	{
		return new RoundHeroAttributes(life.clone(), (HeroShootingAttribute)shurikens.clone(), (HeroShootingAttribute)smokeBomb.clone());
	}

}
