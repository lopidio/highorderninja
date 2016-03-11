package br.com.guigasgame.round.hud;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

public class HeroAttributesBarBellowHud extends HeroAttributesHud
{
	private PlayableGameHero gameHero;
	private RectangleShape fullShurikenShape;
	private RectangleShape currentShurikenShape;

	public HeroAttributesBarBellowHud(PlayableGameHero gameHero)
	{
		this.gameHero = gameHero;

		fullShurikenShape = new RectangleShape(new Vector2f(50, 4));
		fullShurikenShape.setOutlineColor(new Color(0, 0, 20, 255));
		fullShurikenShape.setFillColor(new Color(0, 20, 20, 255));
		fullShurikenShape.setOutlineThickness(2.0f);
		fullShurikenShape.setOrigin(25, 2);

		currentShurikenShape = new RectangleShape(new Vector2f(50, 4));
		currentShurikenShape.setOutlineColor(new Color(0, 0, 200, 100));
		currentShurikenShape.setFillColor(new Color(0, 200, 200, 100));
		currentShurikenShape.setOrigin(25, 2);
		
				
	}

	@Override
	public void lifeChanged(int current, int max)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shurikenNumChanged(int current, int max)
	{
		float ratio = (float)current/max;
		currentShurikenShape = new RectangleShape(new Vector2f(fullShurikenShape.getSize().x*ratio, 4));
		currentShurikenShape.setOutlineColor(new Color(0, 0, 200, 100));
		currentShurikenShape.setFillColor(new Color(0, 200, 200, 100));
		currentShurikenShape.setOrigin(currentShurikenShape.getSize().x/2, 2);
		
	}

	@Override
	public void smokeBombChanged(int current, int max)
	{
	}

	@Override
	public void update(float deltaTime)
	{
		Vec2 position = gameHero.getCollidableHero().getBody().getWorldCenter();
		Vector2f newPosition = WorldConstants.physicsToSfmlCoordinates(position.add(new Vec2(0, 3)));
		currentShurikenShape.setPosition(newPosition);
		fullShurikenShape.setPosition(newPosition);
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(fullShurikenShape);
		renderWindow.draw(currentShurikenShape);
	}

}
