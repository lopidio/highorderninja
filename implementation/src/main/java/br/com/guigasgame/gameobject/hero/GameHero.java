package br.com.guigasgame.gameobject.hero;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jsfml.graphics.Sprite;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.state.ForwardSide;
import br.com.guigasgame.gameobject.hero.state.ForwardSide.Side;

public class GameHero extends GameObject {

	ForwardSide forwardSide;
	GameHeroLogic gameHeroLogic;
	PhysicHeroLogic physicHeroLogic;

	public GameHero() {
		super();
		forwardSide = new ForwardSide(Side.LEFT);
		gameHeroLogic = new GameHeroLogic(this); 
		physicHeroLogic = new PhysicHeroLogic(this);
	}

	@Override
	public void update(float deltaTime) {
		gameHeroLogic.update(deltaTime);
		physicHeroLogic.update(deltaTime);
		
		physicHeroLogic.checkSpeedLimits(gameHeroLogic.getState().getMaxSpeed());
		gameHeroLogic.adjustSpritePosition(WorldConstants.physicsCoordinatesToSfmlCoordinates(physicHeroLogic.getBodyPosition()),
				(float) WorldConstants.radiansToDegree(physicHeroLogic.getAngleRadians()));
	}
	
	@Override
	public BodyDef getBodyDef(Vec2 position) {
		return physicHeroLogic.getBodyDef(position);
	}	
	
	@Override
	public void attachBody(Body body) {
		super.attachBody(body);
		physicHeroLogic.attachFixturesToBody(body);
	}

	@Override
	public Sprite getSprite() {
		return gameHeroLogic.getAnimation().getSprite();
	}

	public void flipSide() {
		forwardSide.flip();
	}

	public Side getForwardSide() {
		return forwardSide.getSide();
	}

	public void applyImpulse(Vec2 impulse) {
		physicHeroLogic.applyImpulse(impulse);
	}

	public void applyForce(Vec2 force) {
		physicHeroLogic.applyForce(force);
	}

	public GameHeroLogic getGameHeroLogic() {
		return gameHeroLogic;
	}

	public PhysicHeroLogic getPhysicHeroLogic() {
		return physicHeroLogic;
	}
}
