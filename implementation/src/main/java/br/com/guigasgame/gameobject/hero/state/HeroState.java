package br.com.guigasgame.gameobject.hero.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.gameobject.hero.action.DisableInvincibilityAction;
import br.com.guigasgame.gameobject.hero.action.EnableInvincibilityAction;
import br.com.guigasgame.gameobject.hero.action.HeroStateAlternativeSetterAction;
import br.com.guigasgame.gameobject.hero.action.HeroStateSetterAction;
import br.com.guigasgame.gameobject.hero.action.JumpAction;
import br.com.guigasgame.gameobject.hero.action.MoveHeroAction;
import br.com.guigasgame.gameobject.hero.action.ShootAction;
import br.com.guigasgame.gameobject.hero.action.UseItemAction;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.input.InputListener;
import br.com.guigasgame.math.Vector2;
import br.com.guigasgame.side.Side;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class HeroState implements InputListener<HeroInputKey>, UpdatableFromTime
{

	protected final PlayableGameHero gameHero;
	protected final HeroStateProperties heroStatesProperties;
	private Map<HeroInputKey, Boolean> inputMap;
	protected List<Animation> animationList;
	private final HeroStateIndex heroIndex;

	protected HeroState(PlayableGameHero gameHero, HeroStateIndex heroIndex)
	{
		super();
		this.heroIndex = heroIndex;
		this.heroStatesProperties = HeroStatesPropertiesPool.getStateProperties(heroIndex);
		animationList = new ArrayList<Animation>();
		animationList.add(Animation.createAnimation(AnimationsCentralPool.getHeroAnimationRepository().getAnimationsProperties(heroIndex)));
		this.gameHero = gameHero;
		
		inputMap = new HashMap<>();
		for( HeroInputKey key : HeroInputKey.values() )
		{
			inputMap.put(key, false);
		}
	}

	protected void stateOnEnter()
	{
		// hook method
	}

	protected void stateOnQuit()
	{
		// hook method
	}

	protected void stateUpdate(float deltaTime)
	{
		// hook method
	}

	protected void stateInputPressed(HeroInputKey inputValue)
	{
		// hook method
	}

	protected void stateInputReleased(HeroInputKey inputValue)
	{
		// hook method
	}
	
	protected void stateInputIsPressing(HeroInputKey inputValue)
	{
		// hook method
	}
	
	protected void stateDoubleTapInput(HeroInputKey inputValue)
	{
		// hook method
	}

	public final void onEnter()
	{
		gameHero.setAnimationList(animationList);
		if (heroStatesProperties.invincible != null)
			gameHero.addAction(new EnableInvincibilityAction());			
		stateOnEnter();
	}

	public final void onQuit()
	{
		if (heroStatesProperties.invincible != null)
			gameHero.addAction(new DisableInvincibilityAction());
		stateOnQuit();
	}

	protected void rope()
	{
		if (heroStatesProperties.rope != null)
		{
			setState(new NinjaRopeShootingState(gameHero));
		}
	}
	
	protected void jump()
	{
		JumpingHeroState jumping = new JumpingHeroState(gameHero);
		if (jumping.canExecute(gameHero))
		{
			gameHero.addAction(new JumpAction(heroStatesProperties));
			setState(jumping);
		}
	}
	
	protected void shoot()
	{
		gameHero.addAction(new ShootAction(heroStatesProperties, pointingDirection()));
	}
	
	private void shootItem()
	{
		gameHero.addAction(new UseItemAction(heroStatesProperties, gameHero.getSmokeBomb(pointingDirection())));
	}

	@Override
	public final void inputPressed(HeroInputKey inputValue)
	{
		inputMap.put(inputValue, true);
		if (inputValue == HeroInputKey.JUMP)
		{
			if (heroStatesProperties.jump != null && !gameHero.getCollidableHero().isHeadTouchingAnything())
			{
				jump();
			}
		}
		if (inputValue == HeroInputKey.ROPE)
		{
			if (heroStatesProperties.rope != null)
			{
				rope();
			}
		}
		stateInputPressed(inputValue);
	}
	
	@Override
	public final void isPressing(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.LEFT)
		{
			if (heroStatesProperties.move != null)
			{
				move(Side.LEFT);
			}
		}
		else if (inputValue == HeroInputKey.RIGHT)
		{
			if (heroStatesProperties.move != null)
			{
				move(Side.RIGHT);
			}
		}
		
		stateInputIsPressing(inputValue);
	}

	@Override
	public final void doubleTapInput(HeroInputKey inputValue)
	{
		stateDoubleTapInput(inputValue);
	}

	protected void move(Side side)
	{
		if (gameHero.isTouchingWallAhead() && gameHero.getCollidableHero().isGoingTo(side.opposite()))
		{
			return;
		}
		gameHero.addAction(new MoveHeroAction(side, heroStatesProperties));
	}

	@Override
	public final void inputReleased(HeroInputKey inputValue)
	{
		inputMap.put(inputValue, false);
		if (inputValue == HeroInputKey.SHOOT)
		{
			shoot();
		}		
		if (inputValue == HeroInputKey.ITEM)
		{
			shootItem();
		}		
		stateInputReleased(inputValue);
	}

	@Override
	public final void update(float deltaTime)
	{
		if (gameHero.isPlayerDead() && heroIndex != HeroStateIndex.HERO_DEAD)
		{
			setState(new DeadHeroState(gameHero));
		}
		stateUpdate(deltaTime);
	}

	protected final void setState(HeroState heroState)
	{
		heroState.getPropertyOfPreviousState(this);
		gameHero.addAction(new HeroStateSetterAction(heroState));
	}

	protected final void setStateAndAlternative(HeroState heroState, HeroState alternativeState)
	{
		heroState.getPropertyOfPreviousState(this);
		alternativeState.getPropertyOfPreviousState(this);
		gameHero.addAction(new HeroStateAlternativeSetterAction(heroState, alternativeState));
	}

	public final Vector2 getMaxSpeed()
	{
		return heroStatesProperties.maxSpeed;
	}

	protected final Vec2 pointingDirection()
	{
		Vec2 retorno = new Vec2();

		if (inputMap.get(HeroInputKey.UP))
		{
			retorno.y = -1;
		}
		else if (inputMap.get(HeroInputKey.DOWN))
		{
			retorno.y = 1;
		}

		if (inputMap.get(HeroInputKey.RIGHT))
		{
			retorno.x = 1;
		}
		else if (inputMap.get(HeroInputKey.LEFT))
		{
			retorno.x = -1;
		}

		if (retorno.length() == 0) // Default
		{
			retorno.x = gameHero.getForwardSide().getHorizontalValue();
		}
		return retorno;
	}
	
	protected boolean isHeroInputMapPressed(HeroInputKey key)
	{
		return inputMap.get(key);
	}

	public boolean canExecute(PlayableGameHero hero)
	{
		//Hook method
		return true;
	}

	private void getPropertyOfPreviousState(HeroState heroState)
	{
		this.inputMap = heroState.inputMap;
	}

	public boolean isAnimationsFinished()
	{
		for( Animation animation : animationList )
		{
			if (!animation.isFinished())
			{
				return false;
			}
		}
		return true;
	}

}
