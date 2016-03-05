package br.com.guigasgame.shape.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class Shape 
{
	@XmlAttribute(required=true)
	protected boolean deadly;

	public Shape() 
	{
		super();
	}
	
	public Shape(boolean deadly)
	{
		this.deadly = deadly;
	}
	
	public boolean isDeadly()
	{
		return deadly;
	}
	
	public void setDeadly(boolean deadly)
	{
		this.deadly = deadly;
	}

	
}
