package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.action.DisableInvincibilityAction;
import br.com.guigasgame.gameobject.hero.action.EnableInvincibilityAction;
import br.com.guigasgame.gameobject.hero.action.SideImpulseAction;
import br.com.guigasgame.gameobject.hero.action.StopMovementAction;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.side.Side;

public class AirSpinHeroState extends HeroState
{

	private Side side;
	private float secondsRemaining;
	private Float minHorizontalSpeed;

	public AirSpinHeroState(PlayableGameHero gameHero, Side side)
	{
		super(gameHero, HeroStateIndex.HERO_AIR_SPIN);
		this.side = side;
		
		Float duration = heroStatesProperties.property.get("duration");
		minHorizontalSpeed = heroStatesProperties.property.get("minHorizontalSpeed");
		if (minHorizontalSpeed == null)
			minHorizontalSpeed = 10f;
		secondsRemaining = duration != null? duration.floatValue(): 0.5f;
	}
	
	@Override
	public boolean canExecute(PlayableGameHero hero)
	{
		if (hero.isTouchingWallAhead())
			return true;
		if (Math.abs(hero.getCollidableHero().getBodyLinearVelocity().x) >= minHorizontalSpeed)
			return true;
		return false;
	}
	
	@Override
	protected void stateOnQuit()
	{
		gameHero.addAction(new DisableInvincibilityAction());
	}

	
	@Override
	protected void stateOnEnter()
	{
		gameHero.addAction(new EnableInvincibilityAction());
		gameHero.addAction(new SideImpulseAction(heroStatesProperties, side).addPrevAction(new StopMovementAction(heroStatesProperties)));
	}
	
	@Override
	public void stateUpdate(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		if (secondsRemaining <= 0)
		{
			setState(new FallingHeroState(gameHero));
		}
	}
	
	@Override
	protected void stateInputPressed(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ACTION)
		{
			setState(new StopMovementState(gameHero));
		}
	}
}
