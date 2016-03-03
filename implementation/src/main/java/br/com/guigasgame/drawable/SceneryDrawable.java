package br.com.guigasgame.drawable;

import java.util.List;

import org.jsfml.graphics.ConvexShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class SceneryDrawable implements Drawable 
{
	private ConvexShape convexShape;

	public SceneryDrawable(Texture texture, List<Vector2f> vertexList)
	{
		
		convexShape = new ConvexShape();
		
		// resize it
		convexShape.setPointCount(vertexList.size());

		// define the points
		int i = 0;
		for (Vector2f vertex : vertexList) 
		{
			convexShape.setPoint(i, vertex);
			++i;
		}
		convexShape.setTexture(texture, true);
		
	}

	@Override
	public void draw(RenderWindow renderWindow) 
	{
		renderWindow.draw(convexShape);
	}

}
