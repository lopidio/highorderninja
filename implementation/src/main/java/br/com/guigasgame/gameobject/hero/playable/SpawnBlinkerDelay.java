package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.color.ColorInterpolator;
import br.com.guigasgame.color.ColorLinearInterpolator;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class SpawnBlinkerDelay implements UpdatableFromTime
{
	private static float TOTAL_SPAWN_DELAY_TIME = 3;
	private PlayableGameHero playableGameHero;
	private ColorInterpolator colorInterpolator;
	private final ColorBlender originalColor;

	private float timeCounter;

	public SpawnBlinkerDelay(PlayableGameHero playableGameHero, ColorBlender colorBlender)
	{
		this.playableGameHero = playableGameHero;
		originalColor = colorBlender;
		colorInterpolator = new ColorLinearInterpolator(originalColor);
		resetCounter();
	}

	public void update(float deltaTime)
	{
		if (timeCounter > 0)
		{
			timeCounter -= deltaTime;
			if (timeCounter <= 0)
				playableGameHero.disableInvincibility();
		}
		updateColor(deltaTime);
	}
	
	private void updateColor(float deltaTime)
	{
		colorInterpolator.update(deltaTime);
		if (colorInterpolator.hasFinished())
		{
			if (colorInterpolator.getCurrentColor().getA() == 255 && timeCounter > 0)
			{
				colorInterpolator = new ColorLinearInterpolator(originalColor);
				colorInterpolator.interpolateToColor(originalColor.makeTranslucid(), 0.5f);
			}
			else if (colorInterpolator.getCurrentColor().getA() == 0)
			{
				colorInterpolator = new ColorLinearInterpolator(colorInterpolator.getCurrentColor());
				colorInterpolator.interpolateToColor(originalColor, 0.5f);
			}
		}
	}

	boolean isActive()
	{
		return timeCounter > 0 || (colorInterpolator.getCurrentColor().getA() != 255);
	}
	
	ColorBlender getColor()
	{
		return colorInterpolator.getCurrentColor();
	}

	void resetCounter()
	{
		timeCounter = TOTAL_SPAWN_DELAY_TIME;
	}

}
