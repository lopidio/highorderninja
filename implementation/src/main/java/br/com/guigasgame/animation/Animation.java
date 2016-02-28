package br.com.guigasgame.animation;

import java.awt.Rectangle;
import java.io.IOException;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.moveable.Moveable;
import br.com.guigasgame.side.Side;
import br.com.guigasgame.updatable.UpdatableFromTime;


public class Animation implements UpdatableFromTime, Drawable, Moveable
{

	private final AnimationProperties animationProperties;
	private short currentFrameNumber;
	private float secondsSinceLastUpdate;
	private Rectangle frameRect;
	private Sprite sprite;
	private boolean finished;

	// Private constructor
	private Animation(AnimationProperties animationProperties)
			throws IOException
	{
		this.animationProperties = animationProperties;
		if (animationProperties.horizontal)
		{
			frameRect = new Rectangle(
					(animationProperties.textureSpriteRect.width - animationProperties.textureSpriteRect.left)
							/ animationProperties.numFrames,
					(animationProperties.textureSpriteRect.height - animationProperties.textureSpriteRect.top));
		}
		else // if (animationProperties.vertical)
		{
			frameRect = new Rectangle(
					animationProperties.textureSpriteRect.width - animationProperties.textureSpriteRect.left,
					(animationProperties.textureSpriteRect.height - animationProperties.textureSpriteRect.top)
							/ animationProperties.numFrames);
		}
		frameRect.x = animationProperties.textureSpriteRect.left;
		frameRect.y = animationProperties.textureSpriteRect.top;	

		
		sprite = new Sprite(animationProperties.texture);
		sprite.setOrigin(frameRect.width / 2, frameRect.height / 2);
		
		secondsSinceLastUpdate = 0;
		currentFrameNumber = 0;
		updateFrameRect();
	}

	public static Animation createAnimation(AnimationProperties animationDefinition)
	{
		try
		{
			return new Animation(animationDefinition);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void update(float deltaTime)
	{
		secondsSinceLastUpdate += deltaTime;
		// timeFromLastUpdate > 1/ framePerSecond
		if (secondsSinceLastUpdate >= animationProperties.secondsPerFrame)
		{
			secondsSinceLastUpdate -= animationProperties.secondsPerFrame;
			++currentFrameNumber;

			if (currentFrameNumber >= animationProperties.numFrames)
			{
				finished = true;
				currentFrameNumber -= animationProperties.numFrames
						- animationProperties.numEntranceFrames;
			}

			updateFrameRect();
		}
	}

	private void updateFrameRect()
	{
		// mudo a posição do rect
		if (animationProperties.horizontal)
		{
			frameRect.x = animationProperties.textureSpriteRect.left
					+ frameRect.width * currentFrameNumber;
		}
		else
		// if (animationDefinition.vertical)
		{
			frameRect.y = animationProperties.textureSpriteRect.top
					+ frameRect.height * currentFrameNumber;
		}
		sprite.setTextureRect(new IntRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height));
	}

	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(sprite);
	}

	public final void flipAnimation(Side side)
	{
		if (side == Side.LEFT)
		{
			// flip X
			sprite.setScale(-1, 1);
		}
		else //if (side == Side.RIGHT)
		{
			// unflip X
			sprite.setScale(1, 1);
		}
	}
	
	
	public void setOrientation(float angleInDegrees)
	{
		sprite.setRotation(angleInDegrees);
	}

	public short getCurrentFrameNumber()
	{
		return currentFrameNumber;
	}

	public int getHeight()
	{
		return frameRect.height;
	}

	public int getWidth()
	{
		return frameRect.width;
	}

	public void setColor(Color color)
	{
		sprite.setColor(color);
	}
	
	public int getAlpha()
	{
		return sprite.getColor().a;
	}

	public void setAlpha(int alpha)
	{
		Color currentColor = sprite.getColor();
		Color newColor = new Color(currentColor.r, currentColor.g, currentColor.b, alpha);
		sprite.setColor(newColor);
	}

	public void scale(float scale)
	{
		sprite.setScale(new Vector2f(scale, scale));
//		sprite.scale(scale, scale);
	}

	@Override
	public void move(Vector2f graphicPosition)
	{
		setPosition(Vector2f.add(sprite.getPosition(), graphicPosition));
	}

	@Override
	public void setPosition(Vector2f graphicPosition)
	{
		sprite.setPosition(graphicPosition.x, graphicPosition.y );
	}

	public boolean isFinished()
	{
		return finished;
	}

}
