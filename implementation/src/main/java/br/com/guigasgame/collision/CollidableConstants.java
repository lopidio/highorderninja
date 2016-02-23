package br.com.guigasgame.collision;

public class CollidableConstants 
{
	
	///What I am
	public enum Category
	{
		HERO(true),
		SCENERY,
		ROPE,
		SHURIKEN;
		
		
		private static int categoriesUsed = 0; 
		private final static int NUM_MAX_PLAYERS = 4;		
		
		CollidableCategory category;
		
		Category(boolean hero)
		{
			if (hero)
				category = getAllPlayersCategory();
			else
			{
				category = getNextCategory();
			}
			System.out.println(toString() + ": " + Integer.toBinaryString(category.getValue()));
		}

		Category()
		{
			this(false);
		}
		
		public static Category fromCollidableFilter(CollidableFilter collidableFilter)
		{
			for( Category category : Category.values() )
			{
				if (collidableFilter.getCategory() == category.category)
					return category;
			}
			return null;
		}
		
		public CollidableCategory getPlayerCategory(int playerID)
		{
			if (playerID > NUM_MAX_PLAYERS - 1)
				return null;
			return HERO.category.matching((1 << (playerID - 1)));
		}
		
		public CollidableCategory getOtherPlayersCategory(int playerID)
		{
			if (playerID > NUM_MAX_PLAYERS - 1)
				return null;
			return HERO.category.matching(~(1 << (playerID - 1)));
		}	
		
		
		public static CollidableCategory getAllPlayersCategory()
		{
			int playersMask = 1;
			for (int i = 0; i < NUM_MAX_PLAYERS - 1; ++i) 
			{
				playersMask = (playersMask << 1) + 1;
			}
			return new CollidableCategory(playersMask);
		}
		
		public static CollidableCategory getNextCategory()
		{
			CollidableCategory retorno = new CollidableCategory((1 << categoriesUsed) << NUM_MAX_PLAYERS );
			System.out.println(Integer.toBinaryString(retorno.getValue()));
			++categoriesUsed;
			return retorno;
		}			
		
	}
	
	public enum CollidableFilterEnum
	{
		HERO 			(new CollidableFilter(sceneryCategory).addCollisionWithEveryThing()),
		SCENERY 		(new CollidableFilter(shurikenCategory).addCollisionWith(herosCategory).and(sceneryCategory).and(shurikenCategory)),
		ROPE 			(new CollidableFilter(ropeBodyCategory).addCollisionWith(sceneryCategory)),
		SHURIKEN 		(new CollidableFilter(ropeNodeCategory).addCollisionWith(sceneryCategory).and(ropeNodeCategory));		
		
		CollidableFilter filter;

		private CollidableFilterEnum(CollidableFilter filter) 
		{
			this.filter = filter;
		}

		public CollidableFilter getFilter() 
		{
			return filter;
		}
		
		
	}
	
	
	///What I am
	public final static CollidableCategory herosCategory		= CollidableConstants.getAllPlayersCategory();
	public final static CollidableCategory sceneryCategory 		= CollidableConstants.getNextCategory();
	public final static CollidableCategory shurikenCategory		= CollidableConstants.getNextCategory();
	public final static CollidableCategory ropeBodyCategory		= CollidableConstants.getNextCategory();
	public final static CollidableCategory ropeNodeCategory		= CollidableConstants.getNextCategory();
	
	public static CollidableCategory getPlayerCategory(int playerID)
	{
		return herosCategory.matching(1 << (playerID - 1));
	}
	
	public static CollidableCategory getOtherPlayersCategory(int playerID)
	{
		return herosCategory.matching(~(1 << (playerID - 1)));
	}

	private static int categoriesUsed = 0; 
	private final static int NUM_MAX_PLAYERS = 4;
	
	public static CollidableCategory getAllPlayersCategory()
	{
		int playersMask = 1;
		for (int i = 0; i < NUM_MAX_PLAYERS - 1; ++i) 
		{
			playersMask = (playersMask << 1) + 1;
		}
		return new CollidableCategory(playersMask);
	}
	
	public static CollidableCategory getNextCategory()
	{
		CollidableCategory retorno = new CollidableCategory((1 << categoriesUsed) << NUM_MAX_PLAYERS );
		System.out.println(Integer.toBinaryString(retorno.getValue()));
		++categoriesUsed;
		return retorno;
	}
	
	
	
	///What I collide with
	private final static CollidableFilter sceneryCollidableFilter		= new CollidableFilter(sceneryCategory).addCollisionWithEveryThing();
	
	private final static CollidableFilter shurikenCollidableFilter		= new CollidableFilter(shurikenCategory).addCollisionWith(herosCategory).and(sceneryCategory).and(shurikenCategory);
	
	private final static CollidableFilter ropeBodyCollidableFilter 		= new CollidableFilter(ropeBodyCategory).addCollisionWith(sceneryCategory);

	private final static CollidableFilter ropeNodeCollidableFilter 		= new CollidableFilter(ropeNodeCategory).addCollisionWith(sceneryCategory).and(ropeNodeCategory);

	public static CollidableFilter getPlayerFilter(int playerID)
	{
		return new CollidableFilter(getPlayerCategory(playerID)).addCollisionWith(shurikenCategory).and(sceneryCategory).and(herosCategory).clone();
	}

	public static CollidableFilter getRopeBodyCollidableFilter()
	{
		return ropeBodyCollidableFilter.clone();
	}

	public static CollidableFilter getRopeNodeCollidableFilter()
	{
		return ropeNodeCollidableFilter.clone();
	}

	public static CollidableFilter getShurikenCollidableFilter()
	{
		return shurikenCollidableFilter.clone();
	}

	public static CollidableFilter getSceneryCollidablefilter()
	{
		return sceneryCollidableFilter.clone();
	}
	
}
