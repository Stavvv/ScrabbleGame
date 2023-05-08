package test;


public class Tile {

	public final char letter;
	public final int score;
	
	private Tile(char letter, int score) {
		super();
		this.letter = letter;
		this.score = score;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + letter;
		result = prime * result + score;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (letter != other.letter)
			return false;
		if (score != other.score)
			return false;
		return true;
	}


	public static class Bag
	{
		private static Bag myBag;
		private final int lettersNum = 26;
		private int origAmount[];
		private int currAmount[];
		private Tile tiles[];
		private int tilesCount;
		
		private Bag()
		{
			this.origAmount = new int[] {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
			this.currAmount = new int[lettersNum];
			this.tilesCount = 0;
			for(int i = 0; i < lettersNum;i++)
			{
				this.currAmount[i] = this.origAmount[i]; 
				this.tilesCount += this.origAmount[i];
			}
			int values[] = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };
			this.tiles = new Tile[lettersNum];
			for(int i = 0; i < lettersNum;i++)
			{
				this.tiles[i] = new Tile((char)('A' + i), values[i]);
			}
		}
		
		public static Bag getBag()
		{
			if(Bag.myBag == null)
			{
				Bag.myBag = new Bag();
			}
			return Bag.myBag;
		}
		
		public Tile getRand()
		{
			if(this.tilesCount == 0)
			{
				return null;
			}
			int randTitleInd = 0 + (int)(Math.random() * ((tiles.length - 1 - 0) + 1));
			while(this.currAmount[randTitleInd] == 0)
			{
				randTitleInd = 0 + (int)(Math.random() * ((tiles.length - 1 - 0) + 1));
			}
			--this.tilesCount;
			--this.currAmount[randTitleInd];
			return this.tiles[randTitleInd];
		}
		
		public Tile getTile(char letter)
		{
			int ind = letter - 'A';
			if(ind >= 0 && ind < this.tiles.length)
			{
				--this.tilesCount;
				--this.currAmount[ind];
				return this.tiles[ind];
			}
			return null;
		}
		
		public void put(Tile tile)
		{
			if(tile == null)
			{
				return;
			}
			int ind = tile.letter - 'A';
			if(ind >= 0 && ind < this.tiles.length)
			{
				if(this.currAmount[ind] < this.origAmount[ind])
				{
					++this.currAmount[ind];
					++this.tilesCount;
				}
			}
		}
		
		public int size()
		{
			return this.tilesCount;
		}
		
		public int[] getQuantities()
		{
			int quantities[] = new int[this.currAmount.length];
			for(int i = 0; i < quantities.length;i++)
			{
				quantities[i] = this.currAmount[i];
			}
			return quantities;
		}
	}
}
