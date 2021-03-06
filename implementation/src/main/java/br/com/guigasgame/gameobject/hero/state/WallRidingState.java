package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.hero.action.DiagonalJumpAction;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

public class WallRidingState extends HeroState 
{
	protected WallRidingState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_WALLRIDING);
	}
	
	@Override
	protected void jump()
	{
		if (heroStatesProperties.jump != null)
		{
			if (isHeroInputMapPressed(HeroInputKey.ACTION))
			{
				setState(new AirSpinHeroState(gameHero, gameHero.getForwardSide().opposite()));
			}
			else //if (isHeroInputPressed(HeroInputKey.UP))
			{
				gameHero.addAction(new DiagonalJumpAction(heroStatesProperties, gameHero.getForwardSide().opposite()));
				setState(new JumpingHeroState(gameHero));
			}
		}			
	}	
	
	@Override
	public void stateUpdate(float deltaTime)
	{
		if (gameHero.getCollidableHero().getBodyLinearVelocity().y >= WorldConstants.MOVING_TOLERANCE)
		{
			setState(new BackFlipHeroState(gameHero));
		}
		if (!gameHero.isTouchingWallAhead())
		{
			setState(new JumpingHeroState(gameHero));
		}
	}
}
