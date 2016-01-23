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

import br.com.guigasgame.animation.HeroAnimationsIndex;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class StatePropertiesFile {

	@XmlElement
	private Map<HeroAnimationsIndex, StateProperties> statesMap;

	protected StatePropertiesFile() {
		statesMap = new HashMap<>();
	}

	public static StatePropertiesFile loadFromFile(String filename) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(StatePropertiesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		StatePropertiesFile statePropertiesFile = ((StatePropertiesFile) jaxbUnmarshaller
				.unmarshal(new File(filename)));
		return statePropertiesFile;
	}

	public static void main(String[] args) throws JAXBException {
		
		try
		{
			StatePropertiesFile states = new StatePropertiesFile();
			StateProperties sp = new StateProperties(true, true, false, 1, 2, 3, 4);
			StateProperties sp2 = new StateProperties(false, true, true, 11, 42, 53, 64);
			states.statesMap.put(HeroAnimationsIndex.HERO_SLIDING, sp);
			states.statesMap.put(HeroAnimationsIndex.HERO_WALLGRABBING, sp);
			JAXBContext context = JAXBContext
					.newInstance(StatePropertiesFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging 
			m.marshal(states, System.out);

//			 Write to File 
//			 m.marshal(anim, new File("ninjaSmooth.xml"));
		}
		catch
		(JAXBException e)
		{
			e.printStackTrace();
		}

//		StatePropertiesFile fromFile = ((StatePropertiesFile) StatePropertiesFile.loadFromFile("oi.txt"));

	}

}
