package br.com.guigasgame.animation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;

import br.com.guigasgame.animation.HeroAnimationRepository.HeroAnimationsIndex;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.resourcemanager.ResourceManager;
import br.com.guigasgame.resourcemanager.UnableTLoadResourceException;

public class HeroAnimationRepository extends ResourceManager<HeroAnimationsIndex, AnimationProperties> {

	public enum HeroAnimationsIndex {
		HERO_STANDING
	}

	HeroAnimationRepository() {
		super(new HashMap<HeroAnimationsIndex, AnimationProperties>());

		try {
			AnimationPropertiesFile<HeroAnimationsIndex> animationPropertiesFile = AnimationPropertiesFile
					.loadFromFile(FilenameConstants.getHeroAnimationFilename());
			for (Entry<HeroAnimationsIndex, AnimationProperties> animation : animationPropertiesFile.getAnimationsMap().entrySet()) {
				AnimationProperties animationProperties = animation.getValue();
				animationProperties.setTexture(animationPropertiesFile.getTexture());
				addResource(animation.getKey(), animationProperties);
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
