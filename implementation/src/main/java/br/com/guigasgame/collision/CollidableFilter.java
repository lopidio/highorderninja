package br.com.guigasgame.collision;

import br.com.guigasgame.collision.CollidableConstants.Category;
import br.com.guigasgame.collision.CollidableFilterManipulator.CollidableFilterAddingCollision;
import br.com.guigasgame.collision.CollidableFilterManipulator.CollidableFilterRemovingCollision;


public class CollidableFilter 
{
	///What I am
	protected final IntegerMask category;

	///What I collide with
	protected IntegerMask collider;

	public CollidableFilter(IntegerMask collidableCategory, IntegerMask collidesWith) 
	{
		category = collidableCategory;
		collider = new IntegerMask(collidesWith.value);
	}

	public CollidableFilter(Category category)
	{
		this.category = category.getMask();
		collider = new IntegerMask();
	}

	public CollidableFilter(IntegerMask playerCategory)
	{
		this(playerCategory, new IntegerMask());
	}

	public CollidableFilter clone() 
	{
		return new CollidableFilter(category, new IntegerMask(collider.value));
	}
	
	public boolean matches(IntegerMask collidableCategory) 
	{
		return collider.matches(collidableCategory.value);
	}
	
	public IntegerMask getCategory()
	{
		return category;
	}
	
	public IntegerMask getCollider()
	{
		return collider;
	}
	
	public CollidableFilterAddingCollision addCollisionWith(Category category)
	{
		return new CollidableFilterAddingCollision(this.category, category.getMask());
	}

	public CollidableFilterAddingCollision addCollisionWithEveryThing()
	{
		return new CollidableFilterAddingCollision(this.category, new IntegerMask().setAll());
	}
	
	
	public CollidableFilterRemovingCollision removeCollisionWith(Category category) 
	{
		return new CollidableFilterRemovingCollision(this.category, collider.clear(category.getMask().value));
	}

	public CollidableFilterRemovingCollision removeCollisionWith(IntegerMask category) 
	{
		return new CollidableFilterRemovingCollision(this.category, collider.clear(category.value));
	}
	
}
