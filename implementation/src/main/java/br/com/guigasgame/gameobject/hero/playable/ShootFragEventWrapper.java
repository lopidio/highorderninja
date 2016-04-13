package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.frag.FragEventWrapper;
import br.com.guigasgame.frag.FragStatistic;


public class ShootFragEventWrapper extends FragEventWrapper
{

	public ShootFragEventWrapper(PlayableGameHero me)
	{
		super(me);
	}

	@Override
	protected void adjustOwnerFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementShoots();
	}

}
