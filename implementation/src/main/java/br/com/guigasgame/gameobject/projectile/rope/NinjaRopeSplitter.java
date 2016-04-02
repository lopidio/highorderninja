package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.raycast.RayCastCallBackWrapper;
import br.com.guigasgame.raycast.RayCastClosestFixture;

class NinjaRopeSplitter
{
	private Vec2 divisionPoint;
	private RayCastClosestFixture castClosestFixture;
	private final Vec2 hookPoint;
	private final Body heroBody;

	
	public NinjaRopeSplitter(Vec2 hookPoint, Body heroBody)
	{
		castClosestFixture = new RayCastClosestFixture(heroBody.getWorld(), 
				heroBody.getPosition().clone(), hookPoint, CollidableCategory.SCENERY.getCategoryMask());
		this.hookPoint = hookPoint.clone();
		this.heroBody = heroBody;
	}
	
	public boolean checkSplitting(float deltaTime)
	{
		RayCastCallBackWrapper retorno = verifyCollision(deltaTime, getHeroTimeProjectionPoint(0));
		if (retorno != null)
		{
			divisionPoint = calculatePreviousCollisionPoint(1 - retorno.fraction, getHeroTimeProjectionPoint(-deltaTime));
			return true;
		}
		return false;
	}
	
	private Vec2 calculatePreviousCollisionPoint(float fraction, Vec2 prevHeroPosition)
	{
		final Vec2 retorno = hookPoint.clone();
		final Vec2 adjust = prevHeroPosition.sub(retorno).mul(fraction).clone();
		return retorno.add(adjust);
	}

	private RayCastCallBackWrapper verifyCollision(float deltaTime, Vec2 heroPosition)
	{
		if (heroPosition == null)
			return null;
		castClosestFixture.setFrom(heroPosition.clone());
		castClosestFixture.setTo(hookPoint.clone());
		castClosestFixture.shoot();
		RayCastCallBackWrapper callBackWrapper = castClosestFixture.getCallBackWrapper();
		if (callBackWrapper != null && callBackWrapper.fraction < 1)
		{
			return callBackWrapper;
		}
		return null;
	}

	private Vec2 getHeroTimeProjectionPoint(float deltaTime)
	{
		return heroBody.getPosition().add(heroBody.getLinearVelocity().mul(deltaTime));
	}

	public boolean isMarkedToDivide()
	{
		if (divisionPoint == null)
			return false;
		return divisionPoint.sub(hookPoint).length() > NinjaRopeScaler.MIN_LINK_SIZE
				&& divisionPoint.sub(heroBody.getPosition()).length() > NinjaRopeScaler.MIN_LINK_SIZE;
	}

	public void unmarkToSplitting()
	{
		divisionPoint = null;
	}

	public Vec2 getDivisionPoint()
	{
		return divisionPoint;
	}

}
