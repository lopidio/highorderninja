package br.com.guigasgame.animation;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.jsfml.graphics.Texture;

import br.com.guigasgame.animation.AnimationsRepositoryCentral.HeroAnimationsIndex;
import br.com.guigasgame.resourcemanager.TextureResourceManager;


@SuppressWarnings("hiding")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
{ HeroAnimationsIndex.class })
public class AnimationPropertiesFile<Enum>
{

	@XmlAttribute
	private String textureFilename;

	@XmlElement
	private Map<Enum, AnimationProperties> animationsMap;

	private Texture sharedTexture;

	AnimationPropertiesFile(String textureFilename)
	{
		this.textureFilename = textureFilename;
		animationsMap = new HashMap<>();
	}

	/**
	 * DO NOT USE
	 */
	AnimationPropertiesFile()
	{
		animationsMap = new HashMap<>();
	}

	public static AnimationPropertiesFile<?> loadFromFile(String filename)
			throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext
				.newInstance(AnimationPropertiesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		AnimationPropertiesFile<?> animationPropertiesFile = ((AnimationPropertiesFile<?>) jaxbUnmarshaller
				.unmarshal(new File(filename)));
		animationPropertiesFile.sharedTexture = TextureResourceManager
				.getInstance().getResource(
						animationPropertiesFile.textureFilename);
		return animationPropertiesFile;
	}

	public AnimationProperties getAnimationsProperties(HeroAnimationsIndex index)
	{
		return animationsMap.get(index);
	}

	public Collection<AnimationProperties> getAnimationsMap()
	{
		return animationsMap.values();
	}

	public Texture getSharedTexture()
	{
		return sharedTexture;
	}

	public static void main(String[] args) throws JAXBException
	{

		AnimationPropertiesFile<HeroAnimationsIndex> anim = new AnimationPropertiesFile<>(
				"Model.png");
		AnimationProperties animationProperties = new AnimationProperties(
				(short) 2, (short) 3, (short) 12, (short) 0, (short) 4,
				(short) 5, (short) 6, true);

		AnimationProperties nova = new AnimationProperties((short) 2,
				(short) 3, (short) 12, (short) 0, (short) 4, (short) 5,
				(short) 6, true);

		anim.animationsMap.put(HeroAnimationsIndex.HERO_STANDING,
				animationProperties);
		anim.animationsMap.put(HeroAnimationsIndex.HERO_CAGANDO, nova);

		try
		{
			JAXBContext context = JAXBContext
					.newInstance(AnimationPropertiesFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging m.marshal(anim, System.out);

//			 Write to File 
			 m.marshal(anim, new File("oi.txt"));
		}
		catch
		(JAXBException e)
		{
			e.printStackTrace();
		}

		@SuppressWarnings("unchecked")
		AnimationPropertiesFile<HeroAnimationsIndex> fromFile = ((AnimationPropertiesFile<HeroAnimationsIndex>) AnimationPropertiesFile
				.loadFromFile("oi.txt"));
		System.out.println(fromFile.textureFilename);

	}

}
