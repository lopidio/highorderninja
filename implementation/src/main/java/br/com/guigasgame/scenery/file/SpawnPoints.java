package br.com.guigasgame.scenery.file;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.guigasgame.shape.Point;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SpawnPoints 
{
	@XmlElement
	private List<Point> point;
	
	public SpawnPoints(List<Point> point)
	{
		super();
		this.point = point;
	}

	public SpawnPoints()
	{
		super();
	}

	public List<Point> getPoint() 
	{
		return point;
	}

	public void setPoint(List<Point> point) 
	{
		this.point = point;
	}
	
	
}
