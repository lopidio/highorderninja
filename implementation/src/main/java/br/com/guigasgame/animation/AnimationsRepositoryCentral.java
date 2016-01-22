package br.com.guigasgame.animation;

public class AnimationsRepositoryCentral{
	private HeroAnimationRepository heroAnimationRepository;

	private static AnimationsRepositoryCentral singleton;
	
	static
	{
		singleton = new AnimationsRepositoryCentral();
	}
	
	private AnimationsRepositoryCentral() {
		this.heroAnimationRepository = new HeroAnimationRepository();
	}

	public static HeroAnimationRepository getHeroAnimationRepository() {
		return getInstance().heroAnimationRepository;
	}
	
	private static AnimationsRepositoryCentral getInstance()
	{
		return singleton;
	}
	

}
