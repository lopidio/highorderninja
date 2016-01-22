package br.com.guigasgame.gameobject.hero;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

import br.com.guigasgame.gamehero.HeroSensorsController.FixtureSensorID;


public class GameHeroFixturesCreator
{

	private Map<FixtureSensorID, FixtureDef> fixtures;

	public GameHeroFixturesCreator()
	{
		fixtures = new HashMap<>();
		// for (FixtureSensorID id : FixtureSensorID.values()) {
		// fixtures.put(id, new FixtureDef());
		// }
		createUpperFixture();
		// createFeetFixture();
		// createLegsFixture();
	}

	private void createUpperFixture()
	{
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.3f, 0.4f, new Vec2(0, -1.4f), 0);

		FixtureDef def = new FixtureDef();
		def.density = 3;
		def.friction = 0.7f;
		def.restitution = 0.3f;
		def.shape = shape;
		fixtures.put(FixtureSensorID.UPPER, def);
	}

	private void createLegsFixture()
	{
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.4f, 0.5f, new Vec2(0, -0.5f), 0);
		fixtures.get(FixtureSensorID.LEGS).shape = shape;
	}

	private void createFeetFixture()
	{
		CircleShape feetShape = new CircleShape();
		feetShape.setRadius(0.5f);
		fixtures.get(FixtureSensorID.FEET).restitution = 0.3f;
		fixtures.get(FixtureSensorID.FEET).shape = feetShape;
	}

	private void addCOisa()
	{
		PolygonShape bottomLeftShape = new PolygonShape();
		bottomLeftShape
				.setAsBox(0.01f, 0.5f, new Vec2(-0.5f - 0.01f, -0.5f), 0);
		FixtureDef fixtureDef4 = new FixtureDef();
		fixtureDef4.isSensor = true;
		fixtureDef4.shape = bottomLeftShape;

	}

	public Map<FixtureSensorID, FixtureDef> getFixturesMap()
	{
		return fixtures;
	}
}
