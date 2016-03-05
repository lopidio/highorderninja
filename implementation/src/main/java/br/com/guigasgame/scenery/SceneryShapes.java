package br.com.guigasgame.scenery;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import br.com.guigasgame.shape.CircleShape;
import br.com.guigasgame.shape.RectangleShape;
import br.com.guigasgame.shape.TriangleShape;

@XmlAccessorType(XmlAccessType.NONE)
public class SceneryShapes 
{
	@XmlElement
	private List<CircleShape> circles;
	@XmlElement
	private List<TriangleShape> triangles;
	@XmlElement
	private List<RectangleShape> rectangles;

	public SceneryShapes(List<CircleShape> circles, List<TriangleShape> triangles, List<RectangleShape> rectangless) {
		super();
		this.circles = circles;
		this.triangles = triangles;
		this.rectangles = rectangless;
	}

	public SceneryShapes() {
		this.circles = new ArrayList<CircleShape>();
		this.triangles = new ArrayList<TriangleShape>();
		this.rectangles = new ArrayList<RectangleShape>();
	}

	public List<CircleShape> getCircles() {
		return circles;
	}

	public List<TriangleShape> getTriangles() {
		return triangles;
	}

	public List<RectangleShape> getRectangles() {
		return rectangles;
	}
	

	public void addCircleShape(CircleShape circleShape)
	{
		circles.add(circleShape);
	}

	public void addTriangleShape(TriangleShape triangleShape)
	{
		triangles.add(triangleShape);
	}

	public void addRectangleShape(RectangleShape rectangleShape)
	{
		rectangles.add(rectangleShape);
	}
	
	
}
