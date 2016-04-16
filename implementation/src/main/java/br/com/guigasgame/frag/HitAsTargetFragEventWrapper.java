package br.com.guigasgame.frag;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.round.event.HeroFragEventWrapper;


public class HitAsTargetFragEventWrapper extends HeroFragEventWrapper
{

	public HitAsTargetFragEventWrapper(PlayableGameHero me, PlayableGameHero other, Projectile projectile)
	{
		super(me, other);
	}

	@Override
	public void adjustFragStatistic(FragStatistic fragStatistic)
	{
		if (getMyTeamId() != getOtherTeamId())
			fragStatistic.incrementHitAsTarget(other.getHeroProperties().getPlayerId());
	}

}
