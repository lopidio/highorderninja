package br.com.guigasgame.shape.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

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

	public CircleShape(Point center, float radius)
	{
		super();
		this.center = center;
		this.radius = radius;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	
}
