package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public class DeadlySceneryDamageAction extends GameHeroAction
{
	private float damage;
	
	public DeadlySceneryDamageAction(float damage)
	{
		this.damage = damage;
	}

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.deadlySceneryHit(damage);
	}

}
