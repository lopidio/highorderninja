package br.com.guigasgame.gameobject.projectile.aimer;

import org.jbox2d.common.Vec2;

public class RaycastAimer
{
		public Vec2 direction;
		public int angleVariation;
		public RaycastAimer(Vec2 direction, int angleVariation) 
		{
			this.direction = direction;
			this.angleVariation = angleVariation;
		}
		
		public boolean isWorseThan(RaycastAimer other)
		{
			if (angleVariation != other.angleVariation)
				return (angleVariation > other.angleVariation);
			return direction.lengthSquared() > direction.lengthSquared();
		}

		public boolean isLongerThan(float newDistance) 
		{
			return 	( direction.length() > newDistance);
		}
		
		public boolean isValid()
		{
			return direction!= null;
		}
}
