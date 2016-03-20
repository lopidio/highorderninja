package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.action.DisableInvincibilityAction;
import br.com.guigasgame.gameobject.hero.action.EnableInvincibilityAction;
import br.com.guigasgame.gameobject.hero.action.MoveHeroAction;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.side.Side;


class SlidingHeroState extends HeroState
{

	private float secondsRemaining;

	protected SlidingHeroState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_SLIDING);
		Float duration = heroStatesProperties.property.get("duration");
		secondsRemaining = duration != null? duration.floatValue(): 0.5f;
	}

	@Override
	public void stateUpdate(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		gameHero.addAction(new MoveHeroAction(gameHero.getForwardSide(), heroStatesProperties));
		if (secondsRemaining <= 0)
		{
			if (gameHero.getCollidableHero().isMoving())
			{
				setState(new RunningHeroState(gameHero));
			}
			else
			{
				setState(new StandingHeroState(gameHero));
			}
		}
		else if (!gameHero.isTouchingGround())
		{
			if (gameHero.getCollidableHero().isFallingDown())
				setState(new FallingHeroState(gameHero));
			if (gameHero.getCollidableHero().isAscending())
				setState(new JumpingHeroState(gameHero));
		}
	}
	
	@Override
	protected void stateOnEnter()
	{
		gameHero.addAction(new EnableInvincibilityAction());
	}
	
	@Override
	protected void stateOnQuit()
	{
		gameHero.addAction(new DisableInvincibilityAction());
	}
	
	@Override
	protected void move(Side side)
	{
		//do nothing
	}
	
	@Override
	public void stateInputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			setState(new JumpingHeroState(gameHero));
		}
	}
	
}
