package br.com.guigasgame.time;

import org.jsfml.system.Clock;
import org.jsfml.system.Time;


public class TimeMaster
{

	private float timeFactor;
	private Clock clock;

	public TimeMaster()
	{
		timeFactor = 1;
		clock = new Clock();
	}

	public Time getElapsedTime()
	{
		return Time.mul(clock.getElapsedTime(), timeFactor);
	}

	public float getTimeFactor()
	{
		return timeFactor;
	}

	public void resetTimeFactor()
	{
		timeFactor = 1;
		;
	}

	public void multiplyTimeFactor(float factor) throws Exception
	{
		if (factor > 0)
		{
			timeFactor *= factor;
		}
		else
		{
			throw new Exception(
					"Impossível multiplicar fator do tempo por número menor ou igual a zero");
		}
	}

	public void setTimeFactor(float factor) throws Exception
	{
		if (factor > 0)
		{
			this.timeFactor = factor;
		}
		else
		{
			throw new Exception(
					"Impossível alterar o fator do tempo por número menor ou igual a zero");
		}
	}

}
