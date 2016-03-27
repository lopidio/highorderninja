package br.com.guigasgame.resourcemanager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.jsfml.graphics.Font;

public class FontResourceManager extends ResourceManager<String, Font>
{
	private static FontResourceManager singleton;

	static
	{
		singleton = new FontResourceManager();
	}

	private FontResourceManager()
	{
		super(new HashMap<String, Font>());
	}

	@Override
	protected Font loadResource(String key) throws IOException
	{
		Font font = new Font();
		font.loadFromFile(new File(key).toPath());
		return font;
	}

	public static FontResourceManager getInstance()
	{
		return singleton;
	}

}
