package br.com.guigasgame.resourcemanager;

import java.io.IOException;
import java.util.Map;


public abstract class ResourceManager<Key, Resource>
{

	private Map<Key, Resource> resourcesMap;

	public ResourceManager(Map<Key, Resource> resourcesMap)
	{
		this.resourcesMap = resourcesMap;
	}

	public final Resource getResource(Key key)
	{
		Resource resource = resourcesMap.get(key);
		if (null == resource)
		{
			try
			{
				resource = loadResource(key);
				if (null != resource)
				{
					addResource(key, resource);
					return resource;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return resource;
	}

	protected final void addResource(Key key, Resource resource)
	{
		resourcesMap.put(key, resource);
		System.out.println(resource.toString() + ": " + resourcesMap.size());
	}

	protected abstract Resource loadResource(Key key) throws IOException;
}
