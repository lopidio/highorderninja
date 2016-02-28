package br.com.guigasgame.collision;

public class CollidableConstants 
{
	
	///What I am
	public enum Category
	{
		HEROS		(getAllPlayersCategory()),
		SCENERY		(getNextCategory()),
		ROPE_NODE	(getNextCategory()),
		ROPE_BODY	(getNextCategory()),
		SHURIKEN	(getNextCategory()), 
		SMOKE_BOMB	(getNextCategory());
		
		private final static int NUM_MAX_PLAYERS = 8;		
		private static int categoriesUsed = 0; 
		
		private IntegerMask mask;
		
		private Category(IntegerMask mask)
		{
			this.mask = mask;
		}
		
		public static Category fromMask(int value)
		{
			for( Category category : Category.values() )
			{
				if (category.mask.matches(value))
					return category;
			}
			return null;
		}
		
		public static IntegerMask getPlayerCategory(int playerID)
		{
			if (playerID > NUM_MAX_PLAYERS - 1)
				return null;
			int valueToMatch = 1 << (playerID);
			return new IntegerMask(HEROS.mask.and(valueToMatch).value);
		}
		
		public static IntegerMask getOtherPlayersCategory(int playerID)
		{
			if (playerID > NUM_MAX_PLAYERS - 1)
				return null;
			return getAllPlayersCategory().clear(getPlayerCategory(playerID).value);
		}	
		
		public static IntegerMask getAllPlayersCategory()
		{
			int playersMask = 1;
			IntegerMask retorno = new IntegerMask();
			for (int i = 0; i < NUM_MAX_PLAYERS; ++i) 
			{
				retorno = retorno.set(playersMask);
				playersMask = (playersMask << 1);
			}
			return retorno;
		}
		
		public static IntegerMask getNextCategory()
		{
			IntegerMask retorno = new IntegerMask((1 << categoriesUsed) << NUM_MAX_PLAYERS );
			++categoriesUsed;
			return retorno;
		}

		public IntegerMask getMask()
		{
			return mask;
		}			
		
		public static void display()
		{
			for( Category category : Category.values() )
			{
				System.out.println("Category " + category.name() + ":\t" + Integer.toBinaryString(category.getMask().value));
			}
		}
		
	}
	
	public enum Filter
	{
		SCENERY 		(new CollidableFilter(Category.SCENERY).addCollisionWithEveryThing()),
		ROPE_NODE		(new CollidableFilter(Category.ROPE_NODE).addCollisionWith(Category.SCENERY)),
		ROPE_BODY		(new CollidableFilter(Category.ROPE_BODY).addCollisionWith(Category.SHURIKEN).and(Category.SCENERY)),
		SHURIKEN 		(new CollidableFilter(Category.SHURIKEN).addCollisionWith(Category.SCENERY).and(Category.ROPE_BODY).and(Category.ROPE_NODE).and(Category.HEROS)),
		SMOKE_BOMB		(new CollidableFilter(Category.SMOKE_BOMB).addCollisionWith(Category.SCENERY).and(Category.ROPE_BODY));
		
		CollidableFilter filter;

		private Filter(CollidableFilter filter) 
		{
			this.filter = filter;
		}

		public CollidableFilter getFilter() 
		{
			return filter;
		}
		
		
		public static CollidableFilter getPlayerFilter(int playerID)
		{
			return new CollidableFilter(Category.getPlayerCategory(playerID)).addCollisionWith(Category.HEROS).and(Category.SCENERY).and(Category.SHURIKEN);

		}
		
	}
}
