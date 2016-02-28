package br.com.guigasgame.collision;

import br.com.guigasgame.collision.CollidableConstants.Category;

public class CollidableFilterManipulator 
{

	public static class CollidableFilterAddingCollision extends CollidableFilter
	{
		CollidableFilterAddingCollision(IntegerMask collidableCategory, IntegerMask collidesWith) 
		{
			super(collidableCategory, collidesWith);
		}
		public CollidableFilterAddingCollision and(Category category)
		{
			return new CollidableFilterAddingCollision(this.category, collider.set(category.getMask().value));
		}
		public CollidableFilterRemovingCollision except(Category category)
		{
			return new CollidableFilterRemovingCollision(this.category, collider.clear(category.getMask().value));
		}
	}
	
	public static class CollidableFilterRemovingCollision extends CollidableFilter
	{
		CollidableFilterRemovingCollision(IntegerMask collidableCategory, IntegerMask collidesWith) 
		{
			super(collidableCategory, collidesWith);
		}
		
		public CollidableFilterRemovingCollision and(Category category)
		{
			return new CollidableFilterRemovingCollision(this.category, collider.clear(category.getMask().value));
		}		

		public CollidableFilterAddingCollision except(Category category)
		{
			return new CollidableFilterAddingCollision(this.category, collider.set(category.getMask().value));
		}		

	}

}
