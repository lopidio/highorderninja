package br.com.guigasgame.collision;

import br.com.guigasgame.collision.CollidableFilterManipulator.CollidableFilterAddingCollision;
import br.com.guigasgame.collision.CollidableFilterManipulator.CollidableFilterRemovingCollision;


public class CollidableFilter 
{
	///What I am
	protected final CollidableCategory category;

	///What I collide with
	protected IntegerMask collider;

	public CollidableFilter(CollidableCategory collidableCategory, CollidableCategory collidesWith) {
		category = collidableCategory;
		collider = new IntegerMask(collidesWith.getValue());
	}

	public CollidableFilter(CollidableCategory collidableCategory)
	{
		category = collidableCategory;
		collider = new IntegerMask();
	}

	public CollidableFilter clone() 
	{
		return new CollidableFilter(category, new CollidableCategory(collider.value));
	}
	
	public boolean matches(CollidableCategory collidableCategory) 
	{
		return collider.matches(collidableCategory.getValue());
	}
	
	public CollidableCategory getCategory()
	{
		return category;
	}
	
	public IntegerMask getCollider()
	{
		return collider;
	}
	
	public CollidableFilterAddingCollision addCollisionWith(CollidableCategory collidableCategory)
	{
		return new CollidableFilterAddingCollision(this.category, collidableCategory);
	}

	public CollidableFilterAddingCollision addCollisionWithEveryThing()
	{
		return new CollidableFilterAddingCollision(this.category, new CollidableCategory(new IntegerMask().setAll().value));
	}
	
	
	public CollidableFilterRemovingCollision removeCollisionWith(CollidableCategory collidableCategory) 
	{
		return new CollidableFilterRemovingCollision(this.category, new CollidableCategory(collider.clear(collidableCategory.getValue()).value));
	}
	
}
