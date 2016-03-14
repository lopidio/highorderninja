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
	private final Vector2f speed;
	private final float distanceFromCamera;
	private final boolean regenerate;
	private boolean markToregenerate;

	public BackgroundGameObject(BackgroundItemProperties backgroundItem, Texture texture)
	{
		AnimationProperties properties = backgroundItem.getAnimationProperties();
		properties.setTexture(texture);
		animation = Animation.createAnimation(properties);
		animation.setPosition(pointToSfmlVector2(backgroundItem.getOrigin()));
		regenerate = backgroundItem.isRegenerate();
		
		distanceFromCamera = backgroundItem.getDistanceFromCamera();
		speed = pointToSfmlVector2(backgroundItem.getSpeed());
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		animation.draw(renderWindow);
		if (regenerate)
		{
			if (animation.getPosition().x > renderWindow.getSize().x)
				markToregenerate();
			else if (animation.getPosition().x + animation.getWidth() < 0)
				markToregenerate();
			if (animation.getPosition().y > renderWindow.getSize().y)
				markToregenerate();
			else if (animation.getPosition().y  + animation.getHeight() < 0)
				markToregenerate();
		}
	}

	private void markToregenerate()
	{
		markToregenerate = true;
	}

	@Override
	public void update(float deltaTime)
	{
		animation.move(Vector2f.mul(speed, deltaTime));
		animation.update(deltaTime);
		if (markToregenerate)
		{
//			animation.setPosition(origin);
			markToregenerate = false;
		}
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
