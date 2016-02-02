package br.com.guigasgame.gameobject.hero.action;



public interface GameHeroAction
{
	public void execute();

	default void preExecute()
	{
		
	}
	
	default void postExecute()
	{
		
	}
	
	default boolean canExecute()
	{
		return true;
	}
}
