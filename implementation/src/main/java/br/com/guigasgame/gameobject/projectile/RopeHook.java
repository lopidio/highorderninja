package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import br.com.guigasgame.collision.Collidable;


public class RopeHook extends Collidable
{
	public RopeHook(Vec2 position)
	{
		super(position);
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.STATIC;
	}

}
