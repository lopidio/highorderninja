package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.action.ShootRopeAction;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.projectile.rope.NinjaHookProjectile;


public class RopeShootingState extends HeroState
{

	private NinjaHookProjectile ninjaHook;

	public RopeShootingState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_ROPE_SHOOTING);

//		setAnimationsColor(Color.GREEN);
	}
	
	@Override
	protected void stateOnEnter()
	{
		ninjaHook = new NinjaHookProjectile(pointingDirection(), gameHero);
		gameHero.addAction(new ShootRopeAction(heroStatesProperties, ninjaHook));
	}
	
	@Override
	protected void stateOnQuit()
	{
		ninjaHook.markToDestroy();
	}
	
	protected void releaseRope()
	{
		ninjaHook.markToDestroy();
		setState(new StandingHeroState(gameHero));
	}
	
	@Override
	protected void stateInputReleased(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ROPE)
		{
			releaseRope();
		}
	}
	
	@Override
	protected void stateUpdate(float deltaTime)
	{
		if (ninjaHook.isHookAttached())
		{
			setState(new NinjaRopeSwingingState(gameHero, ninjaHook.getNinjaRope()));
			ninjaHook.markToDestroy();
		}
		else if (ninjaHook.isMarkedToDestroy())
		{
			setState(new FallingHeroState(gameHero));
		}
	}
	
}
