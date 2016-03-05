package br.com.guigasgame.gameobject.hero;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.frag.RoundFragCounter;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.action.GameHeroAction;
import br.com.guigasgame.gameobject.hero.state.HeroState;
import br.com.guigasgame.gameobject.hero.state.StandingHeroState;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.shuriken.Shuriken;
import br.com.guigasgame.gameobject.projectile.smokebomb.SmokeBombProjectile;
import br.com.guigasgame.side.Side;


public class RoundGameHero extends GameObject
{

	private Side forwardSide;
	private List<GameHeroAction> actionList;
	private CollidableHero collidableHero;
	private List<Animation> animationList;
	private GameHeroInputMap gameHeroInput;
	private final GameHeroProperties heroProperties;
	private RoundFragCounter fragCounter;
	private HeroState state;

	private String lastActionName;

	public RoundGameHero(GameHeroProperties properties)
	{
		this.heroProperties = properties;
		forwardSide = Side.RIGHT;
		actionList = new ArrayList<GameHeroAction>();
		collidableHero = new CollidableHero(properties.getPlayerId(), properties.getInitialPosition());
		collidableHero.addListener(this);
		this.gameHeroInput = properties.getGameHeroInput();
		gameHeroInput.setDeviceId(properties.getPlayerId());

		collidableHero.addListener(this);
		collidableList.add(collidableHero);
		animationList = new ArrayList<>();
		fragCounter = new RoundFragCounter();
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
		for( Animation animation : animationList )
		{
			animation.update(deltaTime);
		}

		state.update(deltaTime);
		gameHeroInput.update(deltaTime);

		updateActionList();

		collidableHero.checkSpeedLimits(state.getMaxSpeed());
		adjustSpritePosition();
	}

	public void adjustSpritePosition()
	{
		final Vector2f vector2f = WorldConstants.physicsToSfmlCoordinates(collidableHero.getBody().getWorldCenter());
		final float angleInDegrees = (float) WorldConstants.radiansToDegrees(collidableHero.getAngleRadians());

		
		for( Animation animation : animationList )
		{
			animation.setPosition(vector2f);
			animation.setOrientation(angleInDegrees);
		}

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
			String currentActionName = gameHeroAction.getClass().getSimpleName();
			if (!currentActionName.equals(lastActionName))
			{
				System.out.println("("+heroProperties.getPlayerId()+") " + currentActionName);
			}
			lastActionName = currentActionName;
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
			state.onQuit();
		}
		if (drawableList.size() > 0)
			drawableList.remove(0);
		System.out.println("\t("+heroProperties.getPlayerId()+") State: " + newState.getClass().getSimpleName());
		state = newState;
		state.onEnter();

		for( Animation animation : animationList )
		{
			animation.flipAnimation(forwardSide);
		}

		gameHeroInput.setInputListener(state);
	}

	public void setAnimationList(List<Animation> animationList)
	{
		this.animationList = animationList;
		drawableList.clear();
		for( Animation animation : animationList )
		{
			animation.setColor(heroProperties.getColor());
			drawableList.add(animation);
		}
	}

	public Side getForwardSide()
	{
		return forwardSide;
	}

	public void setForwardSide(Side side)
	{
		for( Animation animation : animationList )
		{
			animation.flipAnimation(side);
		}
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
		if (projectile != null)
		{
			fragCounter.incrementShoots();
			addChild(projectile);
		}
	}

	public void addAction(GameHeroAction gameHeroAction)
	{
		actionList.add(gameHeroAction);
	}

	public CollidableHero getCollidableHero()
	{
		return collidableHero;
	}

	public Projectile getShuriken(Vec2 pointingDirection)
	{
		return new Shuriken(pointingDirection, new IntegerMask(), this);
	}

	public List<Animation> getAnimation()
	{
		return animationList;
	}

	public Projectile getItem(Vec2 pointingDirection)
	{
		return new SmokeBombProjectile(pointingDirection, collidableHero.getBody().getWorldCenter(), heroProperties.getColor());
	}

	public GameHeroProperties getHeroProperties()
	{
		return heroProperties;
	}
	
	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		Body myBody = (Body) me;
		Body otherBody = (Body) other;
//		float maxValue = properties.initialSpeed*otherBody.getMass();
		System.out.println("Hero Impact: " + myBody.getLinearVelocity().sub(otherBody.getLinearVelocity()).length());
	}

	public void hitOnTarget()
	{
		fragCounter.incrementShootsOnTarget();
	}

	
	public RoundFragCounter getFragCounter()
	{
		return fragCounter;
	}
	
	@Override
	public void onDestroy()
	{
		this.heroProperties.updateFragCounter(fragCounter);
	}
}
