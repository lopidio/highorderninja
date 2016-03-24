package br.com.guigasgame.scenery.creation;

import java.util.ArrayList;
import java.util.List;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.shape.CircleShape;
import br.com.guigasgame.shape.RectangleShape;
import br.com.guigasgame.shape.Shape;
import br.com.guigasgame.shape.TriangleShape;


public class SceneShapeCreator
{

	private List<Shape> box2dShapes;
	private List<Drawable> drawableList;

	public SceneShapeCreator(SceneryShapes sceneryShapes)
	{
		box2dShapes = new ArrayList<>();
		drawableList = new ArrayList<>();

		createShapes(sceneryShapes);
	}

	private void createShapes(SceneryShapes sceneryShapes)
	{
		List<TriangleShape> triangleShapes = sceneryShapes.getTriangles();
		for( TriangleShape triangle : triangleShapes )
		{
			drawableList.add(new DrawableShape(triangle.createAsSfmlShape()));
			box2dShapes.add(triangle);
		}
		List<CircleShape> circleShapes = sceneryShapes.getCircles();
		for( CircleShape circleShape : circleShapes )
		{
			drawableList.add(new DrawableShape(circleShape.createAsSfmlShape()));
			box2dShapes.add(circleShape);
		}
		List<RectangleShape> rectangleShapes = sceneryShapes.getRectangles();
		for( RectangleShape rectangleShape : rectangleShapes )
		{
			drawableList.add(new DrawableShape(rectangleShape.createAsSfmlShape()));
			box2dShapes.add(rectangleShape);
		}

	}

	public List<Shape> getBox2dShapes()
	{
		return box2dShapes;
	}

	public List<Drawable> getDrawableList()
	{
		return drawableList;
	}

}
