package br.com.guigasgame.scenery;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.resourcemanager.TextureResourceManager;
import br.com.guigasgame.shape.parser.CircleShape;
import br.com.guigasgame.shape.parser.Point;
import br.com.guigasgame.shape.parser.RectangleShape;
import br.com.guigasgame.shape.parser.SceneryFile;
import br.com.guigasgame.shape.parser.SceneryShapes;
import br.com.guigasgame.shape.parser.TriangleShape;

public class Scenery extends GameObject
{
	private final Texture texture;
	private List<Shape> box2dShapes;
	private SceneryShapeCollidable shapeCollidable;
	
	public Scenery(SceneryFile sceneryFile)
	{
		texture = TextureResourceManager.getInstance().getResource(sceneryFile.getTextureName());
		shapeCollidable = new SceneryShapeCollidable(new Vec2());
		collidableList.add(shapeCollidable);
		
		box2dShapes = new ArrayList<>();
		
		SceneryShapes sceneryShapes = sceneryFile.getSceneryShapes();
		
		addRectangleShapes(sceneryShapes);
		addCircleShapes(sceneryShapes);
		addTriangleShapes(sceneryShapes);
	}
	
	private void addTriangleShapes(SceneryShapes sceneryShapes)
	{
		List<TriangleShape> triangleShapes = sceneryShapes.getTriangles();
		for( TriangleShape triangle : triangleShapes )
		{
			drawableList.add(new DrawableTile(createSfmlTriangle(triangle)));
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
		System.out.println(shape.getVertexCount() + ", " + shape.m_count);
		return shape;

	}

	private org.jsfml.graphics.Shape createSfmlTriangle(TriangleShape triangle)
	{
		Vector2f pointA = pointToSfmlVector2(triangle.getPointA());
		Vector2f pointB = pointToSfmlVector2(triangle.getPointB());
		Vector2f pointC = pointToSfmlVector2(triangle.getPointC());
		ConvexShape shape = new ConvexShape();
		shape.setPoints(pointA, pointB, pointC);
		shape.setTexture(texture, true);
		return shape;
	}

	private void addCircleShapes(SceneryShapes sceneryShapes)
	{
		List<CircleShape> circleShapes = sceneryShapes.getCircles();
		for( CircleShape circleShape : circleShapes )
		{
			drawableList.add(new DrawableTile(createSfmlCircle(circleShape)));
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
		shape.setOrigin(circleShape.getRadius(), circleShape.getRadius());
		shape.setTexture(texture, true);
		return shape;
	}

	private void addRectangleShapes(SceneryShapes sceneryShapes)
	{
		List<RectangleShape> rectangleShapes = sceneryShapes.getRectangles();
		for( RectangleShape rectangleShape : rectangleShapes )
		{
			drawableList.add(new DrawableTile(createSfmlRectangle(rectangleShape)));
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
		shape.setPosition(position);
		shape.setOrigin(dimension.x/2, dimension.y/2);
		shape.setTexture(texture, true);
		
		return shape;
	}
	
	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		for( Shape shape : box2dShapes )
		{
			shapeCollidable.addListener(this);
			shapeCollidable.addFixture(shape);
		}
		box2dShapes.clear();
	}

	
	
	private Vector2f pointToSfmlVector2(Point point)
	{
		return new Vector2f(point.getX(), point.getY());
	}
}
