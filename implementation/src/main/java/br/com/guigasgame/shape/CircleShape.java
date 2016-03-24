package br.com.guigasgame.shape;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.jbox2d.common.Vec2;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;


@XmlAccessorType(XmlAccessType.NONE)
public class CircleShape extends Shape
{

	@XmlElement
	private Point center;

	@XmlElement
	private float radius;

	public CircleShape()
	{
		super();
		center = new Point();
		radius = 0;
	}

	public CircleShape(Point center, float radius, String textureFilename)
	{
		super(textureFilename);
		this.center = center;
		this.radius = radius;
	}

	public Point getCenter()
	{
		return center;
	}

	public void setCenter(Point center)
	{
		this.center = center;
	}

	public float getRadius()
	{
		return radius;
	}

	public void setRadius(float radius)
	{
		this.radius = radius;
	}

	@Override
	public org.jbox2d.collision.shapes.Shape createBox2dShape()
	{
		final org.jbox2d.collision.shapes.CircleShape shape = new org.jbox2d.collision.shapes.CircleShape();
		
		final Vec2 box2dcenter = WorldConstants.sfmlToPhysicsCoordinates(pointToSfmlVector2(center));
		shape.setRadius(WorldConstants.toBox2dWorld(radius));
		
		shape.getVertex(0).x = box2dcenter.x;
		shape.getVertex(0).y = box2dcenter.y;
		
		return shape;
	}

	@Override
	protected org.jsfml.graphics.Shape createSfmlPolygon()
	{
		final Vector2f position = pointToSfmlVector2(center);
		final org.jsfml.graphics.Shape shape = new org.jsfml.graphics.CircleShape(radius);
		shape.setPosition(position);
		shape.setOrigin(radius, radius);
		return shape;
	}

}
