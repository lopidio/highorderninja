package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class SuperJumpingHeroState extends HeroState
{

	private boolean doubleJumpAllowed;

	protected SuperJumpingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_SUPERJUMP);
		doubleJumpAllowed = true;
	}

	@Override
	public void onEnter()
	{
		jump();
	}

	@Override
	public void inputPressed(HeroInputKey key)
	{
		if (doubleJumpAllowed && key == HeroInputKey.JUMP)
		{
			if (heroStatesProperties.canJump && key == HeroInputKey.JUMP)
			{
				System.out.println("Super Double jump!");
				jump();
				doubleJumpAllowed = false;
			}
		}
	}

	@Override
	public void updateState(float deltaTime)
	{
		if (gameHero.isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
	}

	@Override
	public void isPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.LEFT)
		{
			setHeroForwardSide(Side.LEFT);
			// moveLeft();
		}
		else if (key == HeroInputKey.RIGHT)
		{
			setHeroForwardSide(Side.RIGHT);
			// moveRight();
		}
	}

}
