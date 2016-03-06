package br.com.guigasgame.background;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationProperties;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.shape.Point;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class BackgroundGameObject implements UpdatableFromTime, Drawable
{

	private Animation animation;
	private final Vector2f factor;
	private final float distanceFromCamera;

	public BackgroundGameObject(BackgroundFileItem backgroundItem, Texture texture)
	{
		AnimationProperties properties = backgroundItem.getAnimationProperties();
		properties.setTexture(texture);
		animation = Animation.createAnimation(properties);
		animation.setOrigin(pointToSfmlVector2(backgroundItem.getOrigin()));
		
		distanceFromCamera = backgroundItem.getDistanceFromCamera();
		factor = Vector2f.div(pointToSfmlVector2(backgroundItem.getSpeed()), distanceFromCamera);
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		animation.draw(renderWindow);
	}

	@Override
	public void update(float deltaTime)
	{
		Vector2f adjustment = Vector2f.add(animation.getPosition(), Vector2f.mul(factor, deltaTime));
		animation.setOrigin(adjustment);
//		animation.addPosition(adjustment);
		animation.update(deltaTime);
	}
	
	private static Vector2f pointToSfmlVector2(Point point)
	{
		return new Vector2f(point.getX(), point.getY());
	}

	public float getDistanceFromCamera()
	{
		return distanceFromCamera;
	}

}
