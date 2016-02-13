package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.MoveHeroAction;

public class Rope extends GameObject
{
	private float secondsRemaining;

	private RopeCollidable ropeCollidable;

	Rope(Vec2 position, GameHero gameHero)
	{
		ropeCollidable = new RopeCollidable(position, gameHero);
		collidable = ropeCollidable;
		secondsRemaining = 2f;
	}

	@Override
	public void update(float deltaTime)
	{
		ropeCollidable.enshort();
		secondsRemaining -= deltaTime;
		if (secondsRemaining <= 0)
		{
			markToDestroy();
		}
	}

}
