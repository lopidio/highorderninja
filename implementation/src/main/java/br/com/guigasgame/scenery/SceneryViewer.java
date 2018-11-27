package br.com.guigasgame.scenery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import br.com.guigasgame.scenery.creation.SceneryInitialize;
import br.com.guigasgame.scenery.file.SceneryFile;

public class SceneryViewer
{
	private SceneController scenery;
	static RenderWindow renderWindow;
	private View view;
	private boolean isRunning = true;
	private List<Shape> pointsShapes;

	public SceneryViewer() throws Exception
	{
		String scenePropertiesFile = "implementation/assets/config/alternativeScene.xml";
		SceneryInitialize creator = new SceneryInitialize(SceneryFile.loadFromFile(scenePropertiesFile));
		scenery = new SceneController(creator);
		pointsShapes = new ArrayList<>();
		initializePoints(creator);
		VideoMode[] modes = VideoMode.getFullscreenModes();
		Arrays.sort(modes, new Comparator<VideoMode>()
		{

			@Override
			public int compare(VideoMode o1, VideoMode o2)
			{
				int retorno = o1.height * o1.width - o2.height * o2.width;
				if (retorno == 0)
					return o1.bitsPerPixel - o2.bitsPerPixel;
				return retorno;
			}

		});
		final VideoMode best = modes[modes.length - 1];

		renderWindow = new RenderWindow(best, "Scene Viewer");
		renderWindow.setFramerateLimit(60);
		Vector2f viewSize = renderWindow.getView().getSize();
		view = new View(Vector2f.div(viewSize, 2), viewSize);
	}

	private void initializePoints(SceneryInitialize creator)
	{
		for( Vector2f itemSpots : creator.getItemsSpots() )
		{
			Shape circle = new CircleShape(3);
			circle.setFillColor(Color.GREEN);
			circle.setPosition(itemSpots);
			pointsShapes.add(circle);
		}

		for( Vector2f spawnPoints : creator.getSpawnPoints() )
		{
			org.jsfml.graphics.Shape shape = new org.jsfml.graphics.RectangleShape(new Vector2f(20, 40));
			shape.setPosition(spawnPoints);
			shape.setOrigin(10,20);
			shape.setFillColor(Color.RED);
			pointsShapes.add(shape);
		}
	}

	public static void main(String[] args) throws Exception
	{
		new SceneryViewer().play();
	}


	private void play()
	{
		while (isRunning)
		{
			renderWindow.clear();
			handleEvents();

			scenery.draw(renderWindow);
			renderWindow.setView(view);
			for( Shape shape : pointsShapes )
			{
				renderWindow.draw(shape);
			}
			renderWindow.display();
		}
	}

	private void handleEvents()
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
				if (event.asKeyEvent().key == Keyboard.Key.LEFT)
					view.move(-10, 0);
				if (event.asKeyEvent().key == Keyboard.Key.UP)
					view.move(0, -10);
				if (event.asKeyEvent().key == Keyboard.Key.RIGHT)
					view.move(10, 0);
				if (event.asKeyEvent().key == Keyboard.Key.DOWN)
					view.move(0, 10);

			}
			if (event.type == Event.Type.MOUSE_WHEEL_MOVED)
			{
				if (event.asMouseWheelEvent().delta > 0)// == MouseWheelEvent.WHEEL_UNIT_SCROLL)
					view.zoom(1.2f);
				if (event.asMouseWheelEvent().delta < 0)// == MouseWheelEvent.WHEEL_UNIT_SCROLL)
					view.zoom(.8f);
			}
			if (event.type == Event.Type.MOUSE_BUTTON_PRESSED)
			{
//				System.out.println(Vector2f.sub(view.getCenter(), new Vector2f(event.asMouseButtonEvent().position)));
				System.out.println(event.asMouseButtonEvent().position);
			}
			if (event.type == Event.Type.CLOSED)
			{
				isRunning = false;
			}
		}
	}
}
