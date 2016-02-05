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
import br.com.guigasgame.gameobject.hero.state.HeroState;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.Shuriken;
import br.com.guigasgame.side.Side;


public class GameHero extends GameObject
{

	private final int playerID; //1 to 4

	Side forwardSide;
	List<GameHeroAction> actionList;
	GameHeroLogic gameHeroLogic;
	CollidableHero collidableHero;
	Animation animation;

	public GameHero(int playerID, Vec2 position)
	{
		this.playerID = playerID;
		forwardSide = Side.RIGHT;
		actionList = new ArrayList<GameHeroAction>();
		gameHeroLogic = new GameHeroLogic(this);
		collidableHero = new CollidableHero(playerID, position);
		
		collidable = collidableHero;
		collidable.addListener(this);
	}

	@Override
	public void onEnter()
	{
		collidableHero.loadAndAttachFixturesToBody();
		gameHeroLogic.onEnter();
	}

	@Override
	public void update(float deltaTime)
	{
		animation.update(deltaTime);
		
		gameHeroLogic.update(deltaTime);
		updateActionList();

		collidableHero.checkSpeedLimits(gameHeroLogic.getState().getMaxSpeed());
		adjustSpritePosition();
	}

	public void adjustSpritePosition()
	{
		final Vector2f vector2f = WorldConstants.physicsToSfmlCoordinates(collidableHero.getBodyPosition());
		final float angleInDegrees = (float) WorldConstants.radiansToDegrees(collidableHero.getAngleRadians());
				
		animation.setPosition(vector2f);
		animation.setOrientation(angleInDegrees);
	}	
	
	private void updateActionList()
	{
		//Set action list free to new actions
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

	public void setState(HeroState heroState)
	{
		animation.flipAnimation(forwardSide);		
		gameHeroLogic.setState(heroState);
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
