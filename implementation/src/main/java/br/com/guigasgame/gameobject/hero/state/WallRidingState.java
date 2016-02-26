package br.com.guigasgame.gameobject.hero.state;

import org.jsfml.graphics.Color;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.DiagonalJumpAction;

public class WallRidingState extends HeroState 
{
	protected WallRidingState(GameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_WALLRIDING);
		animation.setColor(Color.YELLOW);
	}
	
	@Override
	protected void stateOnEnter()
	{
		super.stateOnEnter();
	}

	@Override
	protected void jump()
	{
		if (heroStatesProperties.jump != null)
		{
			gameHero.addAction(new DiagonalJumpAction(heroStatesProperties, gameHero.getForwardSide().opposite()));
			setState(new JumpingHeroState(gameHero));
		}			
	}	
	
	@Override
	public void stateUpdate(float deltaTime)
	{
		if (gameHero.getCollidableHero().getBodyLinearVelocity().y >= WorldConstants.MOVING_TOLERANCE)
		{
			gameHero.addAction(new DiagonalJumpAction(heroStatesProperties, gameHero.getForwardSide().opposite()));
			//TODO Add backflip state
			setState(new FallingHeroState(gameHero));
		}
	}
}
