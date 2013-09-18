package nl.avans.avansudoku;


public class Game
{
	// 9 bij 9 blokken = 81 plaatsen
	private int[] _tiles = new int[9 * 9];
	
	public Game()
	{
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				boolean[] poss = getPossibilities((i * j), (j * i));
				
				for (int k = 0; k < 9; k++)
				{
					if (poss[k])
					{
						System.out.println(k + ": true");
					}
				}
			}
		}
	}
	
	private int getTile(int x, int y)
	{
		return _tiles[(y * 9) + x];
	}
	
	private boolean[] getPossibilities(int x, int y)
	{
		boolean[] possibilities = new boolean[9];
		
		for (int i = 0; i < 9; i++)
		{
			int tileX = getTile(i, y);
			int tileY = getTile(x, i);
			
			if (i != x && tileX > 0)
			{
				possibilities[tileX - 1] = true;
			}
			
			if (i != y && tileY > 0)
			{
				possibilities[tileY - 1] = true;
			}
		}
		
		int blockStartX = (x / 3) * 3;
		int blockStartY = (y / 3) * 3;
		
		for (int xx = blockStartX; xx < blockStartX + 3; xx++)
		{
			for (int yy = blockStartY; yy < blockStartY + 3; yy++)
			{
				if (xx == x && yy == y)
				{
					continue;
				}
				
				int tile = getTile(xx, yy);
				
				if (tile > 0)
				{
					possibilities[tile - 1] = true;
				}
			}
		}
		
		return possibilities;
	}
}