package br.com.guigasgame.resourcemanager;

import java.io.File;
import java.io.IOException;

import org.jsfml.graphics.Texture;

public class TextureResourceManager extends ResourceManager<String, Texture> {

	@Override
	public Texture loadResource(String key) throws UnableTLoadResourceException, IOException {
		Texture texture = new Texture();
		
		texture.loadFromFile(new File(key).toPath());
		return texture;
	}

}
