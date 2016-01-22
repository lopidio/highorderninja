package br.com.guigasgame.animation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.resourcemanager.ResourceManager;
import br.com.guigasgame.resourcemanager.UnableTLoadResourceException;

public class HeroAnimationRepository extends ResourceManager<HeroAnimationsIndex, AnimationProperties> {

	HeroAnimationRepository() {
		super(new HashMap<HeroAnimationsIndex, AnimationProperties>());

		try {
			AnimationPropertiesFile<HeroAnimationsIndex> animationPropertiesFile = AnimationPropertiesFile
					.loadFromFile(FilenameConstants.getHeroAnimationFilename());
			for (Entry<HeroAnimationsIndex, AnimationProperties> animation : animationPropertiesFile.getAnimationsMap().entrySet()) {
				addResource(animation.getKey(), animation.getValue());
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected AnimationProperties loadResource(HeroAnimationsIndex key)
			throws UnableTLoadResourceException, IOException {
		throw new UnableTLoadResourceException();
	}

}
