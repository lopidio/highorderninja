package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.frag.HeroFragEventWrapper;
import br.com.guigasgame.frag.FragStatistic;


public class ShootOnTargetFragEventWrapper extends HeroFragEventWrapper
{

	public ShootOnTargetFragEventWrapper(PlayableGameHero me, PlayableGameHero other)
	{
		super(me, other);
	}

	@Override
	protected void adjustFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementShootsOnTarget();
	}
	
}
