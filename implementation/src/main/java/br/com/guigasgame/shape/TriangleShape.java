package br.com.guigasgame.shape;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;


@XmlAccessorType(XmlAccessType.NONE)
public class TriangleShape extends Shape
{

	@XmlElement
	private Point pointA;
	@XmlElement
	private Point pointB;
	@XmlElement
	private Point pointC;

	public TriangleShape()
	{
		super();
		pointA = new Point();
		pointB = new Point();
		pointC = new Point();
	}

	public TriangleShape(Point pointA, Point pointB, Point pointC, String textureFilename)
	{
		super(textureFilename);
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
	}

	public Point getPointA()
	{
		return pointA;
	}

	public void setPointA(Point pointA)
	{
		this.pointA = pointA;
	}

	public Point getPointB()
	{
		return pointB;
	}

	public void setPointB(Point pointB)
	{
		this.pointB = pointB;
	}

	public Point getPointC()
	{
		return pointC;
	}

	public void setPointC(Point pointC)
	{
		this.pointC = pointC;
	}

	@Override
	public org.jbox2d.collision.shapes.Shape createBox2dShape()
	{
		final PolygonShape shape = new PolygonShape();
		final Vector2f sfmlPointA = pointToSfmlVector2(pointA);
		final Vector2f sfmlPointB = pointToSfmlVector2(pointB);
		final Vector2f sfmlPointC = pointToSfmlVector2(pointC);
		
		final Vec2[] vertices = new Vec2[] {	WorldConstants.sfmlToPhysicsCoordinates(sfmlPointC),
										WorldConstants.sfmlToPhysicsCoordinates(sfmlPointB),
										WorldConstants.sfmlToPhysicsCoordinates(sfmlPointA)};
		
		shape.set(vertices, vertices.length);
		return shape;
	}

	@Override
	protected org.jsfml.graphics.Shape createSfmlPolygon()
	{
		final Vector2f sfmlPointA = pointToSfmlVector2(pointA);
		final Vector2f sfmlPointB = pointToSfmlVector2(pointB);
		final Vector2f sfmlPointC = pointToSfmlVector2(pointC);
		final ConvexShape shape = new ConvexShape();
		shape.setPoints(sfmlPointA, sfmlPointB, sfmlPointC);
		return shape;
	}

}
