package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.action.BackFlipAction;

public class BackFlipHeroState extends HeroState
{
	protected BackFlipHeroState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_BACKFLIPPING);
//		setAnimationsColor(Color.BLACK);
	}
	
	@Override
	protected void stateOnEnter()
	{
		gameHero.addAction(new BackFlipAction(heroStatesProperties, gameHero.getForwardSide().opposite()));
	}
	
	public void stateUpdate(float deltaTime)
	{
		if (gameHero.getCollidableHero().isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
		else if (gameHero.getCollidableHero().isAscending())
		{
			setState(new JumpingHeroState(gameHero));
		}
	}

}
