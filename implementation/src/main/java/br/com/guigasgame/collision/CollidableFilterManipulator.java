package br.com.guigasgame.collision;



public class CollidableFilterManipulator 
{
	public static class ColliderDefinitionFromCategory extends CollidableFilter
	{
		private ColliderDefinitionFromCategory(CollidableCategory collidableCategory)
		{
			super(collidableCategory, new CollidableCategory(0));
		}
		
		public CollidableFilterAddingCollision collidesWith(CollidableCategory collidableCategory)
		{
			return new CollidableFilterAddingCollision(this.category, collidableCategory);
		}

		public CollidableFilter collidesWithEveryThing()
		{
			return new CollidableFilter(this.category, new CollidableCategory(new IntegerMask().setAll().value));
		}

		public CollidableFilter collidesWithNothing()
		{
			return new CollidableFilter(this.category, new CollidableCategory(0));
		}
	}	
	

	public static class ColliderDefinitionFromFilter extends CollidableFilter
	{
		private ColliderDefinitionFromFilter(CollidableFilter collidableFilter)
		{
			super(collidableFilter.getCategory(), new CollidableCategory(collidableFilter.getCollider().value));
		}
		
		public CollidableFilterAddingCollision addCollisionWith(CollidableCategory collidableCategory)
		{
			return new CollidableFilterAddingCollision(this.category, new CollidableCategory(collider.set(collidableCategory.getValue()).value));
		}

		public CollidableFilter collidesWithNothing()
		{
			return new CollidableFilter(this.category, new CollidableCategory(0));
		}

		public CollidableFilterRemovingCollision removeCollisionWith(CollidableCategory collidableCategory) 
		{
			return new CollidableFilterRemovingCollision(this.category, new CollidableCategory(collider.clear(collidableCategory.getValue()).value));
		}
	}	
	

	public static class CollidableFilterAddingCollision extends CollidableFilter
	{
		private CollidableFilterAddingCollision(CollidableCategory collidableCategory, CollidableCategory collidesWith) 
		{
			super(collidableCategory, collidesWith);
		}
		public CollidableFilterAddingCollision and(CollidableCategory collidableCategory)
		{
			return new CollidableFilterAddingCollision(this.category, new CollidableCategory(collider.set(collidableCategory.getValue()).value));
		}
		public CollidableFilterRemovingCollision doesntCollideWith(CollidableCategory collidableCategory)
		{
			return new CollidableFilterRemovingCollision(this.category, new CollidableCategory(collider.clear(collidableCategory.getValue()).value));
		}
	}
	
	public static class CollidableFilterRemovingCollision extends CollidableFilter
	{
		private CollidableFilterRemovingCollision(CollidableCategory collidableCategory, CollidableCategory collidesWith) 
		{
			super(collidableCategory, collidesWith);
		}
		
		public CollidableFilterRemovingCollision and(CollidableCategory collidableCategory)
		{
			return new CollidableFilterRemovingCollision(this.category, new CollidableCategory(collider.clear(collidableCategory.getValue()).value));
		}		

	}
	
	public static ColliderDefinitionFromCategory createFromCategory(CollidableCategory collidableCategory)
	{
		return new ColliderDefinitionFromCategory(collidableCategory);
	}
	
	public static ColliderDefinitionFromFilter createFromCollidableFilter(CollidableFilter collidableFilter)
	{
		return new ColliderDefinitionFromFilter(collidableFilter);
	}
}
