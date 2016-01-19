package br.com.guigasgame.animation;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnimationDefinition 
{
    @XmlElement
	public short numFrames;
    @XmlElement
	public short numEntranceFrames;
    @XmlElement
	public short framePerSecond;
	
    @XmlAttribute
    public String textureFilename;
	
	public static AnimationDefinition loadFromFile(String filename) throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(AnimationDefinition.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		AnimationDefinition animationDefinition = (AnimationDefinition) jaxbUnmarshaller.unmarshal(new File(filename));
		return animationDefinition;
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
        AnimationDefinition fromFile = AnimationDefinition.loadFromFile("AnimationDefinition.xml");
        Animation animation = Animation.createAnimation(fromFile);
        for (int i = 0; i < 20; i++) 
        {
        	animation.update((float) 0.05);
        	System.out.println(animation.getCurrentFrameNumber());
        	System.out.println(animation.getSprite().getTextureRect());
		}
        
	}
}
