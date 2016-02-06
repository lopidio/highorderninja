package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import br.com.guigasgame.collision.Collidable;


public class ShurikenCollidable extends Collidable
{

	public ShurikenCollidable(Vec2 position)
	{
		super(position);
		
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.bullet = true;
		
	}

}
