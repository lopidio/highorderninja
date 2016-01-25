package br.com.guigasgame.gameobject.hero;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import br.com.guigasgame.collision.CollidersFilters;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.math.Vector2;


class HeroPhysics
{

	private Map<FixtureSensorID, Fixture> fixtureMap;
	private GameHero gameHero;
	private HeroSensorsController sensorsController;
	private Body body;

	public HeroPhysics(GameHero gameHero)
	{
		this.gameHero = gameHero;
		sensorsController = new HeroSensorsController();
	}

	public void loadAndAttachFixturesToBody(Body body)
	{
		this.body = body;
		// HeroFixtures gameHeroFixtures = HeroFixtures.loadFromFile(FilenameConstants.getHeroFixturesFilename());
		HeroFixturesCreator gameHeroFixtures = new HeroFixturesCreator();
		fixtureMap = new HashMap<>();
		Map<FixtureSensorID, FixtureDef> fixtureDefMap = gameHeroFixtures.getFixturesMap();
		for( Map.Entry<FixtureSensorID, FixtureDef> entry : fixtureDefMap.entrySet() )
		{
			FixtureDef def = entry.getValue();
			
			def.filter.categoryBits = CollidersFilters.CATEGORY_PLAYER;
			def.filter.maskBits = CollidersFilters.MASK_PLAYER;
			
			Fixture fixture = body.createFixture(def);
			fixtureMap.put(entry.getKey(), fixture);
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

	public void checkSpeedLimits(Vector2 vector2)
	{
		Vec2 speed = body.getLinearVelocity();
		Vec2 speedToSubtract = new Vec2();
		float xDiff = Math.abs(speed.x) - Math.abs(vector2.x);
		float yDiff = Math.abs(speed.y) - Math.abs(vector2.y);
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

	public BodyDef editBodyDef(BodyDef bodyDef)
	{
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.DYNAMIC;

		return bodyDef;
	}

	public boolean isTouchingGround()
	{
		return sensorsController.getController(FixtureSensorID.BOTTOM_SENSOR)
				.isTouching();
	}

	public boolean isFallingDown()
	{
		return body.getLinearVelocity().y > 0;
	}

	public boolean isAscending()
	{
		return body.getLinearVelocity().y < 0;
	}

	public boolean isTouchingWallAhead()
	{
		switch (gameHero.getForwardSide())
		{
		case LEFT:
			return sensorsController
					.getController(FixtureSensorID.LEFT_BOTTOM_SENSOR)
					.isTouching()
					&& sensorsController
							.getController(FixtureSensorID.LEFT_TOP_SENSOR)
							.isTouching();
		case RIGHT:
			return sensorsController
					.getController(FixtureSensorID.RIGHT_BOTTOM_SENSOR)
					.isTouching()
					&& sensorsController
							.getController(FixtureSensorID.RIGHT_TOP_SENSOR)
							.isTouching();
		default:
			return false;
		}
	}

	public void disableCollision(FixtureSensorID sensorID)
	{
		Filter filter = fixtureMap.get(sensorID).getFilterData();
		filter.maskBits = 0;// CollidersFilters.MASK_PLAYER;
		fixtureMap.get(sensorID).setFilterData(filter);
	}

	public void enableCollision(FixtureSensorID sensorID)
	{
		fixtureMap.get(sensorID).setSensor(false);
	}

	public Vec2 getBodyLinearVelocity()
	{
		return body.getLinearVelocity();
	}

}