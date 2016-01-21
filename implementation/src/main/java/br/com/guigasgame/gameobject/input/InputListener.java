package br.com.guigasgame.gameobject.input;


public interface InputListener<T> {
	public default void inputPressed(T inputValue)
	{
		
	}
	public default void inputReleased(T inputValue)
	{
		
	}
	public default void isPressed(T inputValue)
	{
		
	}
}
