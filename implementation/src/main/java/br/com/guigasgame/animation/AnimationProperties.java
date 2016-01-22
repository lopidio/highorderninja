package br.com.guigasgame.animation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.jsfml.graphics.Texture;


@XmlAccessorType(XmlAccessType.FIELD)
public class AnimationProperties
{

	@XmlElement
	public final short numFrames;
	@XmlElement
	public final short numEntranceFrames;
	@XmlElement
	public final short framePerSecond;
	@XmlElement
	public final short textureSpriteRectTop;
	@XmlElement
	public final short textureSpriteRectLeft;
	@XmlElement
	public final short textureSpriteRectWidth;
	@XmlElement
	public final short textureSpriteRectHeight;
	@XmlAttribute
	public final boolean horizontal;

	public Texture texture;

	/**
	 * DO NOT USE
	 */
	private AnimationProperties()
	{
		this.numFrames = 0;
		this.numEntranceFrames = 0;
		this.framePerSecond = 0;
		this.textureSpriteRectTop = 0;
		this.textureSpriteRectLeft = 0;
		this.textureSpriteRectWidth = 0;
		this.textureSpriteRectHeight = 0;
		this.horizontal = true;
	}

	AnimationProperties(short numFrames, short numEntranceFrames,
			short framePerSecond, short textureSpriteRectTop,
			short textureSpriteRectLeft, short textureSpriteRectWidth,
			short textureSpriteRectHeight, boolean horizontal)
	{
		super();
		this.numFrames = numFrames;
		this.numEntranceFrames = numEntranceFrames;
		this.framePerSecond = framePerSecond;
		this.textureSpriteRectTop = textureSpriteRectTop;
		this.textureSpriteRectLeft = textureSpriteRectLeft;
		this.textureSpriteRectWidth = textureSpriteRectWidth;
		this.textureSpriteRectHeight = textureSpriteRectHeight;
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
