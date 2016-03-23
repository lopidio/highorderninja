package br.com.guigasgame.gameobject.projectile.smokebomb;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.Color;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.math.Randomizer;

public class SmokeBombParticle extends Projectile
{
	private float alpha;
	private float scale;

	public SmokeBombParticle(Vec2 direction, Vec2 position, Color color)
	{
		super(ProjectileIndex.SMOKE_BOMB_PARTICLE, direction, position);
		collidableFilter = CollidableCategory.SMOKE_BOMB.getFilter();
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
		
		direction.mulLocal(Randomizer.getRandomFloatInInterval(0.3f, 0.9f));
		body.setGravityScale(Randomizer.getRandomFloatInInterval(-0.01f, 0.01f));
		body.applyLinearImpulse(direction, body.getWorldCenter());
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime*Randomizer.getRandomFloatInInterval(1f, 3f));
		for( Drawable drawable : drawableList )
		{
			Animation animation = (Animation) drawable;
			alpha = alpha*(1 - Randomizer.getRandomFloatInInterval(deltaTime/100, deltaTime/2));
			animation.setRotation(Randomizer.getRandomFloatInInterval(0, 180));
			animation.setAlpha((int) alpha);
			if (scale <= 40)
			{
				scale += deltaTime*1.01;
				animation.scale(1 + scale);
			}
			if (alpha <= 20)
				markToDestroy();

		}
	}

}
