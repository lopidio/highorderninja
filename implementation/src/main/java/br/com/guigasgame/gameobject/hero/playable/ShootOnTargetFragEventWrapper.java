package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.frag.FragEventWrapper;
import br.com.guigasgame.frag.FragStatistic;


public class ShootOnTargetFragEventWrapper extends FragEventWrapper
{

	public ShootOnTargetFragEventWrapper(PlayableGameHero me, PlayableGameHero other)
	{
		super(me, other);
	}

	@Override
	protected void adjustOwnerFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementShootsOnTarget();
	}
	
	@Override
	protected void adjustOtherFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementHitAsTarget();
	}

}
