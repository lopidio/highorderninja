package br.com.guigasgame.animation;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnimationPropertiesFile {
	
	@XmlAttribute
	private String animationFilename;
	
	@XmlElement
	private List<AnimationProperties> animationsList;

	private AnimationPropertiesFile(String animationFilename, List<AnimationProperties> animationsList) {
		super();
		this.animationFilename = animationFilename;
		this.animationsList = animationsList;
	}
	
	public static AnimationPropertiesFile loadFromFile(String filename) throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(AnimationPropertiesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		AnimationPropertiesFile animationPropertiesFile = (AnimationPropertiesFile) jaxbUnmarshaller.unmarshal(new File(filename));
		return animationPropertiesFile;
	}

	public String getAnimationFilename() {
		return animationFilename;
	}

	public List<AnimationProperties> getAnimationsList() {
		return animationsList;
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
