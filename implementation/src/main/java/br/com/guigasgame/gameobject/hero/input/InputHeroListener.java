package br.com.guigasgame.gameobject.hero.input;

import br.com.guigasgame.gameobject.hero.input.GameHeroInput.HeroKey;

public interface InputHeroListener {
	public void inputReleased(HeroKey key);
	public void inputPressed(HeroKey key);
}
