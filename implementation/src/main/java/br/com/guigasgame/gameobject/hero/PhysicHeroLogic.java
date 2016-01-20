package br.com.guigasgame.gameobject.hero;

import java.util.Map;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import br.com.guigasgame.gamehero.HeroSensorsController;
import br.com.guigasgame.gamehero.HeroSensorsController.FixtureSensorID;

public class PhysicHeroLogic {

	GameHero gameHero;
	HeroSensorsController sensorsController;
	Body body;

	public PhysicHeroLogic(GameHero gameHero, Vec2 position) {
		this.gameHero = gameHero;
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

	public BodyDef getBodyDef(Vec2 position) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.fixedRotation = true;
		bodyDef.position = position;
		return bodyDef;
	}

	public void attachFixturesToBody(Body body) 
	{
		this.body = body;
		GameHeroFixturesCreator gameHeroFixturesCreator = new GameHeroFixturesCreator();
		Map<FixtureSensorID, FixtureDef> fixtureDefMap = gameHeroFixturesCreator.getFixturesDef();
		for (Map.Entry<FixtureSensorID, FixtureDef> fixturesDef : fixtureDefMap.entrySet()) {
			Fixture fixture = body.createFixture(fixturesDef.getValue());
			sensorsController.attachControllers(fixturesDef.getKey(), fixture);
		}
	}
	
	public boolean isTouchingGround() {
		return sensorsController.getController(FixtureSensorID.BOTTOM).isTouching();
	}
	
	public boolean isBodyFallingDown()
	{
		return body.getLinearVelocity().y < 0;
	}

	public boolean isTouchingWallAhead() {
		switch (gameHero.getForwardSide()) {
		case LEFT:
			return sensorsController.getController(FixtureSensorID.LEFT_BOTTOM).isTouching()
					|| sensorsController.getController(FixtureSensorID.LEFT_TOP).isTouching();
		case RIGHT:
			return sensorsController.getController(FixtureSensorID.RIGHT_BOTTOM).isTouching()
					|| sensorsController.getController(FixtureSensorID.RIGHT_TOP).isTouching();
		default:
			return false;
		}
	}	

}
