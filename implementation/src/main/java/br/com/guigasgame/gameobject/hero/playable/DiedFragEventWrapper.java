package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.frag.HeroFragEventWrapper;
import br.com.guigasgame.frag.FragStatistic;


public class DiedFragEventWrapper extends HeroFragEventWrapper
{
	private PlayableGameHero me;

	public DiedFragEventWrapper(PlayableGameHero me, PlayableGameHero other)
	{
		super(me, other);
		this.me = me;
	}

	public DiedFragEventWrapper(PlayableGameHero me)
	{
		super(me);
		this.me = me;
	}

	@Override
	protected void adjustFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementDeaths();
	}

	public PlayableGameHero getPlayableGameHero()
	{
		return me;
	}


}
