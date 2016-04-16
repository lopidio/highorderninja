package br.com.guigasgame.frag;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.round.event.HeroFragEventWrapper;


public class ShootFragEventWrapper extends HeroFragEventWrapper
{

	public ShootFragEventWrapper(PlayableGameHero me, Projectile projectile)
	{
		super(me);
	}

	@Override
	public void adjustFragStatistic(FragStatistic fragStatistic)
	{
		fragStatistic.incrementShoots();
	}

}
