package br.com.guigasgame.shape;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class TriangleShape  extends Shape
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
	
	public TriangleShape(Point pointA, Point pointB, Point pointC) 
	{
		super();
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
	}

	public Point getPointA() {
		return pointA;
	}

	public void setPointA(Point pointA) {
		this.pointA = pointA;
	}

	public Point getPointB() {
		return pointB;
	}

	public void setPointB(Point pointB) {
		this.pointB = pointB;
	}

	public Point getPointC() {
		return pointC;
	}

	public void setPointC(Point pointC) {
		this.pointC = pointC;
	}
	
	
	
}
