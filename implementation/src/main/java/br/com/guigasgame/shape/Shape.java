package br.com.guigasgame.shape;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.resourcemanager.TextureResourceManager;

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
	
	public abstract org.jbox2d.collision.shapes.Shape createBox2dShape();
	protected abstract org.jsfml.graphics.Shape createSfmlPolygon();
	
	public org.jsfml.graphics.Shape createSfmlShape()
	{
		Texture texture = TextureResourceManager.getInstance().getResource(textureName);
		texture.setRepeated(true);
		org.jsfml.graphics.Shape shape = createSfmlPolygon();
		shape.setTextureRect(new IntRect(shape.getLocalBounds()));
		shape.setTexture(texture);
		return shape;
	}
	
}
