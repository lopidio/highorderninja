package br.com.guigasgame.animation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.jsfml.graphics.Texture;

import br.com.guigasgame.math.Rect;


@XmlAccessorType(XmlAccessType.FIELD)
class AnimationProperties
{

	@XmlElement
	public final short numFrames;
	@XmlElement
	public final short numEntranceFrames;
	@XmlElement
	public final float secondsPerFrame;
	@XmlElement
	public final Rect textureSpriteRect;
	@XmlAttribute
	public final boolean horizontal;

	public Texture texture;

	/**
	 * DO NOT USE
	 */
	@SuppressWarnings("unused")
	private AnimationProperties()
	{
		this.numFrames = 0;
		this.numEntranceFrames = 0;
		this.secondsPerFrame = 0;
		this.textureSpriteRect = new Rect();
		this.horizontal = true;
	}

	AnimationProperties(short numFrames, short numEntranceFrames,
			float secondsPerFrame, Rect textureSpriteRect, boolean horizontal)
	{
		super();
		this.numFrames = numFrames;
		this.numEntranceFrames = numEntranceFrames;
		this.secondsPerFrame = secondsPerFrame;
		this.textureSpriteRect = textureSpriteRect;
		this.horizontal = horizontal;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

}
