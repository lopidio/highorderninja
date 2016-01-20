package br.com.guigasgame.gameobject.hero;

import javax.xml.bind.JAXBException;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationDefinition;
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
		gameHeroInput = new GameHeroInput();
		
		AnimationDefinition animationDefinition;
		try {
			animationDefinition = AnimationDefinition.loadFromFile("AnimationDefinition.xml");
			setState(new StandingState(Animation.createAnimation(animationDefinition), gameHero));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(float deltaTime) {
		state.updateState(deltaTime);
		animation.update(deltaTime);
		gameHeroInput.update();
	}
	public void adjustSpritePosition(Vector2f vector2f, float angleInDegrees) {
		animation.getSprite().setPosition(vector2f);
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
		animation = state.getAnimation();
		gameHeroInput.setInputListener(state);
	}

	public HeroState getState() {
		return state;
	}

}
