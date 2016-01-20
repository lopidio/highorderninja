package br.com.guigasgame.gameobject.hero;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.hero.input.GameHeroInput;
import br.com.guigasgame.gameobject.hero.state.HeroState;
import br.com.guigasgame.gameobject.hero.state.StandingState;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class GameHeroLogic implements UpdatableFromTime{

	GameHero gameHero;
	GameHeroInput gameHeroInput;
	HeroState state;
	Animation animation;
	int life;
	int maxLife;
	int numShurickens;

	public GameHeroLogic(GameHero gameHero) {
		this.gameHero = gameHero;
		setState(new StandingState());
	}

	@Override
	public void update(float deltaTime) {
		state.updateState(deltaTime);
		animation.update(deltaTime);

	}
	public void adjustSpritePosition(Vector2f vector2f, float angleInDegrees) {
		animation.getSprite().setPosition(WorldConstants.SCALE * vector2f.x,
				WorldConstants.SCALE * vector2f.y);
		animation.getSprite().setRotation(angleInDegrees);
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public Animation getAnimation() {
		return animation;
	}

	public int getLife() {
		return life;
	}
	
	public void addLife(int lifeToAdd)
	{
		life += lifeToAdd;
		if (life > maxLife)
			life = maxLife;
	}

	public void hit(int lifeToSubtract)
	{
		life = lifeToSubtract;
	}
	
	public int getNumShurickens() {
		return numShurickens;
	}

	public void addState(HeroState newState) {
		newState.setPreviousState(state);
		setState(newState);
	}

	public void setState(HeroState newState) {
		state = newState;
		gameHeroInput.setInputListener(state);
	}

	public HeroState getState() {
		return state;
	}

}
