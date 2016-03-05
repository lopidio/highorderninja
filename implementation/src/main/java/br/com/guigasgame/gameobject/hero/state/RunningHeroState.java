package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.action.SideOrientationHeroSetter;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class RunningHeroState extends HeroState
{

	protected RunningHeroState(RoundGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_RUNNING);
	}

	@Override
	protected void stateInputIsPressing(HeroInputKey inputValue) 
	{
		if (inputValue == HeroInputKey.ACTION)
		{
			if (gameHero.getCollidableHero().getBodyLinearVelocity().length() >= new Vec2(heroStatesProperties.maxSpeed.x, 0).length()*0.99) //99%
			{
				setState(new SuperRunningState(gameHero));
			}			
		}
	}

	@Override
	protected void stateDoubleTapInput(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.LEFT || inputValue == HeroInputKey.RIGHT)
		{
			setState(new SlidingHeroState(gameHero));
		}
	}
	
	@Override
	public void stateUpdate(float deltaTime)
	{
		if (!gameHero.getCollidableHero().isMoving())
		{
			setState(new StandingHeroState(gameHero));
		}
		else if (gameHero.getCollidableHero().isFallingDown() && !gameHero.isTouchingGround())
		{
			setState(new FallingHeroState(gameHero));
		}

		Side side = gameHero.getCollidableHero().movingToSide();
		if (side != Side.UNKNOWN && side != gameHero.getForwardSide())
			gameHero.addAction(new SideOrientationHeroSetter(side, heroStatesProperties));

	}
}
