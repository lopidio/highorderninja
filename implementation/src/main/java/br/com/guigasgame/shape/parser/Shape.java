package br.com.guigasgame.shape.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class Shape 
{
	@XmlAttribute(required=true)
	protected String property;

	public Shape() {
		super();
	}

	public Shape(String property) {
		super();
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	
}
