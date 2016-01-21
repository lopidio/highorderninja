package br.com.guigasgame.animation;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.updatable.UpdatableFromTime;

public class Animation implements UpdatableFromTime
{

	private short currentFrameNumber;
	private float secondsSinceLastUpdate;
	private Rectangle frameRect;
	private AnimationProperties animationDefinition;
	private Texture texture;
	private Sprite sprite;
	
	//Private constructor
	private Animation(AnimationProperties animationDefinition) throws IOException {
		this.animationDefinition = animationDefinition;
		texture = new Texture();
		texture.loadFromFile(new File(animationDefinition.textureFilename).toPath());
		frameRect = new Rectangle(texture.getSize().x / animationDefinition.numFrames, texture.getSize().y);
		sprite = new Sprite(texture);
		sprite.setOrigin(frameRect.width/2, frameRect.height/2);
		
		secondsSinceLastUpdate = 0;
		currentFrameNumber = 0;		
	}

	public static Animation createAnimation(AnimationProperties animationDefinition)
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

			updateFrameRect();
		}		
	}

	private void updateFrameRect() {
		//mudo a posição do rect
		if (animationDefinition.horizontal)
		{
			frameRect.x = frameRect.width*currentFrameNumber;
		}
		else // if (!animationDefinition.horizontal)
		{
			frameRect.y = frameRect.height*currentFrameNumber;
		}
		sprite.setTextureRect(new IntRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height ));
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
