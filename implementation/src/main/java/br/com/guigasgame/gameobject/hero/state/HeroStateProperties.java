package br.com.guigasgame.gameobject.hero.state;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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
	public final Map<String, Float> property;
	@XmlElement(required=true)
	public final Vector2 maxSpeed;
	
	public HeroStateProperties(ShootXml shoot, RopeXml rope, JumpXml jump, Vector2 maxSpeed, MoveXml move, Map<String, Float> property)
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
		this.property = new HashMap<>();
		this.maxSpeed = new Vector2();
	}

	public void printOut()
	{
		// Write to System.out for debugging 
		try
		{
			JAXBContext context = JAXBContext.newInstance(HeroStatePropertiesFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
			// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(this, System.out);
		}
		catch (JAXBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
