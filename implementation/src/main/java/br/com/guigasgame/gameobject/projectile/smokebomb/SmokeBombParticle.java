package br.com.guigasgame.gameobject.projectile.smokebomb;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.Color;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;

public class SmokeBombParticle extends Projectile
{
	private float alpha;
	private float scale;

	public SmokeBombParticle(Vec2 direction, Vec2 position, Color color)
	{
		super(ProjectileIndex.SMOKE_BOMB_PARTICLE, direction, position);
		collidableFilter = CollidableConstants.Filter.SMOKE_BOMB.getFilter();
		alpha = 255;
		scale = 1;
		setAnimationsColor(color);
	}

	@Override
	public void onEnter()
	{
		shoot();
	}
	
	@Override
	protected void shoot()
	{
		Body body = collidable.getBody();
		FixtureDef def = createFixtureDef();
		body.createFixture(def);
		
		direction.mulLocal(randomizeValueBetween(0, 0.2f));
		body.setGravityScale(randomizeValueBetween(-0.01f, 0.01f));
		body.applyLinearImpulse(direction, body.getWorldCenter());
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime*randomizeValueBetween(1f, 3f));
		for( Drawable drawable : drawableList )
		{
			Animation animation = (Animation) drawable;
			alpha = alpha*(1 - randomizeValueBetween(deltaTime/10, deltaTime/1));
			animation.setAlpha((int) alpha);
			if (scale <= 30)
			{
				scale += deltaTime*1.01;
				animation.scale(1 + scale);
			}
			if (alpha <= 10)
				markToDestroy();

		}
	}
	
	float randomizeValueBetween(float min, float max)
	{
		return (float) ((Math.random()*(max - min)) + min);
	}


}
