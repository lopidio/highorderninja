package br.com.guigasgame.frag;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

public class SuicideFragEventWrapper extends FragEventWrapper
{
	public SuicideFragEventWrapper(PlayableGameHero me)
	{
		super(me);
	}

	@Override
	protected void adjustOwnerFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementDeaths();
		fragStatistic.incrementSuicides();
	}

}
