package br.com.guigasgame.animation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.hero.state.HeroStateIndex;
import br.com.guigasgame.gameobject.item.GameItemIndex;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.math.Rect;
import br.com.guigasgame.resourcemanager.TextureResourceManager;


@SuppressWarnings("hiding")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
{ HeroStateIndex.class, ProjectileIndex.class, GameItemIndex.class })
public class AnimationPropertiesFile<Enum>
{

	@XmlAttribute
	private boolean smooth;

	@XmlElement
	private Map<Enum, AnimationProperties> animationsMap;

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
		for( AnimationProperties animation : animationPropertiesFile.animationsMap.values() )
		{
			Texture texture = TextureResourceManager.getInstance().getResource(animation.textureFilename);
			texture.setSmooth(animationPropertiesFile.smooth);
			animation.setTexture(texture);
		}
		return animationPropertiesFile;
	}

	public AnimationProperties getAnimationsProperties(Enum index)
	{
		return animationsMap.get(index);
	}

	public List<Enum> getAnimationsEnum()
	{
		List<Enum> retorno = new ArrayList<>();
		for( Entry<Enum, AnimationProperties> entry : animationsMap.entrySet() )
		{
			retorno.add(entry.getKey());
		}
		return retorno;
	}

	public Collection<AnimationProperties> getAnimationsMap()
	{
		return animationsMap.values();
	}

	public static void main(String[] args) throws JAXBException
	{

		AnimationPropertiesFile<GameItemIndex> anim = new AnimationPropertiesFile<>();

		AnimationProperties animationProperties = new AnimationProperties((short) 2, (short) 3, (short) 12, new Rect(1, 4, 7, 8), true, FilenameConstants.getItemsAnimationPropertiesFilename());

		AnimationProperties nova = new AnimationProperties((short) 2, (short) 3,(short) 12, new Rect(1, 4, 7, 8), true, FilenameConstants.getItemsAnimationPropertiesFilename());

		anim.animationsMap.put(GameItemIndex.LIFE, animationProperties);
		anim.animationsMap.put(GameItemIndex.SHURIKEN_PACK, nova);

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
			 m.marshal(anim, new File(FilenameConstants.getItemsAnimationPropertiesFilename()));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

	}

	public void setSmooth(boolean smooth)
	{
		this.smooth = smooth;
		for( AnimationProperties animationProperties : animationsMap.values() )
		{
			animationProperties.texture.setSmooth(smooth);
		}
	}

	public boolean isSmooth()
	{
		return smooth;
	}

}
