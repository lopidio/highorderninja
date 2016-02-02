package br.com.guigasgame.composite;

import java.util.Collection;


public interface Composible<T>
{

	void addChild(T child);

	public boolean hasChildrenToAdd();

	public Collection<T> getChildrenList();

	public void clearChildren();
	
}
