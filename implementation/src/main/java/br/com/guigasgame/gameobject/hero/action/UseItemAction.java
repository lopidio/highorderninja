package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.gameobject.projectile.Projectile;


public class UseItemAction extends GameHeroAction
{
	Projectile projectile;	

	public UseItemAction(HeroStateProperties heroStateProperties, Projectile projectile)
	{
		super(heroStateProperties);
		this.projectile = projectile;
	}
	
	@Override
	public boolean childCanExecute(RoundGameHero hero)
	{
		return (heroStateProperties.shoot != null);
	}

	@Override
	public void childExecute(RoundGameHero gameHero)
	{
		gameHero.shoot(projectile);
	}

}
