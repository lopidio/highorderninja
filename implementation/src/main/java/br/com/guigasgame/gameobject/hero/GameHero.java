package br.com.guigasgame.gameobject.hero;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jsfml.graphics.Sprite;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.action.GameHeroAction;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.hero.state.HeroState;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.side.Side;


public class GameHero extends GameObject
{

	private final int playerID; //1 to 4

	Side forwardSide;
	List<GameHeroAction> actionList;
	GameHeroLogic gameHeroLogic;
	HeroPhysics physicHeroLogic;
	Animation animation;

	public GameHero(int playerID, Vec2 position)
	{
		super(position);
		this.playerID = playerID;
		forwardSide = Side.LEFT;
		actionList = new ArrayList<GameHeroAction>();
		gameHeroLogic = new GameHeroLogic(this);
		physicHeroLogic = new HeroPhysics(this);
	}

	@Override
	public void load()
	{
		gameHeroLogic.load();
	}

	@Override
	public void unload()
	{
		gameHeroLogic.unload();
	}

	@Override
	public void update(float deltaTime)
	{
		gameHeroLogic.update(deltaTime);
		updateActionList();

		physicHeroLogic.checkSpeedLimits(gameHeroLogic.getState().getMaxSpeed());
		gameHeroLogic.adjustSpritePosition(WorldConstants.physicsToSfmlCoordinates(physicHeroLogic.getBodyPosition()),
				(float) WorldConstants.radiansToDegrees(physicHeroLogic.getAngleRadians()));
	}

	private void updateActionList()
	{
		Iterator<GameHeroAction> iterator = actionList.iterator();
		while (iterator != null)
		{
			GameHeroAction gameHeroAction = iterator.next();
			System.out.println(gameHeroAction.getClass().getSimpleName());			
			if (gameHeroAction.canExecute())
			{
				gameHeroAction.preExecute();
				gameHeroAction.execute();
				gameHeroAction.postExecute();
			}
			iterator.remove();
		}
	}

	public void setState(HeroState heroState)
	{
		gameHeroLogic.setState(heroState);
	}

	public void setAnimation(Animation animation)
	{
		this.animation = animation;
	}
	
	@Override
	protected void editBodyDef(BodyDef bodyDef)
	{
		physicHeroLogic.editBodyDef(bodyDef);
	}
		
	public void editBody(Body body)
	{
		physicHeroLogic.loadAndAttachFixturesToBody(body);
	}

	@Override
	public Sprite getSprite()
	{
		return gameHeroLogic.getAnimation().getSprite();
	}

	public Side getForwardSide()
	{
		return forwardSide;
	}

	public void applyImpulse(Vec2 impulse)
	{
		physicHeroLogic.applyImpulse(impulse);
	}

	public void applyForce(Vec2 force)
	{
		physicHeroLogic.applyForce(force);
	}

	public GameHeroLogic getGameHeroLogic()
	{
		return gameHeroLogic;
	}

	public HeroPhysics getPhysicHeroLogic()
	{
		return physicHeroLogic;
	}

	public int getPlayerID()
	{
		return playerID;
	}

	public void setForwardSide(Side side)
	{
		forwardSide = side;
	}

	public boolean isTouchingGround()
	{
		return physicHeroLogic.isTouchingGround();
	}

	public boolean isFallingDown()
	{
		return physicHeroLogic.isFallingDown();
	}

	public boolean isAscending()
	{
		return physicHeroLogic.isAscending();
	}

	public boolean isTouchingWallAhead()
	{
		return physicHeroLogic.isTouchingWallAhead();
	}

	public void disableCollision(FixtureSensorID sensorID)
	{
		physicHeroLogic.disableCollision(sensorID);
	}

	public void enableCollision(FixtureSensorID sensorID)
	{
		physicHeroLogic.enableCollision(sensorID);
	}

	public boolean isMoving()
	{
		return physicHeroLogic.getBodyLinearVelocity().length() > WorldConstants.MOVING_TOLERANCE;
	}

	public void shoot(Projectile projectile)
	{
		addChild(projectile);
	}

}
