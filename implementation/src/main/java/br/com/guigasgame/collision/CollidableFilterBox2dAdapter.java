package br.com.guigasgame.collision;

import org.jbox2d.dynamics.Filter;

public class CollidableFilterBox2dAdapter 
{
	///What I am
	protected final IntegerMask category;

	///What I collide with
	protected IntegerMask collider;
	
	public CollidableFilterBox2dAdapter(Filter filter) 
	{
		category = new IntegerMask(filter.categoryBits);
		collider = new IntegerMask(filter.maskBits);
	}
	
	public CollidableFilterBox2dAdapter(CollidableFilter collidableFilter) 
	{
		this.category = collidableFilter.getCategory();
		this.collider = collidableFilter.getCollider();
	}
	
	public CollidableFilterBox2dAdapter(IntegerMask mask) 
	{
		this.category = mask;
		this.collider = new IntegerMask();
	}

	public CollidableFilterBox2dAdapter(CollidableCategory filter) 
	{
		this(filter.getFilter());
	}

	public Filter toBox2dFilter()
	{
		Filter filter = new Filter();
		filter.categoryBits = category.value;
		filter.maskBits = collider.value;
		return filter;
	}	

	public CollidableFilter toCollidableFilter()
	{
		return new CollidableFilter(category, collider);
	}	

}
