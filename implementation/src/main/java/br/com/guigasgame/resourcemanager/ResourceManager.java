package br.com.guigasgame.resourcemanager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ResourceManager<Key, Value> {
	private Map<Key, Value> resourcesMap;

	public ResourceManager() {
		resourcesMap = new HashMap<Key, Value>();
	}
	
	public final Value getResource(Key key)
	{
		Value resource = resourcesMap.get(key);
		if (null == resource)
		{
			try {
				resource = loadResource(key);
				if (null != resource)
				{
					addResource(key, resource);
					return resource;
				}
			} catch (UnableTLoadResourceException | IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	protected final void addResource(Key key, Value resource)
	{
		resourcesMap.put(key, resource);
	}
	
	protected abstract Value loadResource(Key key) throws UnableTLoadResourceException, IOException;
}
