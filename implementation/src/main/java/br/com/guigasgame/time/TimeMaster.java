package br.com.guigasgame.time;

import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;


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
	}

	public void multiplyTimeFactor(float factor) throws Exception
	{
		if (factor > 0)
		{
			timeFactor *= factor;
		}
		else
		{
			throw new Exception("Impossível multiplicar fator do tempo por número menor ou igual a zero");
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
			throw new Exception("Impossível alterar o fator do tempo por número menor ou igual a zero");
		}
	}

	public void restart()
	{
		clock.restart();
	}

	public void handleEvent(Event event)
	{
		if (event.type == Type.KEY_PRESSED)
		{
			if (event.asKeyEvent().key == Key.P)
			{
				try
				{
					setTimeFactor(0.3f);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (event.asKeyEvent().key == Key.O)
			{
				try
				{
					resetTimeFactor();
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
