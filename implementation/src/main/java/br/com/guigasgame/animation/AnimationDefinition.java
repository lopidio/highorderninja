package br.com.guigasgame.animation;

import java.nio.file.Path;

import org.jsfml.graphics.Image;

public abstract class AnimationDefinition 
{
	public Image image;
	public short numFrames;
	public short numEntranceFrames;
	public short framePerSecond;
	public short frameWidth;
	public short frameHeight;
	
	public abstract boolean loadFromFile(Path pathToFile);
}
