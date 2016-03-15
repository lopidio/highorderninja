package br.com.guigasgame.shape;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class Shape 
{
	@XmlAttribute(required=true)
	protected boolean deadly;

	@XmlAttribute(required=true)
	protected String textureName;

	public Shape() 
	{
		super();
	}
	
	public Shape(boolean deadly, String textureName)
	{
		this.deadly = deadly;
		this.textureName = textureName;
	}
	
	public Shape(String textureName)
	{
		this.textureName = textureName;
	}
	
	public boolean isDeadly()
	{
		return deadly;
	}
	
	public void setDeadly(boolean deadly)
	{
		this.deadly = deadly;
	}

	public String getTextureName() 
	{
		return textureName;
	}
	
	
}
