package br.com.guigasgame.gameobject.hero;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;


class GameHeroFixturesCreator
{

	private Map<FixtureSensorID, FixtureDef> fixtures;

	public GameHeroFixturesCreator()
	{
		fixtures = new HashMap<>();

		createUpperFixture();
		createLegsFixture();
		createFeetFixture();
	}

	private void createUpperFixture()
	{
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.3f, 0.4f, new Vec2(0, -1.4f), 0);

		FixtureDef def = new FixtureDef();
		def.shape = shape;
		fixtures.put(FixtureSensorID.UPPER, def);
	}

	private void createLegsFixture()
	{
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.4f, 0.5f, new Vec2(0, -0.5f), 0);

		FixtureDef def = new FixtureDef();
		def.shape = shape;
		fixtures.put(FixtureSensorID.LEGS, def);
	}

	private void createFeetFixture()
	{
		CircleShape feetShape = new CircleShape();
		feetShape.setRadius(0.5f);

		FixtureDef def = new FixtureDef();
		def.restitution = 0.3f;

		def.shape = feetShape;
		fixtures.put(FixtureSensorID.FEET, def);
	}

//	private void addCOisa()
//	{
//		PolygonShape bottomLeftShape = new PolygonShape();
//		bottomLeftShape
//				.setAsBox(0.01f, 0.5f, new Vec2(-0.5f - 0.01f, -0.5f), 0);
//		FixtureDef fixtureDef4 = new FixtureDef();
//		fixtureDef4.isSensor = true;
//		fixtureDef4.shape = bottomLeftShape;
//
//	}

	public Map<FixtureSensorID, FixtureDef> getFixturesMap()
	{
		return fixtures;
	}
}
