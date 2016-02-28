package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
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
	public boolean childCanExecute(GameHero hero)
	{
		return (heroStateProperties.shoot != null);
	}

	@Override
	public void childExecute(GameHero gameHero)
	{
		gameHero.shoot(projectile);
	}

}
