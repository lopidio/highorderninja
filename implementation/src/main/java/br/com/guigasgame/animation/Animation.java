package br.com.guigasgame.animation;

import java.awt.Rectangle;

import org.jsfml.graphics.Sprite;

public class Animation implements UpdatableFromTime
{
	private short currentFrameNumber;
	private float secondsSinceLastUpdate;
	private Rectangle frameRect;
	private AnimationDefinition animationDefinition;
	Sprite sprite;
	
	private Animation(AnimationDefinition animationDefinition) {
		this.animationDefinition = animationDefinition;
		//Private constructor
	}

	public Animation createAnimation(AnimationDefinition animationDefinition)
	{
		Animation animation = new Animation(animationDefinition);
		return animation;
	}

	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	public Sprite getSprite()
	{
		return sprite;
		
	}
}
