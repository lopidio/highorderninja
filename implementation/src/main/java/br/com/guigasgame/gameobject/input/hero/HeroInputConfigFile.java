package br.com.guigasgame.gameobject.input.hero;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jsfml.window.Keyboard.Key;

import br.com.guigasgame.gameobject.input.InputMapController;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;

@XmlRootElement
public class HeroInputConfigFile {

	@XmlAttribute
	public int playerID;

	@XmlElement
	public Map<HeroInputKey, InputMapController<HeroInputKey>> map;

	public HeroInputConfigFile() {
		map = new HashMap<>();
	}
	
	public static void main(String[] args) {
		HeroInputConfigFile anim = new HeroInputConfigFile();
		anim.map = new HashMap<HeroInputKey, InputMapController<HeroInputKey>>();
		anim.playerID = 5;
		anim.map.put(HeroInputKey.LEFT, InputMapController.createKeyboardEvent(Key.SPACE, null, HeroInputKey.LEFT));
		anim.map.put(HeroInputKey.JUMP, InputMapController.createKeyboardEvent(Key.UP, null, HeroInputKey.JUMP));
		
        try {
            JAXBContext context = JAXBContext.newInstance(HeroInputConfigFile.class);
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
        
	}
	
}
