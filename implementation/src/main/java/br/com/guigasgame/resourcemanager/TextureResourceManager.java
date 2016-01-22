package br.com.guigasgame.resourcemanager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.jsfml.graphics.Texture;

public class TextureResourceManager extends ResourceManager<String, Texture> {

	public TextureResourceManager() {
		super(new HashMap<String, Texture>());
	}

	@Override
	public Texture loadResource(String key) throws UnableTLoadResourceException, IOException {
		Texture texture = new Texture();
		
		texture.loadFromFile(new File(key).toPath());
		return texture;
	}

}
