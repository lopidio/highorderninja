package br.com.guigasgame.shape;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.jsfml.system.Vector2f;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class Shape 
{
	@XmlAttribute
	protected float damagePerSecond;

	@XmlAttribute(required=true)
	protected String textureName;

	public Shape() 
	{
		super();
	}
	
	public Shape(float damagePerSecond, String textureName)
	{
		this.textureName = textureName;
	}
	
	public Shape(String textureName)
	{
		this.textureName = textureName;
	}
	
	public boolean isDeadly()
	{
		return damagePerSecond > 0;
	}
	
	public void setDamagePerSecond(float damagePerSecond)
	{
		this.damagePerSecond = damagePerSecond;
	}
	
	public float getDamagePerSecond()
	{
		return damagePerSecond;
	}

	public String getTextureName() 
	{
		return textureName;
	}
	
	protected static Vector2f pointToSfmlVector2(Point point)
	{
		return new Vector2f(point.getX(), point.getY());
	}
	
	public abstract org.jbox2d.collision.shapes.Shape createAsBox2dShape();
	public abstract org.jsfml.graphics.Shape createAsSfmlShape();
	
}
