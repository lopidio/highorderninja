package br.com.guigasgame.gameobject.hero.attributes;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.guigasgame.file.FilenameConstants;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
//@XmlSeeAlso(
//{ HeroAttribute.class, HeroShootingAttribute.class })
public class HeroAttributesFile
{
	@XmlElement
	private final HeroAttribute life;
	@XmlElement
	private final HeroShootingAttribute shuriken;
	@XmlElement
	private final HeroShootingAttribute smokeBomb;
	
	/**
	 * DO NOT USE
	 */
	public HeroAttributesFile(HeroAttribute life, HeroShootingAttribute shuriken, HeroShootingAttribute smokeBomb)
	{
		this.life = life;
		this.shuriken = shuriken;
		this.smokeBomb = smokeBomb;
	}

	public HeroAttributesFile()
	{
		life = new HeroAttribute(100, 1);
		shuriken = new HeroShootingAttribute(10, 2, 0);
		smokeBomb = new HeroShootingAttribute(1, 3, 0.2f);
	}

	public HeroAttribute getLife()
	{
		return life;
	}

	public HeroShootingAttribute getShuriken()
	{
		return shuriken;
	}
	
	public HeroShootingAttribute getSmokeBomb()
	{
		return smokeBomb;
	}

	public static HeroAttributesFile loadFromFile(String filename) throws JAXBException 
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(HeroAttributesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		HeroAttributesFile heroAttributesFile = ((HeroAttributesFile) jaxbUnmarshaller
				.unmarshal(new File(filename)));

		return heroAttributesFile;
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			HeroAttributesFile heroAttributesFile = new HeroAttributesFile();
			
			JAXBContext context = JAXBContext.newInstance(HeroAttributesFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging 
			m.marshal(heroAttributesFile, System.out);

			// Write to File 
			m.marshal(heroAttributesFile, new File(FilenameConstants.getHeroAttributesFilename()));
		}
		catch
		(JAXBException e)
		{
			e.printStackTrace();
		}

	}	

	
}
