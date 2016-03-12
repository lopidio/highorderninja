package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
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
	public boolean childCanExecute(PlayableGameHero hero)
	{
		return (heroStateProperties.shoot != null && hero.canUseItem());
	}

	@Override
	public void childExecute(PlayableGameHero gameHero)
	{
		gameHero.shoot(projectile);
	}

}
