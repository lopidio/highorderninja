package br.com.guigasgame.reproductable;

import java.util.ArrayList;
import java.util.Collection;

public interface Reproductable
{
	<T extends Reproductable> void addChild(T child);

	public boolean hasChildrenToAdd();

	public <T extends Reproductable> Collection<T> getChildrenList();

	public void clearChildrenList();

	public default <T extends Reproductable> Collection<T> reproduce()
	{
		Collection<T> retorno = new ArrayList<>();
		if (hasChildrenToAdd())
		{
			retorno.addAll(getChildrenList());
			clearChildrenList();
		}
		return retorno;
	}

}
