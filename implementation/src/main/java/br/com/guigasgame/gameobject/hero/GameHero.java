package br.com.guigasgame.gameobject.hero;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.action.GameHeroAction;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.hero.state.HeroState;
import br.com.guigasgame.gameobject.hero.state.StandingHeroState;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.Shuriken;
import br.com.guigasgame.side.Side;


public class GameHero extends GameObject
{

	private final int playerID; // 1 to 4

	Side forwardSide;
	List<GameHeroAction> actionList;
	CollidableHero collidableHero;
	Animation animation;
	GameHeroInputMap gameHeroInput;
	HeroState state;

	int life;
	int maxLife;
	int numShurikens;

	public GameHero(int playerID, Vec2 position, GameHeroInputMap gameHeroInput)
	{
		this.playerID = playerID;
		forwardSide = Side.RIGHT;
		actionList = new ArrayList<GameHeroAction>();
		collidableHero = new CollidableHero(playerID, position);
		this.gameHeroInput = gameHeroInput;
		gameHeroInput.setDeviceId(playerID - 1);

		collidable = collidableHero;
		collidable.addListener(this);
	}

	public int getLife()
	{
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
		life -= lifeToSubtract;
	}

	public int getNumShurikens()
	{
		return numShurikens;
	}

	public HeroState getState()
	{
		return state;
	}

	@Override
	public void onEnter()
	{
		collidableHero.loadAndAttachFixturesToBody();
		setState(new StandingHeroState(this));
	}

	@Override
	public void update(float deltaTime)
	{
		animation.update(deltaTime);

		state.stateUpdate(deltaTime);
		gameHeroInput.update(deltaTime);

		updateActionList();

		collidableHero.checkSpeedLimits(state.getMaxSpeed());
		adjustSpritePosition();
	}

	public void adjustSpritePosition()
	{
		final Vector2f vector2f = WorldConstants.physicsToSfmlCoordinates(collidableHero.getBody().getWorldCenter());
		final float angleInDegrees = (float) WorldConstants.radiansToDegrees(collidableHero.getAngleRadians());

		animation.setPosition(vector2f);
		animation.setOrientation(angleInDegrees);
	}

	private void updateActionList()
	{
		// Set action list free to new actions
		List<GameHeroAction> copy = new ArrayList<>();
		copy.addAll(actionList);
		actionList.clear();
		
		Iterator<GameHeroAction> iterator = copy.iterator();
		while (iterator.hasNext())
		{
			GameHeroAction gameHeroAction = iterator.next();
			System.out.println(gameHeroAction.getClass().getSimpleName());
			if (gameHeroAction.canExecute(this))
			{
				gameHeroAction.preExecute(this);
				gameHeroAction.execute(this);
				gameHeroAction.postExecute(this);
			}
		}
	}

	public void setState(HeroState newState)
	{
		if (null != state)
		{
			newState.getPropertyOfPreviousState(state);
			state.onQuit();
		}
		System.out.println("Current state: " + newState.getClass().getSimpleName());
		state = newState;
		state.onEnter();
		animation.flipAnimation(forwardSide);

		gameHeroInput.setInputListener(state);
	}

	public void setAnimation(Animation animation)
	{
		drawable = animation;
		this.animation = animation;
	}

	public Side getForwardSide()
	{
		return forwardSide;
	}

	public int getPlayerID()
	{
		return playerID;
	}

	public void setForwardSide(Side side)
	{
		animation.flipAnimation(side);
		forwardSide = side;
	}
	
	public boolean isTouchingGround()
	{
		return collidableHero.isTouchingGround();
	}

	public boolean isTouchingWallAhead()
	{
		return collidableHero.isTouchingWallAhead(forwardSide);
	}

	public void shoot(Projectile projectile)
	{
		addChild(projectile);
	}

	public void addAction(GameHeroAction gameHeroAction)
	{
		actionList.add(gameHeroAction);
	}

	public CollidableHero getCollidableHero()
	{
		return collidableHero;
	}

	public Projectile getNextProjectile(Vec2 pointingDirection)
	{
		return new Shuriken(pointingDirection, collidable.getBody().getWorldCenter(), playerID);
	}

	public Animation getAnimation()
	{
		return animation;
	}

}
