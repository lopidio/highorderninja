package br.com.guigasgame.gameobject.item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import br.com.guigasgame.shape.Point;

@XmlAccessorType(XmlAccessType.NONE)
public class GameItemProperties
{
	@XmlElement
	public final float restitution;
	@XmlElement
	public final float friction;
	@XmlElement
	public final float mass;
	@XmlElement
	public final Point size;
	@XmlElement
	public final float period;
	@XmlElement
	public final float lifeTime;
	
	public GameItemProperties(float restitution, float friction, float mass, Point size, float period, float lifeTime)
	{
		this.restitution = restitution;
		this.friction = friction;
		this.mass = mass;
		this.size = size;
		this.period = period;
		this.lifeTime = lifeTime;
	}

	public GameItemProperties()
	{
		this.restitution = 0;
		this.friction = 0;
		this.mass = 0;
		this.size = new Point();
		this.period = 0;
		this.lifeTime = 0;
	}

}
