package br.com.guigasgame.gameobject.hero;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Sprite;

import br.com.guigasgame.box2d.debug.WorldManager;
import br.com.guigasgame.gamehero.HeroSensorsController;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.state.ForwardSide;

public class GameHero extends GameObject {

	ForwardSide forwardSide;
	HeroSensorsController sensorsController;
	GameHeroLogic gameHeroLogic;
	PhysicHeroLogic physicHeroLogic;

	public GameHero() {
		super();
		forwardSide = ForwardSide.LEFT;
		sensorsController = new HeroSensorsController(this);
		gameHeroLogic = new GameHeroLogic(this); 
	}

	@Override
	public void update(float deltaTime) {
		gameHeroLogic.update(deltaTime);
		checkSpeedLimits(gameHeroLogic.getState().getMaxSpeed());
		gameHeroLogic.adjustSpritePosition(WorldManager.physicsCoordinatesToSfmlCoordinates(physicHeroLogic.getBodyPosition()),
				(float) WorldManager.radiansToDegree(physicHeroLogic.getAngleRadians()));
	}

	@Override
	public Sprite getSprite() {
		return gameHeroLogic.getAnimation().getSprite();
	}

	public void flipSide() {
		if (forwardSide == ForwardSide.LEFT)
			forwardSide = ForwardSide.RIGHT;
		else
			// if (forwardSide == ForwardSide.LEFT)
			forwardSide = ForwardSide.LEFT;
	}

	public ForwardSide getForwardSide() {
		return forwardSide;
	}

	public final HeroSensorsController getSensorsController() {
		return sensorsController;
	}

	public void applyImpulse(Vec2 impulse) {
		physicHeroLogic.applyImpulse(impulse);
	}

	public void applyForce(Vec2 force) {
		physicHeroLogic.applyForce(force);
	}

	
	private void checkSpeedLimits(Vec2 maxSpeed) {
		physicHeroLogic.checkSpeedLimits(maxSpeed);
	}

}
