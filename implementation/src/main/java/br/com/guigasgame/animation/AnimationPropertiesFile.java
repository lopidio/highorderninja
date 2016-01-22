package br.com.guigasgame.animation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;

import br.com.guigasgame.resourcemanager.TextureResourceManager;

@XmlRootElement
public class AnimationPropertiesFile<E extends Enum<E>> {
	
	@XmlAttribute
	private String textureFilename;
	
	@XmlElement
	private Map<E, AnimationProperties> animationsMap;

	private Texture texture;
	
	AnimationPropertiesFile() {
		animationsMap = new HashMap<>();
	}
	
	public static <E extends Enum<E>> AnimationPropertiesFile<E> loadFromFile(String filename) throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(AnimationPropertiesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		@SuppressWarnings("unchecked")
		AnimationPropertiesFile<E> animationPropertiesFile = ((AnimationPropertiesFile<E>) jaxbUnmarshaller.unmarshal(new File(filename)));
		animationPropertiesFile.texture = TextureResourceManager.getInstance().getResource(animationPropertiesFile.textureFilename);
		return animationPropertiesFile;
	}

	public AnimationProperties getAnimationsProperties(E index) {
		return animationsMap.get(index);
	}
	
	
	public Map<E, AnimationProperties> getAnimationsMap() {
		return animationsMap;
	}
	
	public Texture getTexture() {
		return texture;
	}

	public static void main(String[] args) throws JAXBException {
		AnimationPropertiesFile anim = new AnimationPropertiesFile<>();
		anim.textureFilename = "asd";
		AnimationProperties animationProperties = new AnimationProperties(HeroAnimationsIndex.HERO_STANDING, (short)2, (short)3, (short)12,
			new IntRect(0, 4, 5, 6),true);

		
		anim.animationsMap.put(HeroAnimationsIndex.HERO_STANDING, animationProperties);
		
        try {
            JAXBContext context = JAXBContext.newInstance(AnimationPropertiesFile.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            // Write to System.out for debugging
             m.marshal(anim, System.out);
 
            // Write to File
//            m.marshal(emp, new File(FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }		
//        
//		AnimationPropertiesFile fromFile = AnimationPropertiesFile.loadFromFile("AnimationDefinition.xml");
//		AnimationPropertiesFile animation = AnimationPropertiesFile.createAnimation(fromFile);
//        for (int i = 0; i < 20; i++) 
//        {
//        	animation.update((float) 0.05);
//        	System.out.println(animation.getCurrentFrameNumber());
//        	System.out.println(animation.getSprite().getTextureRect());
//		}
        
	}
	
}
