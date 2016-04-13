package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.frag.FragEventWrapper;
import br.com.guigasgame.frag.FragStatistic;


public class KillFragEventWrapper extends FragEventWrapper
{

	public KillFragEventWrapper(PlayableGameHero me, PlayableGameHero other)
	{
		super(me, other);
	}

	@Override
	protected void adjustOwnerFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementKills();
	}
	
	@Override
	protected void adjustOtherFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementDeaths();
	}

}
