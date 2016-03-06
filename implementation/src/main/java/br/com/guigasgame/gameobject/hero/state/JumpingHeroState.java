package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.action.JumpAction;
import br.com.guigasgame.gameobject.hero.action.JumpPressingHelp;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class JumpingHeroState extends HeroState
{

	private boolean doubleJumpAllowed;

	protected JumpingHeroState(RoundGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_ASCENDING);
		doubleJumpAllowed = true;
		
	}
	
	@Override
	protected void jump()
	{
		if (doubleJumpAllowed)
		{
			gameHero.addAction(new JumpAction(heroStatesProperties));
			doubleJumpAllowed = false;
		}
	}

	@Override
	public void stateUpdate(float deltaTime)
	{
		if (gameHero.getCollidableHero().isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
		else if (!gameHero.getCollidableHero().isAscending())
		{
			setState(new StandingHeroState(gameHero));
		}
		else if (gameHero.isTouchingWallAhead())
		{
			if (gameHero.getForwardSide() == Side.LEFT && gameHero.getCollidableHero().isGoingTo(Side.LEFT))
			{
				setState(new WallGrabHeroState(gameHero));
			}
			else if (gameHero.getForwardSide() == Side.RIGHT && gameHero.getCollidableHero().isGoingTo(Side.RIGHT))
			{
				setState(new WallGrabHeroState(gameHero));
			}
		}
		
	}
	
	@Override
	public void stateInputIsPressing(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			gameHero.addAction(new JumpPressingHelp(heroStatesProperties));
		}
	}

	
	@Override
	protected void stateInputPressed(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ACTION)
		{
			if (gameHero.getCollidableHero().getBodyLinearVelocity().length() >= new Vec2(heroStatesProperties.maxSpeed.x, 0).length()*0.99)
			{
				if (isHeroInputPressed(HeroInputKey.LEFT))
					setState(new AirSpinHeroState(gameHero, Side.LEFT));
				else if (isHeroInputPressed(HeroInputKey.RIGHT))
					setState(new AirSpinHeroState(gameHero, Side.RIGHT));
				else
					setState(new StopMovementState(gameHero));
			}
			else
				setState(new StopMovementState(gameHero));
		}
	}
	
}
