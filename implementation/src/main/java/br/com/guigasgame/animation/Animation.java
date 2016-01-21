package br.com.guigasgame.animation;

import java.awt.Rectangle;
import java.io.IOException;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.updatable.UpdatableFromTime;

public class Animation implements UpdatableFromTime {

	private final AnimationProperties animationDefinition;
	private short currentFrameNumber;
	private float secondsSinceLastUpdate;
	private Rectangle frameRect;
	private Sprite sprite;

	// Private constructor
	private Animation(AnimationProperties animationProperties) throws IOException {
		this.animationDefinition = animationProperties;
		if (animationProperties.horizontal) {
			frameRect = new Rectangle(animationDefinition.textureSpriteRect.width / animationDefinition.numFrames,
					animationDefinition.textureSpriteRect.height);
		} else // if (animationProperties.vertical)
		{
			frameRect = new Rectangle(animationDefinition.textureSpriteRect.width,
					animationDefinition.textureSpriteRect.height / animationDefinition.numFrames);
		}
		sprite = new Sprite(animationProperties.texture);
		sprite.setOrigin(frameRect.width / 2, frameRect.height / 2);

		secondsSinceLastUpdate = 0;
		currentFrameNumber = 0;
	}

	public static Animation createAnimation(AnimationProperties animationDefinition) {
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

	public void update(float deltaTime) {
		secondsSinceLastUpdate += deltaTime;
		// timeFromLastUpdate > 1/ framePerSecond
		float multiplicationValue = secondsSinceLastUpdate * animationDefinition.framePerSecond;
		if (multiplicationValue >= 1) {
			secondsSinceLastUpdate = multiplicationValue - 1;
			++currentFrameNumber;

			if (currentFrameNumber >= animationDefinition.numFrames) {
				currentFrameNumber -= animationDefinition.numFrames - animationDefinition.numEntranceFrames;
			}

			updateFrameRect();
		}
	}

	private void updateFrameRect() {
		// mudo a posição do rect
		if (animationDefinition.horizontal) 
		{
			frameRect.x = animationDefinition.textureSpriteRect.left + frameRect.width * currentFrameNumber;
		} else // if (animationDefinition.vertical)
		{
			frameRect.y = animationDefinition.textureSpriteRect.top + frameRect.height * currentFrameNumber;
		}
		sprite.setTextureRect(new IntRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height));
	}

	public final Sprite getSprite() {
		return sprite;
	}

	public void setPosition(Vector2f graphicPosition) {
		sprite.setPosition(graphicPosition);
	}

	public short getCurrentFrameNumber() {
		return currentFrameNumber;
	}
}
