package br.com.guigasgame.destroyable;

import java.util.Collection;
import java.util.Iterator;

public interface Destroyable
{
	public void markToDestroy();
	public boolean isMarkedToDestroy();
	public default boolean isAlive()
	{
		return !isMarkedToDestroy();
	}
	///NEVER CALL DIRECTLY (Instead, use markToDestroy())
	default void destroy()
	{
		
	}
	
	public static <T extends Destroyable> void clearDestroyable(Collection<T> list)
	{
		Iterator<T> iterator = list.iterator();
		while (iterator.hasNext())
		{
			Destroyable toRemove = iterator.next(); // must be called before you can call iterator.remove()
			if (toRemove.isMarkedToDestroy())
			{
				toRemove.destroy();
				iterator.remove();
			}
		}
	}
}
