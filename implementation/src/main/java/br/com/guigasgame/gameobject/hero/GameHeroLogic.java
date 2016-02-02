package br.com.guigasgame.gameobject.hero;

import br.com.guigasgame.gameobject.hero.state.FallingHeroState;
import br.com.guigasgame.gameobject.hero.state.HeroState;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap;
import br.com.guigasgame.updatable.UpdatableFromTime;


class GameHeroLogic implements UpdatableFromTime
{

	GameHero gameHero;
	GameHeroInputMap gameHeroInput;
	HeroState state;
	int life;
	int maxLife;
	int numShurickens;

	public GameHeroLogic(GameHero gameHero)
	{
		this.gameHero = gameHero;
	}

	@Override
	public void update(float deltaTime)
	{
		state.updateState(deltaTime);
		gameHeroInput.update();
	}


	public int getLife()
	{
		return life;
	}

	public void addLife(int lifeToAdd)
	{
		life += lifeToAdd;
		if (life > maxLife) life = maxLife;
	}

	public void hit(int lifeToSubtract)
	{
		life = lifeToSubtract;
	}

	public int getNumShurickens()
	{
		return numShurickens;
	}

	public void setState(HeroState newState)
	{
		if (null != state) state.onQuit();
		System.out.println(	"Current state: " + newState.getClass().getSimpleName());
		state = newState;
		state.onEnter();
		//animation = state.getAnimation();
		gameHeroInput.setInputListener(state);
	}

	public HeroState getState()
	{
		return state;
	}

	public void load()
	{
		gameHeroInput = GameHeroInputMap.loadFromConfigFile(gameHero.getPlayerID());

		setState(new FallingHeroState(gameHero));
	}

	public void unload()
	{
		// TODO Auto-generated method stub

	}

}
