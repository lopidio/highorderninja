package br.com.guigasgame.gameobject.projectile;

import java.util.Locale.Category;

import org.jbox2d.dynamics.Filter;

import br.com.guigasgame.collision.CollidableLanguage;
import br.com.guigasgame.collision.IntegerMask;

public class ProjectileCollidableFilter
{
	private final CollidableLanguage collidableFilter;
	private IntegerMask aimingMask;

	public ProjectileCollidableFilter(Filter collidableFilter) 
	{
		this.collidableFilter = new CollidableLanguage(collidableFilter);
	}

	public ProjectileCollidableFilter aimTo(Category category)
	{
		collidableFilter = collidableFilter.and(category);
		return this;
	}

}
