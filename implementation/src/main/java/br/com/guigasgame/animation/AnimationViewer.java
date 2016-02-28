package br.com.guigasgame.animation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;


class AnimationViewer
{

	static RenderWindow renderWindow;
	static boolean isRunning = true;
	public static void main(String[] args) throws JAXBException
	{
		String animationPropertiesFile = "projectilesAnimation.xml";
		renderWindow = new RenderWindow(new VideoMode(1000, 600, 32), "Test");
		renderWindow.setFramerateLimit(60);

		AnimationPropertiesFile<?> fromFile = ((AnimationPropertiesFile<?>) AnimationPropertiesFile
				.loadFromFile(animationPropertiesFile));
		
		List<Animation> animationList = new ArrayList<Animation>();
		
		short positionX = 0;
		short positionY = 0;
		int maxHeight = 0;
		
		for( AnimationProperties properties : fromFile.getAnimationsMap() )
		{
			Animation novaAnimacao = Animation.createAnimation(properties);
			//Setar a posição correta

			//Desloca verticalmente
			if (positionX + novaAnimacao.getWidth() > renderWindow.getSize().x)
			{
				positionY += maxHeight;
				maxHeight = 0;
			}
			
			novaAnimacao.setPosition(new Vector2f(positionX, positionY));
			
			//Desloca horizontalmente
			positionX += novaAnimacao.getWidth() + 20;
			if (novaAnimacao.getHeight() > maxHeight)
			{
				maxHeight = novaAnimacao.getHeight();
			}
			
			animationList.add(novaAnimacao);
		}
		
		Clock clock = new Clock();
		
		while (isRunning)
		{
			float deltaTime = clock.getElapsedTime().asSeconds();
			clock.restart();
			renderWindow.clear();
			handleEvents();

			for( Animation animation : animationList )
			{
				animation.update(deltaTime);
				animation.draw(renderWindow);
			}
			
			renderWindow.display();
		}
	}
	
	private static void handleEvents()
	{
		Iterable<Event> events = renderWindow.pollEvents();
		for( Event event : events )
		{
			if (event.type == Event.Type.KEY_PRESSED)
			{
				if (event.asKeyEvent().key != Keyboard.Key.ESCAPE) break;
				isRunning = false;
			}
			if (event.type == Event.Type.CLOSED)
			{
				isRunning = false;
			}
		}
	}
			

}
