package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.action.ShootRopeAction;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.projectile.rope.NinjaHookProjectile;
import br.com.guigasgame.gameobject.projectile.rope.NinjaRope;


public class NinjaRopeShootingState extends HeroState
{

	private NinjaHookProjectile ninjaHook;

	public NinjaRopeShootingState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_ROPE_SHOOTING);
	}
	
	@Override
	protected void stateOnEnter()
	{
		ninjaHook = gameHero.createNinjaRopeProjectile(pointingDirection());
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
			NinjaRope ninjaRope = ninjaHook.getNinjaRope();
			setState(new NinjaRopeSwingingState(gameHero, ninjaRope));
			gameHero.addChild(ninjaRope);
			ninjaHook.markToDestroy();
		}
		else if (ninjaHook.isMarkedToDestroy())
		{
			setState(new JumpingHeroState(gameHero));
		}
	}
	
}
