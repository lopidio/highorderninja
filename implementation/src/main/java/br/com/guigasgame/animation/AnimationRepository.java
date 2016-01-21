package br.com.guigasgame.animation;

import java.io.IOException;

import javafx.util.Pair;

import javax.xml.bind.JAXBException;

import br.com.guigasgame.resourcemanager.ResourceManager;
import br.com.guigasgame.resourcemanager.UnableTLoadResourceException;

public class AnimationRepository extends ResourceManager<Pair<String, AnimationsIndex>, AnimationProperties> {

	@Override
	public AnimationProperties loadResource(Pair<String, AnimationsIndex> key) throws UnableTLoadResourceException, IOException {
		
		try {
			AnimationPropertiesFile animationPropertiesFile = AnimationPropertiesFile.loadFromFile(key.getKey()) ;
			for (AnimationProperties animationProperties : animationPropertiesFile.getAnimationsList()) {
				addResource(key, resource);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new UnableTLoadResourceException();
		}
		
		return null;
//		JAXBContext jaxbContext = JAXBContext.newInstance(AnimationProperties.class);
//		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//		AnimationProperties animationDefinition = (AnimationProperties) jaxbUnmarshaller.unmarshal(new File(filename));
//		return animationDefinition;
	}

}
