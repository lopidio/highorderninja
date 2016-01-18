package br.com.guigasgame.animation;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class Animation implements UpdatableFromTime
{

	private short currentFrameNumber;
	private float secondsSinceLastUpdate;
	private Rectangle frameRect;
	private AnimationDefinition animationDefinition;
	private Texture texture;
	private Sprite sprite;
	
	//Private constructor
	private Animation(AnimationDefinition animationDefinition) throws IOException {
		this.animationDefinition = animationDefinition;
		texture = new Texture();
		texture.loadFromFile(new File(animationDefinition.textureFilename).toPath());
		frameRect = new Rectangle(texture.getSize().x / animationDefinition.numFrames, texture.getSize().y);
		sprite = new Sprite(texture);
		sprite.setOrigin(frameRect.width/2, frameRect.height/2);
		
		secondsSinceLastUpdate = 0;
		currentFrameNumber = 0;		
	}

	public static Animation createAnimation(AnimationDefinition animationDefinition)
	{
		try {
			Animation animation;
			animation = new Animation(animationDefinition);
			return animation;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void update(float deltaTime) 
	{
		secondsSinceLastUpdate += deltaTime;
		//timeFromLastUpdate > 1/ framePerSecond
		float multiplicationValue = secondsSinceLastUpdate * animationDefinition.framePerSecond;
		if (multiplicationValue >= 1)
		{
			secondsSinceLastUpdate = multiplicationValue - 1;
			++currentFrameNumber;

			if (currentFrameNumber >= animationDefinition.numFrames)
			{
				currentFrameNumber -= animationDefinition.numFrames - animationDefinition.numEntranceFrames;
			}

			//mudo a posição do rect
			frameRect.x = frameRect.width*currentFrameNumber;
			sprite.setTextureRect(new IntRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height ));
		}		
	}
	
	public final Sprite getSprite()
	{
		return sprite;
	}
	
	public void setPosition(Vector2f graphicPosition)
	{
		sprite.setPosition(graphicPosition);
	}

	public short getCurrentFrameNumber() {
		return currentFrameNumber;
	}
}
