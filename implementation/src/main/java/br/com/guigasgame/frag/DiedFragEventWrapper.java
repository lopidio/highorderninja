package br.com.guigasgame.frag;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.round.event.HeroFragEventWrapper;


public class DiedFragEventWrapper extends HeroFragEventWrapper
{
	public DiedFragEventWrapper(PlayableGameHero me, PlayableGameHero other, Projectile projectile)
	{
		super(me, other);
	}

	public DiedFragEventWrapper(PlayableGameHero me)
	{
		super(me, null);
	}

	@Override
	public void adjustFragStatistic(FragStatistic fragStatistic)
	{
		if (other != null)
			fragStatistic.incrementDeaths(other.getHeroProperties().getPlayerId());
		else
			fragStatistic.incrementDeaths(-1);
	}


}
