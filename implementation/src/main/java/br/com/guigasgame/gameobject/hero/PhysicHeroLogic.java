package br.com.guigasgame.gameobject.hero;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class PhysicHeroLogic {

	Body body;

	public Body getBody() {
		return body;
	}

	public void applyImpulse(Vec2 impulse) {
		body.applyLinearImpulse(impulse, body.getWorldCenter());
	}

	public void applyForce(Vec2 force) {
		body.applyForce(force, body.getWorldCenter());
	}

	public void checkSpeedLimits(Vec2 maxSpeed) {
		Vec2 speed = body.getLinearVelocity();
		Vec2 speedToSubtract = new Vec2();
		float xDiff = Math.abs(speed.x) - Math.abs(maxSpeed.x); 
		float yDiff = Math.abs(speed.y) - Math.abs(maxSpeed.y); 
		if (xDiff > 0) {
			speedToSubtract.x = xDiff;
		}
		if (yDiff > 0) {
			speedToSubtract.y = xDiff;
		}
		
		//Se houve alteração
		if (speed.length() > 0)
		{
			body.setLinearVelocity(speed.sub(speedToSubtract));
		}
		
	}
	
	public Vec2 getBodyPosition()
	{
		return body.getPosition();
	}
	
	public float getAngleRadians()
	{
		return body.getAngle();
	}
}
