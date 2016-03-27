package br.com.guigasgame.gameobject.hero.playable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.frag.HeroFragStatistic;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.action.GameHeroAction;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttributeListener;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.hero.state.HeroState;
import br.com.guigasgame.gameobject.hero.state.StandingHeroState;
import br.com.guigasgame.gameobject.item.GameItem;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.shuriken.Shuriken;
import br.com.guigasgame.gameobject.projectile.smokebomb.SmokeBombProjectile;
import br.com.guigasgame.side.Side;


public class PlayableGameHero extends GameObject
		implements HeroAttributeListener
{

	private Side forwardSide;
	private List<GameHeroAction> actionList;
	private CollidableHero collidableHero;
	private List<Animation> animationList;
	private GameHeroInputMap gameHeroInput;
	private final PlayableHeroDefinition heroProperties;
	private HeroFragStatistic fragStatistic;
	private HeroState state;

	private String lastActionName;
	private List<GameItem> gameItems;
	private RoundHeroAttributes heroAttributes;
	private boolean invincible;
	private boolean playerIsDead;

	public PlayableGameHero(PlayableHeroDefinition properties)
	{
		this.heroProperties = properties;
		playerIsDead = false;
		forwardSide = Side.RIGHT;
		actionList = new ArrayList<GameHeroAction>();
		collidableHero = new CollidableHero(properties.getPlayerId(), properties.getInitialPosition(), this);
		collidableHero.addListener(this);
		this.gameHeroInput = properties.getGameHeroInput();
		gameHeroInput.setDeviceId(properties.getPlayerId());
		gameItems = new ArrayList<>();
		heroAttributes = properties.getRoundHeroAttributes();
		heroAttributes.getLife().addListener(this);

		collidableList.add(collidableHero);
		animationList = new ArrayList<>();
		fragStatistic = new HeroFragStatistic();
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
		updateActionList();
		collidableHero.checkSpeedLimits(state.getMaxSpeed());
		if (!playerIsDead)
		{
			gameHeroInput.update(deltaTime);

			updateItemsList();

			heroAttributes.update(deltaTime);
		}
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
			animation.setRotation(angleInDegrees);
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
				System.out.println("("
						+ heroProperties.getPlayerId() + ") "
						+ currentActionName);
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
		System.out.println("\t("
				+ heroProperties.getPlayerId() + ") State: "
				+ newState.getClass().getSimpleName());
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
		if (heroAttributes.getShurikens().isAbleToShoot())
		{
			fragStatistic.incrementShoots();
			heroAttributes.getShurikens().decrement(1);
			return new Shuriken(pointingDirection, this);
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
			return new SmokeBombProjectile(pointingDirection, this);
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
		// Body myBody = (Body) me;
		// Body otherBody = (Body) other;
		// float maxValue = properties.initialSpeed*otherBody.getMass();
		// System.out.println("Hero Impact: " +
		// myBody.getLinearVelocity().sub(otherBody.getLinearVelocity()).length());
	}

	public void hitOnTarget()
	{
		fragStatistic.incrementShootsOnTarget();
	}

	public HeroFragStatistic getFragStatistic()
	{
		return fragStatistic;
	}

	@Override
	public void gotEmpty(HeroAttribute heroAttribute)
	{
		die();
	}

	@Override
	public void onDestroy()
	{
		state.onQuit();
		// this.heroProperties.updateFragCounter(fragCounter);
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

	public void getHitByProjectile(float damage, FixtureSensorID fixtureSensorID, PlayableGameHero owner)
	{
		if (!invincible)
		{
			if (!playerIsDead)
			{
				System.out.println(fixtureSensorID);
				owner.fragStatistic.incrementShootsOnTarget();
				fragStatistic.incrementHitAsTarget();
				if (fixtureSensorID == FixtureSensorID.HEAD)
				{
					damage *= 3;
				}
				heroAttributes.getLife().decrement(damage);
				if (playerIsDead)
				{
					fragStatistic.incrementDeaths();
					if (heroProperties.getHeroTeam().getTeamId() != owner.heroProperties.getHeroTeam().getTeamId())
						owner.getFragStatistic().incrementKills();
				}
			}

		}
		else
		{
			System.out.println("Absorved projectile");
		}
	}

	public Vector2f getMassCenter()
	{
		return WorldConstants.physicsToSfmlCoordinates(collidableHero.getBody().getWorldCenter());
	}

	public boolean canShootShuriken()
	{
		return heroAttributes.getShurikens().isAbleToShoot();
	}

	public boolean canUseItem()
	{
		return heroAttributes.getSmokeBomb().isAbleToShoot();
	}

	public boolean isInvincible()
	{
		return invincible;
	}

	public void die()
	{
		System.out.println("Got dead");
		collidableHero.die();
		playerIsDead = true;
	}

	public boolean isPlayerDead()
	{
		return playerIsDead;
	}

	public void enableInvincibility()
	{
		invincible = true;
	}

	public void disableInvincibility()
	{
		invincible = false;
	}

	public void deadlySceneryHit(float damage)
	{
		if (!playerIsDead)
		{
			heroAttributes.getLife().decrement(damage);
			if (heroAttributes.getLife().getCurrentValue() <= 0)
			{
				fragStatistic.incrementSuicide();
			}
		}
	}

	@Override
	protected void gotOutOfScenery()
	{
		if (!playerIsDead)
		{
			fragStatistic.incrementSuicide();
		}
	}

}
