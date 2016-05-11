package br.com.guigasgame.animation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.resourcemanager.FontResourceManager;


class AnimationViewer
{

	static RenderWindow renderWindow;
	static boolean isRunning = true;
	public static void main(String[] args) throws JAXBException
	{
		String animationPropertiesFile = "heroAnimationProperties.xml";
		renderWindow = new RenderWindow(new VideoMode(1000, 600, 32), "Animation Viewer");
		renderWindow.setFramerateLimit(60);

		AnimationPropertiesFile<Enum> fromFile = ((AnimationPropertiesFile<Enum>) AnimationPropertiesFile
				.loadFromFile(animationPropertiesFile));
		
		List<Text> textList = createTexts(fromFile.getAnimationsEnum());
		List<Animation> animationList = new ArrayList<Animation>();
		
		short positionX = 100;
		short positionY = 50;
		int maxHeight = 0;

		int i = 0;
		for( AnimationProperties properties : fromFile.getAnimationsMap() )
		{
			Animation novaAnimacao = Animation.createAnimation(properties);
			//Setar a posição correta

			//Desloca verticalmente
			if (positionX + novaAnimacao.getWidth() > renderWindow.getSize().x)
			{
				positionX = 100;
				positionY += maxHeight;
				maxHeight = 0;
			}
			
			novaAnimacao.move(new Vector2f(positionX, positionY));
			textList.get(i++).setPosition(new Vector2f(positionX, positionY + 50));
			
			//Desloca horizontalmente
			positionX += novaAnimacao.getWidth() + 150;
			if (novaAnimacao.getHeight() > maxHeight)
			{
				maxHeight = novaAnimacao.getHeight() + 50;
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

			i = 0;
			for( Animation animation : animationList )
			{
				renderWindow.draw(textList.get(i++));
				animation.update(deltaTime);
				animation.draw(renderWindow);
			}
			
			renderWindow.display();
		}
	}

	@SuppressWarnings("rawtypes")
	private static List<Text> createTexts(List<Enum> list)
	{
		List<Text> textList = new ArrayList<>();
		for(  Enum value : list )
		{
			Font font = FontResourceManager.getInstance().getResource(FilenameConstants.getFragStatistcsFontFilename());
			Text text = new Text();
			text.setFont(font);
			text.setCharacterSize(10);
			text.setColor(Color.GREEN);
			text.setFont(font);
			text.setString(value.name());
			text.setOrigin(text.getLocalBounds().width/2, text.getLocalBounds().height/2);
			textList.add(text);
		}
		return textList;
	}
	
	private static void handleEvents()
	{
		Iterable<Event> events = renderWindow.pollEvents();
		for( Event event : events )
		{
			if (event.type == Event.Type.KEY_PRESSED)
			{
				if (event.asKeyEvent().key == Keyboard.Key.ESCAPE)
				{
					isRunning = false;
					break;
				}
			}
			if (event.type == Event.Type.CLOSED)
			{
				isRunning = false;
			}
		}
	}
			

}
