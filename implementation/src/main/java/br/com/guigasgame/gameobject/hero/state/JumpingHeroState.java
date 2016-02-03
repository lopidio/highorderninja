package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.DoubleJumpAction;
import br.com.guigasgame.gameobject.hero.action.JumpAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class JumpingHeroState extends HeroState
{

	private boolean doubleJumpAllowed;

	protected JumpingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_ASCENDING);
		doubleJumpAllowed = true;
	}

	@Override
	public void onEnter()
	{
		gameHero.addAction(new JumpAction(gameHero, heroStatesProperties));
	}

	@Override
	public void stateInputPressed(HeroInputKey key)
	{
		if (doubleJumpAllowed && key == HeroInputKey.JUMP)
		{
			gameHero.addAction(new DoubleJumpAction(gameHero, heroStatesProperties));
			doubleJumpAllowed = false;
		}
	}

	@Override
	public void isPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.LEFT)
		{
			gameHero.setForwardSide(Side.LEFT);
		}
		else if (key == HeroInputKey.RIGHT)
		{
			gameHero.setForwardSide(Side.RIGHT);
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

}
