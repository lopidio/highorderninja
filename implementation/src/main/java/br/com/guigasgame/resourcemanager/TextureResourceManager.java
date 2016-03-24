package br.com.guigasgame.resourcemanager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.jsfml.graphics.Texture;


public class TextureResourceManager extends ResourceManager<String, Texture>
{

	private static TextureResourceManager singleton;

	static
	{
		singleton = new TextureResourceManager();
	}

	private TextureResourceManager()
	{
		super(new HashMap<String, Texture>());
	}

	@Override
	protected Texture loadResource(String key)
			throws IOException
	{
		Texture texture = new Texture();
		texture.loadFromFile(new File(key).toPath());
		return texture;
	}

	public static TextureResourceManager getInstance()
	{
		return singleton;
	}

}
