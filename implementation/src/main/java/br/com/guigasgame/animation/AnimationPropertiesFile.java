package br.com.guigasgame.animation;

import java.io.File;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnimationPropertiesFile<E extends Enum<E>> {
	
	@XmlAttribute
	private String animationFilename;
	
	@XmlElement
	private Map<E, AnimationProperties> animationsMap;

	private AnimationPropertiesFile(String animationFilename, Map<E, AnimationProperties> animationsList) {
		super();
		this.animationFilename = animationFilename;
		this.animationsMap = animationsList;
	}
	
	public static <E extends Enum<E>> AnimationPropertiesFile<E> loadFromFile(String filename) throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(AnimationPropertiesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		@SuppressWarnings("unchecked")
		AnimationPropertiesFile<E> animationPropertiesFile = ((AnimationPropertiesFile<E>) jaxbUnmarshaller.unmarshal(new File(filename)));
		return animationPropertiesFile;
	}

	public String getAnimationFilename() {
		return animationFilename;
	}

	public AnimationProperties getAnimationsProperties(E index) {
		return animationsMap.get(index);
	}
	
	
	public Map<E, AnimationProperties> getAnimationsMap() {
		return animationsMap;
	}

	public static void main(String[] args) throws JAXBException {
//		AnimationDefinition anim = new AnimationDefinition();
//		anim.framePerSecond = 12;
//		anim.frameWidth = 42;
//		anim.numEntranceFrames = 2;
//		anim.numFrames = 7;
//		anim.textureFilename = "C:iei";
//		
//        try {
//            JAXBContext context = JAXBContext.newInstance(AnimationDefinition.class);
//            Marshaller m = context.createMarshaller();
//            //for pretty-print XML in JAXB
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
// 
//            // Write to System.out for debugging
//             m.marshal(anim, System.out);
// 
//            // Write to File
////            m.marshal(emp, new File(FILE_NAME));
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }		
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
