package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.gameobject.projectile.Projectile;



public class ShootAction extends GameHeroAction
{
	Projectile projectile;	

	public ShootAction(HeroStateProperties heroStateProperties, Projectile projectile)
	{
		super(heroStateProperties);
		this.projectile = projectile;
	}
	
	@Override
	public boolean childCanExecute(PlayableGameHero hero)
	{
		return (heroStateProperties.shoot != null);
	}

	@Override
	public void childExecute(PlayableGameHero gameHero)
	{
		gameHero.shoot(projectile);
	}

}
