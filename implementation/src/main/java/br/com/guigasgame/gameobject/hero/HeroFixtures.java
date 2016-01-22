package br.com.guigasgame.gameobject.hero;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;


class HeroFixtures
{

	@XmlElement
	private Map<FixtureSensorID, FixtureDef> fixtures;

	/**
	 * DO NOT USE
	 */
	public HeroFixtures()
	{
		fixtures = new HashMap<>();

		createUpperFixture();
		createLegsFixture();
		createFeetFixture();
		createBottomLeftSensor();
	}

	static HeroFixtures loadFromFile(String filename)
	{
		try
		{
			JAXBContext jaxbContext = JAXBContext
					.newInstance(HeroFixtures.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			HeroFixtures gameHeroFixture = ((HeroFixtures) jaxbUnmarshaller
					.unmarshal(new File(filename)));
			return gameHeroFixture;
		}
		catch (JAXBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Map<FixtureSensorID, FixtureDef> getFixturesMap()
	{
		return fixtures;
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

		def.isSensor = true;

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

	private void createBottomLeftSensor()
	{
		PolygonShape bottomLeftShape = new PolygonShape();
		bottomLeftShape.setAsBox(0.01f, 0.5f, new Vec2(-0.5f - 0.01f, -0.5f), 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.isSensor = true;
		fixtureDef.shape = bottomLeftShape;
		fixtures.put(FixtureSensorID.LEFT_BOTTOM_SENSOR, fixtureDef);
	}

	public static void main(String[] args)
	{

//		try
//		{
//			HeroFixtures gameHeroFixture = new HeroFixtures();
//			JAXBContext context = JAXBContext.newInstance(HeroFixtures.class);
//			Marshaller m = context.createMarshaller(); // for pretty-print XML
//														// in JAXB
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//
//			// Write to System.out for debugging
//			m.marshal(gameHeroFixture, System.out);
//
//			// Write to File
//			m.marshal(gameHeroFixture, new File(FilenameConstants.getHeroFixturesFilename()));
//		}
//		catch (JAXBException e)
//		{
//			e.printStackTrace();
//		}
		

		
		loadFromFile(FilenameConstants.getHeroFixturesFilename());
	}

}
