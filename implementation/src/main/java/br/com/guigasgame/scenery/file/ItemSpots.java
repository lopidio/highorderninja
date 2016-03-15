package br.com.guigasgame.scenery.file;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.guigasgame.shape.Point;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ItemSpots 
{
	@XmlElement
	private List<Point> spot;

	public ItemSpots() 
	{
		super();
	}
	
	public ItemSpots(List<Point> spot) 
	{
		super();
		this.spot = spot;
	}

	public List<Point> getSpot() 
	{
		return spot;
	}

	public void setSpot(List<Point> spot)
	{
		this.spot = spot;
	}
	
	

}
