package br.com.guigasgame.gameobject.hero.playable;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;


class HeroFixturesCreator
{

	@XmlElement
	private Map<FixtureSensorID, FixtureDef> fixtures;

	/**
	 * DO NOT USE
	 */
	public HeroFixturesCreator()
	{
		fixtures = new HashMap<>();

		createHeadFixture();
		createBodyFixture();
		createFeetFixture();
		createBottomSensor();
		createBottomLeftSensor();
		createBottomRightSensor();
		createTopLeftSensor();
		createTopRightSensor();
	}

	// static HeroFixtures loadFromFile(String filename)
	// {
	// try
	// {
	// JAXBContext jaxbContext = JAXBContext
	// .newInstance(HeroFixtures.class);
	// Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	// HeroFixtures gameHeroFixture = ((HeroFixtures) jaxbUnmarshaller
	// .unmarshal(new File(filename)));
	// return gameHeroFixture;
	// }
	// catch (JAXBException e)
	// {
	// e.printStackTrace();
	// }
	// return null;
	// }

	public Map<FixtureSensorID, FixtureDef> getFixturesMap()
	{
		return fixtures;
	}

	private void createHeadFixture()
	{
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.25f, 0.5f, new Vec2(0, -1.5f), 0);

		FixtureDef def = new FixtureDef();
		def.shape = shape;
		def.density = 2.f;
		fixtures.put(FixtureSensorID.HEAD, def);
	}

	private void createBodyFixture()
	{
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.25f, 0.6f, new Vec2(0, -0.5f), 0);

		FixtureDef def = new FixtureDef();
		def.shape = shape;
		def.density = 2.f;
		fixtures.put(FixtureSensorID.BODY, def);
	}

	private void createFeetFixture()
	{
		CircleShape feetShape = new CircleShape();
		// feetShape.setRadius(0.35f);
		feetShape.setRadius(0.3f);

		FixtureDef def = new FixtureDef();
		def.restitution = 0.0f;
		def.friction = 0.95f;

		def.shape = feetShape;
		def.density = 2.f;
		fixtures.put(FixtureSensorID.FEET, def);
	}

	private void createBottomSensor()
	{
		PolygonShape bottomShape = new PolygonShape();
		bottomShape.setAsBox(0.2f, 0.4f, new Vec2(0, 0f), 0);

		FixtureDef def = new FixtureDef();
		def.isSensor = true;
		def.shape = bottomShape;
		def.density = 0;
		fixtures.put(FixtureSensorID.BOTTOM_SENSOR, def);
	}

	private void createBottomLeftSensor()
	{
		PolygonShape bottomLeftShape = new PolygonShape();
		bottomLeftShape.setAsBox(0.2f, 0.3f, new Vec2(-0.5f - 0.1f, -0.5f), 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.isSensor = true;
		fixtureDef.shape = bottomLeftShape;
		fixtureDef.density = 0;
		fixtures.put(FixtureSensorID.LEFT_BOTTOM_SENSOR, fixtureDef);
	}

	private void createBottomRightSensor()
	{
		PolygonShape rightLeftShape = new PolygonShape();
		rightLeftShape.setAsBox(0.2f, 0.3f, new Vec2(0.5f + 0.1f, -0.5f), 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.isSensor = true;
		fixtureDef.shape = rightLeftShape;
		fixtureDef.density = 0;
		fixtures.put(FixtureSensorID.RIGHT_BOTTOM_SENSOR, fixtureDef);
	}

	private void createTopLeftSensor()
	{
		PolygonShape topLeftShape = new PolygonShape();
		topLeftShape.setAsBox(0.2f, 0.3f, new Vec2(-0.5f - 0.1f, -1.7f), 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.isSensor = true;
		fixtureDef.shape = topLeftShape;
		fixtureDef.density = 0;
		fixtures.put(FixtureSensorID.LEFT_TOP_SENSOR, fixtureDef);
	}

	private void createTopRightSensor()
	{
		PolygonShape topRightShape = new PolygonShape();
		topRightShape.setAsBox(0.2f, 0.3f, new Vec2(0.5f + 0.1f, -1.7f), 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.isSensor = true;
		fixtureDef.shape = topRightShape;
		fixtureDef.density = 0;
		fixtures.put(FixtureSensorID.RIGHT_TOP_SENSOR, fixtureDef);
	}

	// public static void main(String[] args)
	// {
	//
	// try
	// {
	// HeroFixtures gameHeroFixture = new HeroFixtures();
	// JAXBContext context = JAXBContext.newInstance(HeroFixtures.class);
	// Marshaller m = context.createMarshaller(); // for pretty-print XML
	// // in JAXB
	// m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	//
	// // Write to System.out for debugging
	// m.marshal(gameHeroFixture, System.out);
	//
	// // Write to File
	// m.marshal(gameHeroFixture, new
	// File(FilenameConstants.getHeroFixturesFilename()));
	// }
	// catch (JAXBException e)
	// {
	// e.printStackTrace();
	// }
	//
	//
	//
	// loadFromFile(FilenameConstants.getHeroFixturesFilename());
	// }
	//
}
