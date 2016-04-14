package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.frag.HeroFragEventWrapper;
import br.com.guigasgame.frag.FragStatistic;


public class KillFragEventWrapper extends HeroFragEventWrapper
{

	public KillFragEventWrapper(PlayableGameHero me, PlayableGameHero other)
	{
		super(me, other);
	}

	@Override
	protected void adjustFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementKills();
	}
	
}
