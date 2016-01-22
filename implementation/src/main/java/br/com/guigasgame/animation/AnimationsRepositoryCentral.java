package br.com.guigasgame.animation;

import javax.xml.bind.annotation.XmlEnum;

public class AnimationsRepositoryCentral
{
	@XmlEnum
	public enum HeroAnimationsIndex {
		HERO_STANDING,
		HERO_CAGANDO
	}
//	private AnimationPropertiesFile<HeroAnimationsIndex> heroAnimations;

	private static AnimationsRepositoryCentral singleton;
	
	static
	{
		singleton = new AnimationsRepositoryCentral();
	}
	
	private AnimationsRepositoryCentral() {
		initializeHeroAnimations();
	}

//	public static AnimationPropertiesFile<HeroAnimationsIndex> getHeroAnimationRepository() {
//		return getInstance().heroAnimations;
//	}
//	
	private static AnimationsRepositoryCentral getInstance()
	{
		return singleton;
	}
	

	private void initializeHeroAnimations() {
//		this.heroAnimations = new AnimationPropertiesFile<HeroAnimationsIndex>(FilenameConstants.getHeroAnimationFilename());
//		
//		try {
//			heroAnimations = AnimationPropertiesFile.loadFromFile(FilenameConstants.getHeroAnimationFilename());
//			
//			for (Entry<HeroAnimationsIndex, AnimationProperties> animation : heroAnimations.getAnimationsMap().entrySet()) {
//				AnimationProperties animationProperties = animation.getValue();
//				animationProperties.setTexture(heroAnimations.getTexture());
//			}
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
