package br.com.guigasgame.gameobject.hero.sensors;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlEnum;

import org.jbox2d.dynamics.Fixture;


public class HeroSensorsController
{

	@XmlEnum
	public enum FixtureSensorID
	{
		HEAD, UPPER, FEET,
		// SENSORS
		LEFT_TOP_SENSOR, RIGHT_TOP_SENSOR, LEFT_BOTTOM_SENSOR, RIGHT_BOTTOM_SENSOR, BOTTOM_SENSOR
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
