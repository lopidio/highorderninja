package br.com.guigasgame.box2d.debug;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;


public class SFMLDebugDraw extends DebugDraw
{

	RenderWindow renderWindow;

	public SFMLDebugDraw(IViewportTransform viewport, RenderWindow renderWindow)
	{
		super(viewport);
		this.renderWindow = renderWindow;
	}

	// convert a Box2D (float 0.0f - 1.0f range) color to a SFML color (uint8 0
	// - 255 range)
	Color makeColor(Color3f color)
	{
		return new Color((int) (color.x * 255), (int) (color.y * 255),
				(int) (color.z * 255));
	}

	@Override
	public void drawCircle(Vec2 arg0, float arg1, Color3f arg2)
	{
		CircleShape cs = new CircleShape();
		cs.setPosition(WorldConstants.physicsToSfmlCoordinates(arg0));
		cs.setRadius(arg1);
		cs.setFillColor(makeColor(arg2));
		renderWindow.draw(cs);
	}

	@Override
	public void drawPoint(Vec2 arg0, float arg1, Color3f arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void drawSegment(Vec2 arg0, Vec2 arg1, Color3f arg2)
	{
		VertexArray line = new VertexArray();
		line.add(new Vertex(WorldConstants
				.physicsToSfmlCoordinates(arg0), makeColor(arg2)));
		line.add(new Vertex(WorldConstants
				.physicsToSfmlCoordinates(arg1), makeColor(arg2)));
		line.setPrimitiveType(PrimitiveType.LINES);

		renderWindow.draw(line);
	}

	@Override
	public void drawSolidCircle(Vec2 center, float radius, Vec2 axis,
			Color3f color)
	{
		// no converion in cordinates of center and upper left corner, Circle in
		// sfml is managed by default with the center
		CircleShape circle = new CircleShape(radius * WorldConstants.SCALE);
		circle.setPosition(center.x * WorldConstants.SCALE - radius
				* WorldConstants.SCALE, center.y * WorldConstants.SCALE
				- radius * WorldConstants.SCALE);
		circle.setFillColor(Color.mul(makeColor(color), Color.RED));
		circle.setOutlineColor(makeColor(color));
		circle.setOutlineThickness(1.f);

		// line of the circle wich shows the angle
		Vec2 p = center.add(axis.mul(radius));
		drawSegment(center, p, color);

		renderWindow.draw(circle);
	}

	@Override
	public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color)
	{
		ConvexShape polygon = new ConvexShape(vertexCount);
		for( int i = 0; i < vertices.length; i++ )
		{
			Vec2 vertex = vertices[i];
			if (vertex.length() > 0)
			{
				polygon.setPoint(i, WorldConstants
						.physicsToSfmlCoordinates(vertex));
			}
		}
		polygon.setFillColor(Color.mul(makeColor(color),
				Color.mul(Color.RED, Color.WHITE)));
		polygon.setOutlineColor(makeColor(color));
		polygon.setOutlineThickness(1.0f);
		renderWindow.draw(polygon);
	}

	@Override
	public void drawString(float arg0, float arg1, String arg2, Color3f arg3)
	{
		Text fpsText = new Text();
		// fpsText.SetFont(Font.geGetDefaultFont());
		fpsText.scale(100, 100);
		fpsText.setPosition(arg0, arg1);
		fpsText.setString(arg2);
		renderWindow.draw(fpsText);
	}

	@Override
	public void drawTransform(Transform xf)
	{
		float lineProportion = 0.4f;
		// Vec2 p1 = xf.p, p2;
		//
		// // red (X axis)
		// p2 = p1.add(new Vec2(p1.x + lineProportion * xf.p.x, p1.y));
		// drawSegment(p1, p2, new Color3f(255, 0, 0));
		//
		// // green (Y axis)
		// p2 = p1.add(new Vec2(p1.x, p1.y + lineProportion * xf.p.y));
		// drawSegment(p1, p2, new Color3f(0, 255, 0));
	}

}
