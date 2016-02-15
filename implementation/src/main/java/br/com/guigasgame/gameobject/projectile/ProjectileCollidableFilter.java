package br.com.guigasgame.gameobject.projectile;

import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.CollidableFilter.Category;

public class ProjectileCollidableFilter
{
	private CollidableFilter collidableFilter;

	public ProjectileCollidableFilter(CollidableFilter collidableFilter) 
	{
		this.collidableFilter = collidableFilter;
	}

	public ProjectileCollidableFilter aimTo(Category category)
	{
		collidableFilter = collidableFilter.and(category);
		return this;
	}

	public CollidableFilter getCollidableFilter() 
	{
		return collidableFilter;
	}
	
}
