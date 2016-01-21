package br.com.guigasgame.animation;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;

public class AnimationProperties 
{
	@XmlID
	public final AnimationsIndex heroAnimation;
    @XmlElement
	public final  short numFrames;
    @XmlElement
	public final short numEntranceFrames;
    @XmlElement
	public final short framePerSecond;
    @XmlElement
    public final IntRect textureSpriteRect;
    @XmlAttribute
	public final boolean horizontal;

    public final Texture texture;
	
	private AnimationProperties(AnimationsIndex heroAnimation, short numFrames, short numEntranceFrames, short framePerSecond,
			IntRect textureSpriteRect, boolean horizontal, Texture texture) {
		super();
		this.heroAnimation = heroAnimation;
		this.numFrames = numFrames;
		this.numEntranceFrames = numEntranceFrames;
		this.framePerSecond = framePerSecond;
		this.textureSpriteRect = textureSpriteRect;
		this.horizontal = horizontal;
		this.texture = texture;
	}

}
