package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public class GetHitAction extends GameHeroAction
{
	private float damage;

	public GetHitAction(float damage)
	{
		this.damage = damage;
	}

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.getHit(damage);
	}

}
