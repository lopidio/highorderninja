package br.com.guigasgame.gamehero;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.dynamics.Fixture;


public class HeroSensorsController
{

	public enum FixtureSensorID
	{
		HEAD, UPPER, LEGS, FEET, LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM, BOTTOM
	}

	Map<FixtureSensorID, SensorController> sensorsControllerMap;

	public HeroSensorsController()
	{
		sensorsControllerMap = new HashMap<FixtureSensorID, SensorController>();
		for( FixtureSensorID sensorsID : FixtureSensorID.values() )
		{

			sensorsControllerMap.put(sensorsID, new SensorController());
		}

	}

	public void attachController(FixtureSensorID sensorID, Fixture fixturesToAdd)
	{
		SensorController sensor = sensorsControllerMap.get(sensorID);
		fixturesToAdd.setUserData(sensor);
	}

	public SensorController getController(FixtureSensorID sensorID)
	{
		return sensorsControllerMap.get(sensorID);
	}

}
