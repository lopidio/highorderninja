package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.gameobject.projectile.Projectile;



public class ShootRopeAction extends GameHeroAction
{
	Projectile projectile;	

	public ShootRopeAction(HeroStateProperties heroStateProperties, Projectile projectile)
	{
		super(heroStateProperties);
		this.projectile = projectile;
	}
	
	@Override
	public boolean childCanExecute(PlayableGameHero hero)
	{
		return (heroStateProperties.rope != null);
	}

	@Override
	public void childExecute(PlayableGameHero gameHero)
	{
		gameHero.shoot(projectile);
	}

}
