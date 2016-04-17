package br.com.guigasgame.gamemachine;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationProperties;
import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.math.Rect;

public class SplashScreenGameState implements GameState
{
	private float totalTime;
	private final Animation animation;
	
	public SplashScreenGameState(float totalTime)
	{
		this.totalTime = totalTime;
		AnimationProperties animationProperties = new AnimationProperties((short)8, (short)0, 0.1f, new Rect(0, 0, 1600, 255), true, FilenameConstants.getLogoFilename());
		animation = Animation.createAnimation(animationProperties);
	}

	@Override
	public void update(float updateDelta)
	{
		totalTime -= updateDelta;
		animation.update(updateDelta);
		if (totalTime <= 0)
		{
			GameMachine.getInstance().switchState(new RoundConfigurationState());
		}
	}
	
	@Override
	public void enterState(RenderWindow renderWindow)
	{
		animation.setPosition(Vector2f.div(new Vector2f(renderWindow.getSize()), 2));
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		animation.draw(renderWindow);
	}
	
	@Override
	public ColorBlender getBackgroundColor()
	{
		return ColorBlender.BLACK;
	}

}
