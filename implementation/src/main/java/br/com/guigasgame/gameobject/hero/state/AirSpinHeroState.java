package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.action.SideImpulseAction;
import br.com.guigasgame.gameobject.hero.action.StopMovementAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;

public class AirSpinHeroState extends HeroState
{

	private Side side;
	private float secondsRemaining;
	private Float minHorizontalSpeed;

	public AirSpinHeroState(RoundGameHero gameHero, Side side)
	{
		super(gameHero, HeroStateIndex.HERO_AIR_SPIN);
		this.side = side;
//		setAnimationsColor(Color.BLUE);
		
		Float duration = heroStatesProperties.property.get("duration");
		minHorizontalSpeed = heroStatesProperties.property.get("minHorizontalSpeed");
		if (minHorizontalSpeed == null)
			minHorizontalSpeed = 10f;
		secondsRemaining = duration != null? duration.floatValue(): 0.5f;

	}
	
	@Override
	public boolean canExecute(RoundGameHero hero)
	{
		if (hero.isTouchingWallAhead())
			return true;
		if (Math.abs(hero.getCollidableHero().getBodyLinearVelocity().x) >= minHorizontalSpeed)
			return true;
		return false;
	}
	
	@Override
	protected void stateOnEnter()
	{
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
