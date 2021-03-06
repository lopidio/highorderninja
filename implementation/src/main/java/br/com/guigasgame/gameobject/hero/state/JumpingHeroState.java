package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.action.JumpAction;
import br.com.guigasgame.gameobject.hero.action.JumpPressingHelp;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.side.Side;


class JumpingHeroState extends HeroState
{

	private boolean doubleJumpAllowed;

	protected JumpingHeroState(PlayableGameHero gameHero)
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
			if (isHeroInputMapPressed(HeroInputKey.LEFT) && gameHero.getCollidableHero().isGoingTo(Side.LEFT))
				setState(new AirSpinHeroState(gameHero, Side.LEFT));
			else if (isHeroInputMapPressed(HeroInputKey.RIGHT) && gameHero.getCollidableHero().isGoingTo(Side.RIGHT))
				setState(new AirSpinHeroState(gameHero, Side.RIGHT));
			else if (isHeroInputMapPressed(HeroInputKey.UP))
				setState(new StopMovementState(gameHero));
			else if (isHeroInputMapPressed(HeroInputKey.DOWN))
				setState(new DivingState(gameHero));
		}
	}
	
	@Override
	protected void stateDoubleTapInput(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.DOWN)
			setState(new DivingState(gameHero));
		else if (inputValue == HeroInputKey.UP)
			setState(new StopMovementState(gameHero));
	}

}
