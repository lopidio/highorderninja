package br.com.guigasgame.gamehero;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import br.com.guigasgame.gameobject.hero.GameHero;

public class HeroSensorsController
{
	public enum SensorsID {
		LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM, BOTTOM
	}

	private GameHero gameHero;
	Map<SensorsID, SensorController> sensorsControllerMap;
	
	public HeroSensorsController(GameHero gameHero) {
		super();
		this.gameHero = gameHero;
		sensorsControllerMap = new HashMap<HeroSensorsController.SensorsID, SensorController>();
		sensorsControllerMap.put(SensorsID.BOTTOM, new SensorController());
		addSensorsToBody(null);
	}
	
	private void addSensorsToBody(Body body)
	{
		PolygonShape sensorShape = new PolygonShape();
		sensorShape.setAsBox(1, 1);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.isSensor = true;
		fixtureDef.shape = sensorShape;
		
		
		Fixture fixture = body.createFixture(fixtureDef);
		fixture.setUserData(sensorsControllerMap.get(SensorsID.LEFT_BOTTOM));
	}

	public boolean isTouchingGround()
	{
		return sensorsControllerMap.get(SensorsID.BOTTOM).isTouching();
	}

	public boolean isTouchingWallAhead()
	{
		switch (gameHero.getForwardSide()) {
		case LEFT:
			return sensorsControllerMap.get(SensorsID.LEFT_BOTTOM).isTouching() ||
					sensorsControllerMap.get(SensorsID.LEFT_TOP).isTouching();
		case RIGHT:
			return sensorsControllerMap.get(SensorsID.RIGHT_BOTTOM).isTouching() ||
					sensorsControllerMap.get(SensorsID.RIGHT_TOP).isTouching();
		default:
			return false;
		}
	}
}
