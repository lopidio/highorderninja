package br.com.guigasgame.math;

import java.util.Random;

public class Randomizer
{
	private static final Random randomizer = new Random();

	public static int getRandomIntInInterval(int min, int max)
	{
		if (max == min)
			return max;
		int value = randomizer.nextInt(max - min);
		return value + min;
	}

	public static float getRandomFloatInInterval(float max, float min)
	{
		return (randomizer.nextFloat()*(max - min)) + min;
	}
}
