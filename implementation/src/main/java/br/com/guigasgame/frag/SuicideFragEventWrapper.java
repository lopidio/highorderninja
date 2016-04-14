package br.com.guigasgame.frag;

import br.com.guigasgame.gameobject.hero.playable.DiedFragEventWrapper;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

public class SuicideFragEventWrapper extends DiedFragEventWrapper
{
	public SuicideFragEventWrapper(PlayableGameHero me)
	{
		super(me);
	}

	@Override
	protected void adjustFragStatistic(FragStatistic fragStatistic)
	{
		super.adjustFragStatistic(fragStatistic);
		fragStatistic.incrementSuicides();
	}

}
