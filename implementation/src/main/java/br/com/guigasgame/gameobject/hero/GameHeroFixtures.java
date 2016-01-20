package br.com.guigasgame.gameobject.hero;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;

public class GameHeroFixtures 
{
	enum FixtureID
	{
		HEAD,
		BODY,
		LEGS,
		FEET,
		LEFT_TOP, 
		RIGHT_TOP, 
		LEFT_BOTTOM, 
		RIGHT_BOTTOM, 
		BOTTOM
	}

	private Map<FixtureID, FixtureDef> fixtures;
	
	public GameHeroFixtures() 
	{
		fixtures = new HashMap<>();
		for (FixtureID id : FixtureID.values()) {
			fixtures.put(id, new FixtureDef());
		}
		createBodyFixture();
	}
	
	public void addFixturesToBody(Body body)
	{
		for (Map.Entry<FixtureID, FixtureDef> fixtures: fixtures.entrySet()) {
			body.createFixture(fixtures.getValue());
		}
	}
	
	private void createBodyFixture()
	{
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.3f, 0.4f, new Vec2(0, -1.4f), 0);
		fixtures.get(FixtureID.HEAD).shape = shape;
	}

	private void createLegsFixture()
	{
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.4f, 0.5f, new Vec2(0, -0.5f), 0);
		fixtures.get(FixtureID.LEGS).shape = shape;
	}
	
	private void createFeetFixture()
	{
		CircleShape feetShape = new CircleShape();
		feetShape.setRadius(0.5f);
		fixtures.get(FixtureID.FEET).restitution = 0.3f;
		fixtures.get(FixtureID.FEET).shape = feetShape;
	}
	
	private void addCOisa()
	{
		PolygonShape bottomLeftShape = new PolygonShape();
		bottomLeftShape.setAsBox(0.01f, 0.5f, new Vec2(-0.5f - 0.01f, -0.5f), 0);
		FixtureDef fixtureDef4 = new FixtureDef();
		fixtureDef4.isSensor = true;
		fixtureDef4.shape = bottomLeftShape;

	}
}
