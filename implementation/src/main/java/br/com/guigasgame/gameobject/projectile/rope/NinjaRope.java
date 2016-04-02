package br.com.guigasgame.gameobject.projectile.rope;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public class NinjaRope extends GameObject
{
	private final Vec2 hookPosition;
	private List<NinjaRopeLink> ropeBodies;

	public NinjaRope(Vec2 attachPoint, PlayableGameHero gameHero, float maxSize)
	{
		this.hookPosition = attachPoint;
		ropeBodies = new ArrayList<>();
		ropeBodies.add(new NinjaRopeLink(attachPoint, gameHero.getCollidableHero().getBody(), maxSize));
		addChild(ropeTail());
	}
	
	@Override
	protected void onDestroy()
	{
		for( NinjaRopeLink link : ropeBodies )
		{
			link.markToDestroy();
		}
		System.out.println("Destroy rope");
	}
	
	private NinjaRopeLink ropeTail()
	{
		return ropeBodies.get(ropeBodies.size()-1);
	}
	
	public void update(float deltaTime)
	{
		if (!ropeTail().isAttached())
			return;
		if (ropeTail().isMarkedToDivide())
		{
			NinjaRopeLink tail = ropeTail().divide();
			ropeBodies.add(tail);
			return;
		}
		else if (ropeBodies.size() > 1 && ropeTail().isMarkedToReunite())
		{
			ropeTail().markToDestroy();
			ropeBodies.remove(ropeTail());
			ropeTail().wakeUp();
		}
	}

	public void shorten()
	{
		ropeTail().shorten();
	}
	
	public void increase()
	{
		ropeTail().increase();
	}
	
	public Vec2 getHookPosition()
	{
		return hookPosition;
	}
	
}
