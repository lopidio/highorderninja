package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.frag.HeroFragEventWrapper;
import br.com.guigasgame.frag.FragStatistic;


public class ShootFragEventWrapper extends HeroFragEventWrapper
{

	public ShootFragEventWrapper(PlayableGameHero me)
	{
		super(me);
	}

	@Override
	protected void adjustFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementShoots();
	}

}
