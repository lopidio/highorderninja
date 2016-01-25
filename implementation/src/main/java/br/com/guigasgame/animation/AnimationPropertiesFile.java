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

import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.math.Rect;
import br.com.guigasgame.resourcemanager.TextureResourceManager;


@SuppressWarnings("hiding")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
{ HeroAnimationsIndex.class, ProjectileIndex.class })
public class AnimationPropertiesFile<Enum>
{

	@XmlAttribute
	private String textureFilename;

	@XmlAttribute
	private boolean smooth;

	@XmlElement
	private Map<Enum, AnimationProperties> animationsMap;

	private Texture sharedTexture;

	/**
	 * DO NOT USE
	 * 
	 * @param textureFilename
	 */
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
		JAXBContext jaxbContext = JAXBContext.newInstance(AnimationPropertiesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		
		AnimationPropertiesFile<?> animationPropertiesFile = ((AnimationPropertiesFile<?>) jaxbUnmarshaller.unmarshal(new File(filename)));
		animationPropertiesFile.sharedTexture = TextureResourceManager.getInstance().getResource(animationPropertiesFile.textureFilename);
		animationPropertiesFile.sharedTexture.setSmooth(animationPropertiesFile.smooth);
		for( AnimationProperties animation : animationPropertiesFile.animationsMap.values() )
		{
			animation.setTexture(animationPropertiesFile.sharedTexture);
		}
		return animationPropertiesFile;
	}

	public AnimationProperties getAnimationsProperties(Enum index)
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

		AnimationPropertiesFile<HeroAnimationsIndex> anim = new AnimationPropertiesFile<>("ninja.bmp");

		AnimationProperties animationProperties = new AnimationProperties((short) 2, (short) 3, (short) 12, new Rect(1, 4, 7, 8), true);

		AnimationProperties nova = new AnimationProperties((short) 2, (short) 3,(short) 12, new Rect(1, 4, 7, 8), true);

		anim.animationsMap.put(HeroAnimationsIndex.HERO_STANDING,animationProperties);
		anim.animationsMap.put(HeroAnimationsIndex.HERO_RUNNING, nova);

		try
		{
			JAXBContext context = JAXBContext
					.newInstance(AnimationPropertiesFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			m.marshal(anim, System.out);

			// Write to File
			// m.marshal(anim, new File("ninjaSmooth.xml"));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		// @SuppressWarnings("unchecked")
		// AnimationPropertiesFile<HeroAnimationsIndex> fromFile =
		// ((AnimationPropertiesFile<HeroAnimationsIndex>)
		// AnimationPropertiesFile
		// .loadFromFile("oi.txt"));
		// System.out.println(fromFile.textureFilename);

	}

	public void setSmooth(boolean smooth)
	{
		this.smooth = smooth;
		sharedTexture.setSmooth(smooth);
	}

	public boolean isSmooth()
	{
		return smooth;
	}

}