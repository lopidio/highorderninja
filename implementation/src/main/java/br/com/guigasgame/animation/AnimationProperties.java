package br.com.guigasgame.animation;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;

public class AnimationProperties 
{
	@XmlAttribute
	public final short animationIndex;
    @XmlElement
	public final short numFrames;
    @XmlElement
	public final short numEntranceFrames;
    @XmlElement
	public final short framePerSecond;
    @XmlElement
    public final IntRect textureSpriteRect;
    @XmlAttribute
	public final boolean horizontal;

    public Texture texture;
	
	AnimationProperties(short animationIndex, short numFrames, short numEntranceFrames, short framePerSecond,
			IntRect textureSpriteRect, boolean horizontal) {
		super();
		this.animationIndex = animationIndex;
		this.numFrames = numFrames;
		this.numEntranceFrames = numEntranceFrames;
		this.framePerSecond = framePerSecond;
		this.textureSpriteRect = textureSpriteRect;
		this.horizontal = horizontal;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
