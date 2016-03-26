package br.com.guigasgame.reproductable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReproductableList implements Reproductable
{
	
	private List<Reproductable> children;
	
	
	public ReproductableList()
	{
		children = new ArrayList<>();
	}

	@Override
	public <T extends Reproductable> void addChild(T child)
	{
		children.add(child);
	}

	@Override
	public boolean hasChildrenToAdd()
	{
		return children.size() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Reproductable> Collection<T> getChildrenList()
	{
		return ((Collection<T>) children);
	}

	@Override
	public void clearChildrenList()
	{
		children.clear();
	}

}
