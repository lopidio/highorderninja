package br.com.guigasgame.round.event;

import br.com.guigasgame.frag.FragStatistic;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

public class SuicideFragEventWrapper extends HeroFragEventWrapper
{
	public SuicideFragEventWrapper(PlayableGameHero me)
	{
		super(me);
	}

	@Override
	public void adjustFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementDeaths(-1);
	}

}
