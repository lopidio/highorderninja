package br.com.guigasgame.scenery.creation;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.resourcemanager.TextureResourceManager;
import br.com.guigasgame.shape.CircleShape;
import br.com.guigasgame.shape.Point;
import br.com.guigasgame.shape.RectangleShape;
import br.com.guigasgame.shape.TriangleShape;

public class SceneShapeCreator 
{
	private List<Shape> box2dShapes;
	private List<Drawable> drawableList;

	public SceneShapeCreator(SceneryShapes sceneryShapes) 
	{
		box2dShapes = new ArrayList<>();
		drawableList = new ArrayList<>();

		addRectangleShapes(sceneryShapes);
		addCircleShapes(sceneryShapes);
		addTriangleShapes(sceneryShapes);
	}
	public List<Shape> getBox2dShapes() 
	{
		return box2dShapes;
	}
	
	private void addTriangleShapes(SceneryShapes sceneryShapes)
	{
		List<TriangleShape> triangleShapes = sceneryShapes.getTriangles();
		for( TriangleShape triangle : triangleShapes )
		{
			drawableList.add(new DrawableShape(createSfmlTriangle(triangle)));
			box2dShapes.add(createBox2dTriangle(triangle));
		}
	}

	private Shape createBox2dTriangle(TriangleShape triangle)
	{
		PolygonShape shape = new PolygonShape();
		Vector2f pointA = pointToSfmlVector2(triangle.getPointA());
		Vector2f pointB = pointToSfmlVector2(triangle.getPointB());
		Vector2f pointC = pointToSfmlVector2(triangle.getPointC());
		
		Vec2[] vertices = new Vec2[] {	WorldConstants.sfmlToPhysicsCoordinates(pointC),
										WorldConstants.sfmlToPhysicsCoordinates(pointB),
										WorldConstants.sfmlToPhysicsCoordinates(pointA)};
		
		shape.set(vertices, vertices.length);
		return shape;

	}

	private org.jsfml.graphics.Shape createSfmlTriangle(TriangleShape triangle)
	{
		Vector2f pointA = pointToSfmlVector2(triangle.getPointA());
		Vector2f pointB = pointToSfmlVector2(triangle.getPointB());
		Vector2f pointC = pointToSfmlVector2(triangle.getPointC());
		ConvexShape shape = new ConvexShape();
		shape.setTexture(TextureResourceManager.getInstance().getResource(triangle.getTextureName()));
		shape.setPoints(pointA, pointB, pointC);
		if (triangle.isDeadly())
			shape.setFillColor(Color.RED);
		return shape;
	}

	private void addCircleShapes(SceneryShapes sceneryShapes)
	{
		List<CircleShape> circleShapes = sceneryShapes.getCircles();
		for( CircleShape circleShape : circleShapes )
		{
			drawableList.add(new DrawableShape(createSfmlCircle(circleShape)));
			box2dShapes.add(createBox2dCircle(circleShape));
		}
	}

	private Shape createBox2dCircle(CircleShape circleShape)
	{
		org.jbox2d.collision.shapes.CircleShape shape = new org.jbox2d.collision.shapes.CircleShape();
		
		Vec2 box2dcenter = WorldConstants.sfmlToPhysicsCoordinates(pointToSfmlVector2(circleShape.getCenter()));
		shape.setRadius(WorldConstants.toBox2dWorld(circleShape.getRadius()));
		
		shape.getVertex(0).x = box2dcenter.x;
		shape.getVertex(0).y = box2dcenter.y;
		
		return shape;
	}

	private org.jsfml.graphics.Shape createSfmlCircle(CircleShape circleShape)
	{
		Vector2f position = pointToSfmlVector2(circleShape.getCenter());
		
		org.jsfml.graphics.Shape shape = new org.jsfml.graphics.CircleShape(circleShape.getRadius());
		shape.setPosition(position);
		shape.setTexture(TextureResourceManager.getInstance().getResource(circleShape.getTextureName()));
		shape.setOrigin(circleShape.getRadius(), circleShape.getRadius());
		if (circleShape.isDeadly())
			shape.setFillColor(Color.RED);
		return shape;
	}

	private void addRectangleShapes(SceneryShapes sceneryShapes)
	{
		List<RectangleShape> rectangleShapes = sceneryShapes.getRectangles();
		for( RectangleShape rectangleShape : rectangleShapes )
		{
			drawableList.add(new DrawableShape(createSfmlRectangle(rectangleShape)));
			box2dShapes.add(createBox2dRectangle(rectangleShape));
		}
		
	}

	private Shape createBox2dRectangle(RectangleShape rectangleShape)
	{
		PolygonShape shape = new PolygonShape();
		
		Vec2 box2dcenter = WorldConstants.sfmlToPhysicsCoordinates(pointToSfmlVector2(rectangleShape.getCenter()));
		Vec2 box2dHalfDimension = WorldConstants.sfmlToPhysicsCoordinates(pointToSfmlVector2(rectangleShape.getHalfDimension()));
		shape.setAsBox(box2dHalfDimension.x, box2dHalfDimension.y, box2dcenter, 0);
		return shape;
	}

	
	private org.jsfml.graphics.Shape createSfmlRectangle(RectangleShape rectangleShape)
	{
		Vector2f position = pointToSfmlVector2(rectangleShape.getCenter());
		Vector2f dimension = pointToSfmlVector2(rectangleShape.getHalfDimension());
		dimension = Vector2f.mul(dimension, 2);
		org.jsfml.graphics.Shape shape = new org.jsfml.graphics.RectangleShape(dimension);
		shape.setTexture(TextureResourceManager.getInstance().getResource(rectangleShape.getTextureName()));
		shape.setPosition(position);
		shape.setOrigin(dimension.x/2, dimension.y/2);
		if (rectangleShape.isDeadly())
			shape.setFillColor(Color.RED);
		
		return shape;
	}
	
	public List<Drawable> getDrawableList() 
	{
		return drawableList;
	}
	
	private static Vector2f pointToSfmlVector2(Point point)
	{
		return new Vector2f(point.getX(), point.getY());
	}
	
	
}
