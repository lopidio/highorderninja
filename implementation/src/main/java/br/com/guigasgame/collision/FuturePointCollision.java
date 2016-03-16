package br.com.guigasgame.collision;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class FuturePointCollision 
{
	private Body me;
	private Vec2 speed;

	public FuturePointCollision(Body me)
	{
		this.me = me;
		this.speed = me.getLinearVelocity();
	}
	
	public Vec2 futurePoint(Body other)
	{
	//http://twobitcoder.blogspot.com.br/2010/04/circle-collision-detection.html
			
//			return response.point;
//			float passTime = 0;
//			 So the meeting position will be the starting position of the reciever, plus the distance he will travel, which is actualy the velocity vector times how long it will take the ball to reach him (that is passTime).
//			Vec2 meetingPosition = projectilePosition.add(projectileSpeed.mul(passTime));

//			 Now to calculate the direction of the pass:
//			 This is the vector that points from the ball's current location to where they will meet.
//			final Vec2 direction = meetingPosition.sub(response.point);

//			 Last, to calculate the magnitude of the pass velocity:
//			  float ballVelocityMagnitude = direction.length();
//			 Since the distance = velocity * time, we get that velocity = distance / time

//			 Last, give the ball the velocity it needs to get to the destination:

//			 projectileSpeed = direction.normalize() * ballVelocityMagnitude;
//			 Multiply the normalized (size 1) version of the ball's direction with the magnitude you want it to be.
			  return null;
	}
}
