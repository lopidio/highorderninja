package br.com.guigasgame.shape;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


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
	
	public RectangleShape(Point center, Point halfDimension) {
		super();
		this.center = center;
		this.halfDimension = halfDimension;
	}

	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}
	public Point getHalfDimension() {
		return halfDimension;
	}
	public void setHalfDimension(Point halfDimension) {
		this.halfDimension = halfDimension;
	}
	
	
}
