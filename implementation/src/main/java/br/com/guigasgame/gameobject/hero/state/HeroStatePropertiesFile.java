package br.com.guigasgame.gameobject.hero.state;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.guigasgame.math.Vector2;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HeroStatePropertiesFile
{

	@XmlElement
	private Map<HeroStateIndex, HeroStateProperties> statesMap;

	protected HeroStatePropertiesFile()
	{
		statesMap = new HashMap<>();
	}

	public static HeroStatePropertiesFile loadFromFile(String filename)
			throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(HeroStatePropertiesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		HeroStatePropertiesFile statePropertiesFile = ((HeroStatePropertiesFile) jaxbUnmarshaller.unmarshal(new File(filename)));

		return statePropertiesFile;
	}

	public Map<HeroStateIndex, HeroStateProperties> getStatesMap()
	{
		return statesMap;
	}

	public static void newProperties() throws JAXBException
	{

		try
		{
			Map<String, Float> maps = new HashMap<>();
			maps.put("diving", 0.6f);
			maps.put("other", 0.8f);
			HeroStatePropertiesFile states = new HeroStatePropertiesFile();
			states.statesMap.put(HeroStateIndex.HERO_SLIDING, new HeroStateProperties(new HeroStateProperties.ShootXml(), new HeroStateProperties.InvincibleXml(), new HeroStateProperties.RopeXml(), new HeroStateProperties.JumpXml(-10f), new Vector2(), new HeroStateProperties.MoveXml(20f), maps));
			JAXBContext context = JAXBContext.newInstance(HeroStatePropertiesFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			m.marshal(states, System.out);

			// Write to File
			// m.marshal(anim, new File("prototype.xml"));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws JAXBException
	{
		newProperties();
		// loadFromFile("statePrototype.xml");
	}

}
