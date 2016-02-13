package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.GameHero;


public class NinjaRope extends GameObject
{

	private final float distortFactor = 10.f;

	private RopeHook ropeHook;
	private boolean markToEnshort;
	private boolean markToEnlarge;

	NinjaRope(Vec2 position, GameHero gameHero)
	{
		ropeHook = new RopeHook(position, gameHero);
		collidable = ropeHook;
		markToEnlarge = false;
		markToEnshort = false;
	}

	@Override
	public void update(float deltaTime)
	{
		if (markToEnlarge)
		{
			ropeHook.enlarge(deltaTime * distortFactor);
		}
		else if (markToEnshort)
		{
			ropeHook.enshort(deltaTime * distortFactor);
		}

		markToEnlarge = false;
		markToEnshort = false;
	}

	public void enlarge()
	{
		markToEnshort = false;
		markToEnlarge = true;
	}

	public void enshort()
	{
		markToEnlarge = false;
		markToEnshort = true;
	}

	@Override
	public void onDestroy()
	{
		ropeHook.removeJoint();
	}
}
