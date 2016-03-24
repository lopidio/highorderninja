package br.com.guigasgame.shape;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.resourcemanager.TextureResourceManager;


@XmlAccessorType(XmlAccessType.NONE)
public class RectangleShape extends Shape
{

	@XmlElement
	private Point center;
	@XmlElement
	private Point halfDimension;

	public RectangleShape()
	{
		super();
		center = new Point();
		halfDimension = new Point();
	}

	public RectangleShape(Point center, Point halfDimension, String textureFilename)
	{
		super(textureFilename);
		this.center = center;
		this.halfDimension = halfDimension;
	}

	public Point getCenter()
	{
		return center;
	}

	public void setCenter(Point center)
	{
		this.center = center;
	}

	public Point getHalfDimension()
	{
		return halfDimension;
	}

	public void setHalfDimension(Point halfDimension)
	{
		this.halfDimension = halfDimension;
	}

	@Override
	public org.jbox2d.collision.shapes.Shape createAsBox2dShape()
	{
		final PolygonShape shape = new PolygonShape();
		
		final Vec2 box2dcenter = WorldConstants.sfmlToPhysicsCoordinates(pointToSfmlVector2(center));
		final Vec2 box2dHalfDimension = WorldConstants.sfmlToPhysicsCoordinates(pointToSfmlVector2(halfDimension));
		shape.setAsBox(box2dHalfDimension.x, box2dHalfDimension.y, box2dcenter, 0);
		return shape;
	}

	@Override
	public org.jsfml.graphics.Shape createAsSfmlShape()
	{
		final Vector2f position = pointToSfmlVector2(center);
		Vector2f dimension = pointToSfmlVector2(halfDimension);
		dimension = Vector2f.mul(dimension, 2);
		org.jsfml.graphics.Shape shape = new org.jsfml.graphics.RectangleShape(dimension);
		shape.setTexture(TextureResourceManager.getInstance().getResource(textureName));
		shape.setPosition(position);
		shape.setOrigin(dimension.x/2, dimension.y/2);
		
		return shape;
	}

}
