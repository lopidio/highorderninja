package br.com.guigasgame.gameobject.projectile.rope;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public class NinjaRope extends GameObject
{
	private List<NinjaRopePiece> ropeBodies;
	private NinjaHookProjectile hookProjectile;

	public NinjaRope(NinjaHookProjectile ninjaHookProjectile, PlayableGameHero gameHero, float maxSize)
	{
		this.hookProjectile = ninjaHookProjectile;
		ropeBodies = new ArrayList<>();
		ropeBodies.add(new NinjaRopePiece(ninjaHookProjectile.getPosition(), gameHero.getCollidableHero().getBody(), gameHero.getHeroProperties().getColor(), maxSize));
		addChild(ropeTail());
	}
	
	@Override
	protected void onDestroy()
	{
		for( NinjaRopePiece link : ropeBodies )
		{
			link.markToDestroy();
		}
		hookProjectile.markToDestroy();
		System.out.println("Destroy rope");
	}
	
	
	public void update(float deltaTime)
	{
		verifyCutTheRope();
		hookProjectile.setAngle((float)(getHeadAngle() + Math.PI)); //Opposite to the rope's angle
		if (ropeTail().isMarkedToDivide())
		{
			NinjaRopePiece tail = ropeTail().divide();
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

	private void verifyCutTheRope()
	{
		for( NinjaRopePiece piece : ropeBodies )
		{
			if (piece.isCutTheRope())
				markToDestroy();
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
		return hookProjectile.getPosition();
	}

	private NinjaRopePiece ropeTail()
	{
		return ropeBodies.get(ropeBodies.size()-1);
	}
	
	private NinjaRopePiece ropeHead()
	{
		return ropeBodies.get(0);
	}
	
	public float getHeadAngle()
	{
		return ropeHead().getAngle();
	}
	
}
