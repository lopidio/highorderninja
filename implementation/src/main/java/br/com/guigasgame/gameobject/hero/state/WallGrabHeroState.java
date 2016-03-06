package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.action.DiagonalJumpAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


public class WallGrabHeroState extends HeroState
{

	private Side wallSide;

	protected WallGrabHeroState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_WALLGRABBING);
		wallSide = gameHero.getForwardSide();
	}

	@Override
	public void stateUpdate(float deltaTime)
	{
		if (gameHero.getCollidableHero().isTouchingGround() && !gameHero.getCollidableHero().isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
		if (!gameHero.isTouchingWallAhead())
		{
			setState(new FallingHeroState(gameHero));
		}
	}
	
	@Override
	protected void jump()
	{
		if (heroStatesProperties.jump != null)
		{
			gameHero.addAction(new DiagonalJumpAction(heroStatesProperties, wallSide.opposite()));
			setState(new JumpingHeroState(gameHero));
		}			
	}

	@Override
	public void stateInputPressed(HeroInputKey key)
	{
		if (wallSide == Side.LEFT && key == HeroInputKey.RIGHT)
		{
			setState(new FallingHeroState(gameHero));
		}
		if (wallSide == Side.RIGHT && key == HeroInputKey.LEFT)
		{
			setState(new FallingHeroState(gameHero));
		}
	}

}
