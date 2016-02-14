package br.com.guigasgame.gameobject.hero.state;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.guigasgame.math.Vector2;

@XmlRootElement
public class HeroStateProperties
{
	
	public static class JumpXml
	{
		@XmlAttribute(required=true)
		public final float impulse;

		public JumpXml(float impulse)
		{
			this.impulse = impulse;
		}
		public JumpXml()
		{
			this.impulse = 0;
		}
	}

	public static class MoveXml
	{
		@XmlAttribute(required=true)
		public final float acceleration;
		public MoveXml(float acceleration)
		{
			this.acceleration = acceleration;
		}
		public MoveXml()
		{
			this.acceleration = 0;
		}		
	}	
	

	public static class PropertyXml
	{
		@XmlAttribute(required=true)
		public final float value;

		@XmlAttribute(required=true)
		public final String name;

		public PropertyXml(float value, String name)
		{
			this.value = value;
			this.name = name;
		}

		public PropertyXml()
		{
			this.value = 0;
			this.name = "";
		}
		
	}	
	
	public static class ShootXml{}	
	public static class RopeXml{}	
	
	@XmlElement
	public final ShootXml shoot;
	@XmlElement
	public final RopeXml rope;
	@XmlElement
	public final JumpXml jump;
	@XmlElement
	public final MoveXml move;
	@XmlElement
	public final List<PropertyXml> property;
	@XmlElement(required=true)
	public final Vector2 maxSpeed;
	
	public HeroStateProperties(ShootXml shoot, RopeXml rope, JumpXml jump, Vector2 maxSpeed, MoveXml move, List<PropertyXml> property)
	{
		super();
		this.shoot = shoot;
		this.rope = rope;
		this.jump = jump;
		this.maxSpeed = maxSpeed;
		this.move = move;
		this.property = property;
	}
	
	public HeroStateProperties()
	{
		this.shoot = null;
		this.rope = null;
		this.jump = null;
		this.move = null;
		this.property = null;
		this.maxSpeed = new Vector2();
	}
}
