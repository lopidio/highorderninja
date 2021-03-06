package br.com.guigasgame.animation;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.math.Rect;
import br.com.guigasgame.moveable.Moveable;
import br.com.guigasgame.resourcemanager.TextureResourceManager;
import br.com.guigasgame.side.Side;
import br.com.guigasgame.updatable.UpdatableFromTime;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;


public class Animation implements UpdatableFromTime, Drawable, Moveable
{

	private final AnimationProperties animationProperties;
	private short currentFrameNumber;
	private float secondsSinceLastUpdate;
	private Rect frameRect;
	private Sprite sprite;
	private boolean finished;

	// Private constructor
	private Animation(AnimationProperties animationProperties)
			throws IOException
	{
		this.animationProperties = animationProperties;
		if (animationProperties.horizontal)
		{
			frameRect = new Rect(0, 0,
					(animationProperties.textureSpriteRect.width - animationProperties.textureSpriteRect.left)
							/ animationProperties.numFrames,
					(animationProperties.textureSpriteRect.height - animationProperties.textureSpriteRect.top));
		}
		else // if (animationProperties.vertical)
		{
			frameRect = new Rect(0, 0,
					animationProperties.textureSpriteRect.width - animationProperties.textureSpriteRect.left,
					(animationProperties.textureSpriteRect.height - animationProperties.textureSpriteRect.top)
							/ animationProperties.numFrames);
		}
		frameRect.left = animationProperties.textureSpriteRect.left;
		frameRect.top = animationProperties.textureSpriteRect.top;

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
			Texture texture = animationDefinition.getTexture();
			if (texture == null)
			{
				texture = TextureResourceManager.getInstance().getResource(animationDefinition.textureFilename);
				texture.setSmooth(true);
				animationDefinition.setTexture(texture);
			}

			return new Animation(animationDefinition);
		}
		catch (IOException e)
		{
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
			frameRect.left = animationProperties.textureSpriteRect.left
					+ frameRect.width * currentFrameNumber;
		}
		else
		// if (animationDefinition.vertical)
		{
			frameRect.top = animationProperties.textureSpriteRect.top
					+ frameRect.height * currentFrameNumber;
		}
		sprite.setTextureRect(new IntRect(frameRect.left, frameRect.top, frameRect.width, frameRect.height));
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


	public void setRotation(double angle)
	{
		sprite.setRotation((float) angle);
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

	public void setColor(ColorBlender color)
	{
		sprite.setColor(color.getSfmlColor());
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
	}

	public void scale(Vector2f scale)
	{
		sprite.setScale(scale);
	}

	@Override
	public void setPosition(Vector2f origin)
	{
		sprite.setPosition(origin);
	}

	@Override
	public void move(Vector2f offset)
	{
		sprite.setPosition(Vector2f.add(sprite.getPosition(), offset));
	}

	public boolean isFinished()
	{
		return finished;
	}

	@Override
	public Vector2f getPosition()
	{
		return sprite.getPosition();
	}

	public Sprite getSprite()
	{
		return sprite;
	}

	public ConstTexture getTexture()
	{
		return sprite.getTexture();
	}

}
