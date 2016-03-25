package br.com.guigasgame.destroyable;


public interface Destroyable
{
	public void markToDestroy();
	public boolean isMarkedToDestroy();
	public default void destroy()
	{
		
	}
}
