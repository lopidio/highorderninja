package br.com.guigasgame.gameobject.hero;

import java.util.Map;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;


class HeroPhysics
{

	GameHero gameHero;
	HeroSensorsController sensorsController;
	Body body;

	public HeroPhysics(GameHero gameHero)
	{
		this.gameHero = gameHero;
		sensorsController = new HeroSensorsController();
	}
	
	public void loadAndAttachFixturesToBody(Body body)
	{
		this.body = body;
		HeroFixtures gameHeroFixtures = HeroFixtures.loadFromFile(FilenameConstants.getHeroFixturesFilename());
		Map<FixtureSensorID, FixtureDef> fixtureDefMap = gameHeroFixtures
				.getFixturesMap();
		for( Map.Entry<FixtureSensorID, FixtureDef> entry : fixtureDefMap
				.entrySet() )
		{
			Fixture fixture = body.createFixture(entry.getValue());
			sensorsController.attachController(entry.getKey(), fixture);
		}
	}

	public void applyImpulse(Vec2 impulse)
	{
		body.applyLinearImpulse(impulse.mul(body.getMass()),
				body.getWorldCenter());
	}

	public void applyForce(Vec2 force)
	{
		body.applyForce(force, body.getWorldCenter());
	}

	public void checkSpeedLimits(Vec2 maxSpeed)
	{
		Vec2 speed = body.getLinearVelocity();
		Vec2 speedToSubtract = new Vec2();
		float xDiff = Math.abs(speed.x) - Math.abs(maxSpeed.x);
		float yDiff = Math.abs(speed.y) - Math.abs(maxSpeed.y);
		if (xDiff > 0)
		{
			speedToSubtract.x = speed.x < 0 ? xDiff : -xDiff;
		}
		if (yDiff > 0)
		{
			speedToSubtract.y = speed.y < 0 ? yDiff : -yDiff;
		}

		// Se houve alteração
		if (speedToSubtract.length() > 0)
		{
			body.setLinearVelocity(speed.add(speedToSubtract));
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

	public BodyDef getBodyDef(Vec2 position)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.fixedRotation = true;
		bodyDef.position = position;
		bodyDef.type = BodyType.DYNAMIC;

		return bodyDef;
	}

	public boolean isTouchingGround()
	{
		return sensorsController.getController(FixtureSensorID.BOTTOM_SENSOR)
				.isTouching();
	}

	public boolean isBodyFallingDown()
	{
		return body.getLinearVelocity().y < 0;
	}

	public boolean isTouchingWallAhead()
	{
		switch (gameHero.getForwardSide())
		{
		case LEFT:
			return sensorsController.getController(FixtureSensorID.LEFT_BOTTOM_SENSOR)
					.isTouching()
					|| sensorsController
							.getController(FixtureSensorID.LEFT_TOP_SENSOR)
							.isTouching();
		case RIGHT:
			return sensorsController
					.getController(FixtureSensorID.RIGHT_BOTTOM_SENSOR).isTouching()
					|| sensorsController.getController(
							FixtureSensorID.RIGHT_TOP_SENSOR).isTouching();
		default:
			return false;
		}
	}

}
