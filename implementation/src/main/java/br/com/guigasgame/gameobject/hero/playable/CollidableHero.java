package br.com.guigasgame.gameobject.hero.playable;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.math.Vector2;
import br.com.guigasgame.side.Side;


public class CollidableHero extends Collidable
{
	private Map<FixtureSensorID, Fixture> fixtureMap;
	private HeroSensorsController sensorsController;
	private CollidableFilter filter;
	private final PlayableGameHero playableHero;

	public CollidableHero(int playerId, Vec2 initialPosition, PlayableGameHero playableGameHero)
	{
		super(initialPosition);
		this.playableHero = playableGameHero;
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.DYNAMIC;
		
		sensorsController = new HeroSensorsController();
		filter = CollidableCategory.getPlayerFilter(playerId);
	}

	public void loadAndAttachFixturesToBody()
	{
		// HeroFixtures gameHeroFixtures = HeroFixtures.loadFromFile(FilenameConstants.getHeroFixturesFilename());
		HeroFixturesCreator gameHeroFixtures = new HeroFixturesCreator();
		fixtureMap = new HashMap<>();
		Map<FixtureSensorID, FixtureDef> fixtureDefMap = gameHeroFixtures.getFixturesMap();
		for( Map.Entry<FixtureSensorID, FixtureDef> entry : fixtureDefMap.entrySet() )
		{
			FixtureDef def = entry.getValue();
			if (def.isSensor)
			{
				def.filter = new CollidableFilterBox2dAdapter(filter.removeCollisionWithEveryThing().except(CollidableCategory.SCENERY)).toBox2dFilter();
			}
			else
			{
				def.filter = new CollidableFilterBox2dAdapter(filter).toBox2dFilter();
			}
			
			
			Fixture fixture = body.createFixture(def);
			fixtureMap.put(entry.getKey(), fixture);
			sensorsController.attachController(entry.getKey(), fixture);
		}
	}

	public void applyImpulse(Vec2 impulse)
	{
		body.applyLinearImpulse(impulse.mul(body.getMass()), body.getWorldCenter());
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
	
	public boolean isMoving()
	{
		return body.getLinearVelocity().length() > WorldConstants.MOVING_TOLERANCE;
	}	

	public Vec2 getBodyPosition()
	{
		return body.getPosition();
	}

	public float getAngleRadians()
	{
		return body.getAngle();
	}

	public boolean isTouchingGround()
	{
		return sensorsController.getController(FixtureSensorID.BOTTOM_SENSOR).isTouching();
	}

	public boolean isFallingDown()
	{
		return body.getLinearVelocity().y > WorldConstants.MOVING_TOLERANCE;
	}

	public boolean isAscending()
	{
		return body.getLinearVelocity().y < -WorldConstants.MOVING_TOLERANCE;
	}

	public boolean isTouchingWallAhead(Side side)
	{
		switch (side)
		{
		case LEFT:
			return sensorsController.getController(FixtureSensorID.LEFT_BOTTOM_SENSOR).isTouching()
					&& sensorsController.getController(FixtureSensorID.LEFT_TOP_SENSOR).isTouching();
		case RIGHT:
			return sensorsController.getController(FixtureSensorID.RIGHT_BOTTOM_SENSOR).isTouching()
					&& sensorsController.getController(FixtureSensorID.RIGHT_TOP_SENSOR).isTouching();
		default:
			return false;
		}
	}

	public void disableCollision(FixtureSensorID sensorID)
	{
		Filter newFilter = new Filter();
		newFilter.categoryBits = filter.getCategory().value;
		newFilter.maskBits = 0;
		fixtureMap.get(sensorID).setFilterData(newFilter);
	}

	public void enableCollision(FixtureSensorID sensorID)
	{
		fixtureMap.get(sensorID).setFilterData(new CollidableFilterBox2dAdapter(filter).toBox2dFilter());
	}

	public Vec2 getBodyLinearVelocity()
	{
		return body.getLinearVelocity();
	}

	public void stopMovement()
	{
		body.setLinearVelocity(new Vec2());
	}

	public boolean isGoingTo(Side side)
	{
		if (side == Side.LEFT)
			return body.getLinearVelocity().x < -WorldConstants.MOVING_TOLERANCE;
		else if (side == Side.RIGHT)
			return body.getLinearVelocity().x > WorldConstants.MOVING_TOLERANCE;
		return false;
	}
	
	public Side movingToSide()
	{
		if (body.getLinearVelocity().x < -WorldConstants.MOVING_TOLERANCE)
			return Side.LEFT;
		if (body.getLinearVelocity().x > WorldConstants.MOVING_TOLERANCE)
			return Side.RIGHT;
		return Side.UNKNOWN;
	}

	public void putRopeSwingingProperties()
	{
		for( Fixture fixture : fixtureMap.values() )
		{
			fixture.setFriction(1f);
			fixture.setRestitution(0);
		}

	}

	public void takeOutRopeSwingingProperties()
	{
		for( Fixture fixture : fixtureMap.values() )
		{
			fixture.setFriction(0f);
			fixture.setRestitution(0.5f);
		}
	}

	public PlayableGameHero getPlayableHero()
	{
		return playableHero;
	}

	public void die()
	{
		final Filter deadFilter = new CollidableFilterBox2dAdapter(CollidableCategory.DEAD_HERO.getFilter()).toBox2dFilter();
		for( Fixture fixture : fixtureMap.values() )
		{
			fixture.setFilterData(deadFilter);
		}
	}

	public Vec2 getGroundNormal()
	{
		Vec2 normal = sensorsController.getController(FixtureSensorID.FEET).contactsAverageNormal();
		if (normal != null)
			return normal;
		//Return 0, -1 when has no contact
		return new Vec2(0, -1);
	}

}
