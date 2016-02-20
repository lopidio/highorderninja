package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.GameHero;


public class NinjaRope extends GameObject
{

	private final float distortFactor = 10.f;

	private RopeHook ropeHook;
	private boolean markToShorten;
	private boolean markToIncrease;

	NinjaRope(Vec2 position, GameHero gameHero, ProjectileProperties properties)
	{
		ropeHook = new RopeHook(position, gameHero, properties);
		collidable = ropeHook;
		markToIncrease = false;
		markToShorten = false;
	}

	@Override
	public void update(float deltaTime)
	{
		if (markToIncrease)
		{
			ropeHook.enlarge(deltaTime * distortFactor);
		}
		else if (markToShorten)
		{
			ropeHook.enshort(deltaTime * distortFactor);
		}

		markToIncrease = false;
		markToShorten = false;
	}

	public void increase()
	{
		markToShorten = false;
		markToIncrease = true;
	}

	public void shorten()
	{
		markToIncrease = false;
		markToShorten = true;
	}

	@Override
	public void onDestroy()
	{
		ropeHook.removeJoint();
	}
}
