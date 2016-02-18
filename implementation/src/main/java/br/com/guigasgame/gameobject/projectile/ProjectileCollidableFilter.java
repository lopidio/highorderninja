package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.dynamics.Filter;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.collision.IntegerMask;

public class ProjectileCollidableFilter
{
	private CollidableFilter collidableFilter;
	private IntegerMask aimingMask;

	public ProjectileCollidableFilter(Filter collidableFilter) 
	{
		this.collidableFilter = new CollidableFilterBox2dAdapter(collidableFilter).toCollidableFilter();
		aimingMask = new IntegerMask();
	}

	public ProjectileCollidableFilter(CollidableFilter collidableFilter) 
	{
		this.collidableFilter = collidableFilter;
		aimingMask = new IntegerMask();
	}

	public ProjectileCollidableFilter aimTo(CollidableCategory collidableCategory)
	{
		aimingMask = aimingMask.set(collidableCategory.getValue());
		return this;
	}
	
	public CollidableFilter getCollidableFilter()
	{
		return collidableFilter;
	}
	
	public IntegerMask getAimingMask()
	{
		return aimingMask;
	}
	

}
