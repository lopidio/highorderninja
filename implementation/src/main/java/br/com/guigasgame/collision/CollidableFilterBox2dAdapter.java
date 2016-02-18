package br.com.guigasgame.collision;

import org.jbox2d.dynamics.Filter;

public class CollidableFilterBox2dAdapter 
{
	///What I am
	protected final CollidableCategory category;

	///What I collide with
	protected IntegerMask collider;
	
	public CollidableFilterBox2dAdapter(Filter filter) 
	{
		category = new CollidableCategory(filter.categoryBits);
		collider = new IntegerMask(filter.maskBits);
	}
	
	public CollidableFilterBox2dAdapter(CollidableFilter collidableFilter) 
	{
		
		this.category = collidableFilter.getCategory();
		this.collider = collidableFilter.getCollider();
	}

	public Filter toBox2dFilter()
	{
		Filter filter = new Filter();
		filter.categoryBits = category.getValue();
		filter.maskBits = collider.value;
		return filter;
	}	

	public CollidableFilter toCollidableFilter()
	{
		return new CollidableFilter(category, new CollidableCategory(collider.value));
	}	

}
