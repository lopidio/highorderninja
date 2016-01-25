package br.com.guigasgame.gameobject.hero.state;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.executor.HeroJumperExecutor;
import br.com.guigasgame.gameobject.hero.executor.HeroShooterExecutor;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.input.InputListener;
import br.com.guigasgame.math.Vector2;
import br.com.guigasgame.side.Side;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class HeroState
		implements InputListener<HeroInputKey>, UpdatableFromTime
{

	protected final GameHero gameHero;
	protected final HeroAnimationsIndex heroAnimationsIndex;
	protected final HeroStatesProperties heroStatesProperties;
	private Map<HeroInputKey, Boolean> inputMap;
	private final Animation animation;
	protected final HeroJumperExecutor jumper;
	protected final HeroShooterExecutor shooter;

	protected HeroState(GameHero gameHero, HeroAnimationsIndex heroAnimationsIndex)
	{
		super();
		this.heroAnimationsIndex = heroAnimationsIndex;
		this.heroStatesProperties = HeroStatesPropertiesRepository.getStateProperties(heroAnimationsIndex);
		this.animation = Animation.createAnimation(AnimationsRepositoryCentral.getHeroAnimationRepository().getAnimationsProperties(heroAnimationsIndex));
		this.gameHero = gameHero;
		inputMap = new HashMap<>();
		for( HeroInputKey key : HeroInputKey.values() )
		{
			inputMap.put(key, false);
		}
		jumper = heroStatesProperties.canJump
				? new HeroJumperExecutor(heroStatesProperties, gameHero) : null;
		shooter = heroStatesProperties.canShoot
				? new HeroShooterExecutor(heroStatesProperties, gameHero)
				: null;
	}

	public void onEnter()
	{
		// hook method
	}

	public void onQuit()
	{
		// hook method
	}

	public void updateState(float deltaTime)
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

	@Override
	public final void inputPressed(HeroInputKey inputValue)
	{
		inputMap.put(inputValue, true);
		stateInputPressed(inputValue);
	}

	@Override
	public final void inputReleased(HeroInputKey inputValue)
	{
		inputMap.put(inputValue, false);
		stateInputReleased(inputValue);
	}

	@Override
	public final void update(float deltaTime)
	{
		animation.update(deltaTime);
		updateState(deltaTime);
	}

	protected final void setState(HeroState heroState)
	{
		gameHero.setState(heroState);
		heroState.animation.flipAnimation(gameHero.getForwardSide());
		heroState.inputMap = inputMap;
	}

	public final Vector2 getMaxSpeed()
	{
		return heroStatesProperties.maxSpeed;
	}

	public final Animation getAnimation()
	{
		return animation;
	}

	protected void shoot()
	{
		shooter.shoot(gameHero.getBody().getPosition(), ProjectileIndex.SHURIKEN, HeroShooterExecutor.createDirection(
						inputMap.get(HeroInputKey.UP), inputMap.get(HeroInputKey.RIGHT), 
						inputMap.get(HeroInputKey.DOWN), inputMap.get(HeroInputKey.LEFT)));
	}

	// protected boolean jump()
	// {
	// return jumper.jump(heroStatesProperties, gameHero);
	// // if (heroStatesProperties.canJump)
	// // {
	// // gameHero.applyImpulse(new Vec2(0,
	// // -heroStatesProperties.jumpImpulse));
	// // return true;
	// // }
	// // return false;
	// }
	//
	// protected boolean doubleJump()
	// {
	// HeroJumper jumper = new HeroJumper();
	// return jumper.doubleJump(heroStatesProperties, gameHero);
	// if (heroStatesProperties.canJump)
	// {
	// gameHero.applyImpulse(new Vec2(0,
	// -heroStatesProperties.jumpImpulse/2));
	// return true;
	// }
	// return false;
	// }
	//
	// protected boolean diagonalJump(Side side)
	// {
	// HeroJumper jumper = new HeroJumper();
	// return jumper.diagonalJump(heroStatesProperties, gameHero, side);
	// if (heroStatesProperties.canJump)
	// {
	// Vec2 jumpDirection = new Vec2(0, -1);
	// if (side == Side.LEFT)
	// {
	// jumpDirection.x = -1;
	// }
	// else // if (gameHero.getForwardSide() == Side.RIGHT)
	// {
	// jumpDirection.x = 1;
	// }
	//
	// jumpDirection.normalize();
	// jumpDirection.mulLocal(heroStatesProperties.jumpImpulse);
	//
	// System.out.println(jumpDirection);
	// System.out.println(jumpDirection.length());
	// gameHero.applyImpulse(jumpDirection);
	// return true;
	// }
	// return false;
	// }

	protected void moveForward()
	{
		if (gameHero.getForwardSide() == Side.LEFT)
		{
			moveLeft();
		}
		else // if (gameHero.getForwardSide() == Side.RIGHT)
		{
			moveRight();
		}
	}

	protected void moveBackward()
	{
		if (gameHero.getForwardSide() == Side.LEFT)
		{
			moveRight();
		}
		else // if (gameHero.getForwardSide() == Side.RIGHT)
		{
			moveLeft();
		}
	}

	protected void setHeroForwardSide(Side side)
	{
		animation.flipAnimation(side);
		gameHero.setForwardSide(side);
	}

	protected void moveRight()
	{
		if (gameHero.getForwardSide() == Side.LEFT)
			setHeroForwardSide(Side.RIGHT);
		gameHero.applyForce(new Vec2(heroStatesProperties.horizontalAcceleration, 0));
	}

	protected void moveLeft()
	{
		if (gameHero.getForwardSide() == Side.RIGHT)
			setHeroForwardSide(Side.LEFT);
		gameHero.applyForce(new Vec2(-heroStatesProperties.horizontalAcceleration, 0));
	}

}
