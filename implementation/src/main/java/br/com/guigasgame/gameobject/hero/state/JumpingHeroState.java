package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.JumpAction;
import br.com.guigasgame.gameobject.hero.action.JumpPressingHelp;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class JumpingHeroState extends HeroState
{

	private boolean doubleJumpAllowed;

	protected JumpingHeroState(GameHero gameHero)
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
		
	}
	
	@Override
	protected void stateInputIsPressing(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.JUMP)
		{
			gameHero.addAction(new JumpPressingHelp(heroStatesProperties));
		}
	}

	@Override
	protected void stateInputPressed(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ACTION)
		{
			setState(new DivingState(gameHero));
		}
	}
	
}
