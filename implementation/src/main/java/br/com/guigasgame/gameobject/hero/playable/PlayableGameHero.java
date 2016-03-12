package br.com.guigasgame.gameobject.hero.playable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.frag.RoundFragCounter;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.action.GameHeroAction;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap;
import br.com.guigasgame.gameobject.hero.state.HeroState;
import br.com.guigasgame.gameobject.hero.state.StandingHeroState;
import br.com.guigasgame.gameobject.item.GameItem;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.shuriken.Shuriken;
import br.com.guigasgame.gameobject.projectile.smokebomb.SmokeBombProjectile;
import br.com.guigasgame.side.Side;


public class PlayableGameHero extends GameObject
{

	private Side forwardSide;
	private List<GameHeroAction> actionList;
	private CollidableHero collidableHero;
	private List<Animation> animationList;
	private GameHeroInputMap gameHeroInput;
	private final PlayableHeroDefinition heroProperties;
	private RoundFragCounter fragCounter;
	private HeroState state;

	private String lastActionName;
	private List<GameItem> gameItems;
	private RoundHeroAttributes heroAttributes;

	public PlayableGameHero(PlayableHeroDefinition properties)
	{
		this.heroProperties = properties;
		forwardSide = Side.RIGHT;
		actionList = new ArrayList<GameHeroAction>();
		collidableHero = new CollidableHero(properties.getPlayerId(), properties.getInitialPosition(), this);
		collidableHero.addListener(this);
		this.gameHeroInput = properties.getGameHeroInput();
		gameHeroInput.setDeviceId(properties.getPlayerId());
		gameItems = new ArrayList<>();
		heroAttributes = properties.getRoundHeroAttributes();

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

		updateItemsList();
		updateActionList();

		collidableHero.checkSpeedLimits(state.getMaxSpeed());
		heroAttributes.update(deltaTime);
		adjustSpritePosition();
	}

	private void updateItemsList()
	{
		for( GameItem item : gameItems )
		{
			item.acts(this);
		}
		gameItems.clear();
	}

	private void adjustSpritePosition()
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
		forwardSide = side;
		for( Animation animation : animationList )
		{
			animation.flipAnimation(side);
		}
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
		else
			System.out.println("No ammo");
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
		if (heroAttributes.getShurikens().isAbleToShoot())
		{
			heroAttributes.getShurikens().decrement(1);
			return new Shuriken(pointingDirection, new IntegerMask(), this);
		}
		return null;
	}

	public List<Animation> getAnimation()
	{
		return animationList;
	}

	public Projectile getSmokeBomb(Vec2 pointingDirection)
	{
		if (heroAttributes.getSmokeBomb().isAbleToShoot())
		{
			heroAttributes.getSmokeBomb().decrement(1);
			return new SmokeBombProjectile(pointingDirection, collidableHero.getBody().getWorldCenter(), heroProperties.getColor());
		}
		return null;		
	}

	public PlayableHeroDefinition getHeroProperties()
	{
		return heroProperties;
	}
	
	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
//		Body myBody = (Body) me;
//		Body otherBody = (Body) other;
//		float maxValue = properties.initialSpeed*otherBody.getMass();
//		System.out.println("Hero Impact: " + myBody.getLinearVelocity().sub(otherBody.getLinearVelocity()).length());
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
//		this.heroProperties.updateFragCounter(fragCounter);
	}

	public void regeneratesLife(int lifeToAdd)
	{
		heroAttributes.getLife().increment(lifeToAdd);
	}

	public void addItem(GameItem item)
	{
		gameItems.add(item);
	}

	public void refillShurikenPack()
	{
		heroAttributes.getShurikens().refill();
	}

	public void getHit(float damage)
	{
		heroAttributes.getLife().decrement(damage);
	}

	public Vector2f getMassCenter()
	{
		return WorldConstants.physicsToSfmlCoordinates(collidableHero.getBody().getWorldCenter());
	}

}
