package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


public class FallingHeroState extends HeroState
{

	public FallingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_FALLING);
	}

	@Override
	public void isPressed(HeroInputKey key)
	{

		if (key == HeroInputKey.LEFT)
		{
			setHeroForwardSide(Side.LEFT);
//			moveLeft();
		}
		else if (key == HeroInputKey.RIGHT)
		{
			setHeroForwardSide(Side.RIGHT);
//			moveRight();
		}
		if (gameHero.isTouchingWallAhead())
		{
			if (key == HeroInputKey.LEFT && gameHero.getForwardSide() == Side.LEFT)
			{
				setState(new WallGrabHeroState(gameHero));
			}
			else if (key == HeroInputKey.RIGHT && gameHero.getForwardSide() == Side.RIGHT)
			{
				setState(new WallGrabHeroState(gameHero));
			}
		}
	}
	public void stateInputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.SHOOT)
		{
			shoot();
		}
	}

	@Override
	public void updateState(float deltaTime)
	{
		if (gameHero.isTouchingGround() && !gameHero.isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
	}

}
