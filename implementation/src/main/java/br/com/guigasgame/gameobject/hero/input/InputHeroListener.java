package br.com.guigasgame.gameobject.hero.input;

import br.com.guigasgame.gameobject.hero.input.GameHeroInput.HeroInputKey;

public interface InputHeroListener {
	public default void inputPressed(HeroInputKey key)
	{
		
	}
	public default void inputReleased(HeroInputKey key)
	{
		
	}
	public default void isPressed(HeroInputKey key)
	{
		
	}
	public default void isReleased(HeroInputKey key)
	{
		
	}
}
