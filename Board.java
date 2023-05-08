package test;


public class Board {

	private static Board myBoard;
	private Tile[][] board;
	private final int sizeBoard = 15;
	private final int tripleWord[][] = {{0, 0}, {0, 7}, {7, 0}};	
	private final int duplicatedLetter[][] = {{0, 3}, {2, 6}, {3, 0}, {3, 7}, {6, 2}, {6, 6}, {7, 3}};
	private final int tripleLetter[][] = {{1, 5}, {5, 1}, {5, 5}};
	private final int doubleWord[][] = {{1, 1}, {2, 2}, {3, 3}, {4, 4}};
	private int numWords = 0;
	
	
	public boolean isDictionaryLegal(Word word)
	{
		if(null == word)
		{
			return false;
		}
		return true;
	}

	private boolean isInValidBoundaries(int row, int col)
	{
		return row >= 0 && row < sizeBoard && col >= 0 && col < sizeBoard;
	}

	private boolean isGotNeighbor(int row, int col)
	{
		if(isInValidBoundaries(row - 1, col))
		{
			if(board[row - 1][col] != null)
			{
				return true;
			}
		}
		if(isInValidBoundaries(row + 1, col))
		{
			if(board[row + 1][col] != null)
			{
				return true;
			}
		}
		if(isInValidBoundaries(row, col - 1))
		{
			if(board[row][col - 1] != null)
			{
				return true;
			}
		}
		if(isInValidBoundaries(row, col + 1))
		{
			if(board[row][col + 1] != null)
			{
				return true;
			}
		}
		return false;
	}
	public boolean boardLegal(Word word) {
		if(word == null || word.getTiles() == null)
		{
			return false;
		}
		
		Tile[] tiles = word.getTiles();
		
		int row = word.getRow(), col = word.getCol();
		int curr = 0;
		boolean onStar = false, hasNeighbor = false, foundCommon = false;
		boolean vertical = word.isVertical();
		while(isInValidBoundaries(row, col) && curr < tiles.length)
		{
			if(this.board[row][col] != null)
			{
				if(tiles[curr] != null && tiles[curr] != this.board[row][col])
					return false;
				foundCommon = true;
			}
			if(tiles[curr] == null && board[row][col] == null)
			{
				return false;
			}
			if(row == sizeBoard / 2 && col == sizeBoard / 2)
			{
				onStar = true;
			}
			if(isGotNeighbor(row, col))
			{
				hasNeighbor = true;
			}
			if(vertical)
			{
				++row;
			}
			else
			{
				++col;
			}
			++curr;
		}
		if(curr != tiles.length)
		{
			return false;
		}
		return onStar || hasNeighbor || foundCommon;
	}

	private Word searchHorizontalWord(int row, int col)
	{
		int start = col, end = col;
		while(isInValidBoundaries(row, start) && board[row][start] != null)
		{
			start--;
		}
		while(isInValidBoundaries(row, end) && board[row][end] != null)
		{
			end++;
		}
		if(++start >= --end)
		{
			return null;
		}
		int length = end - start + 1;
		Tile[] tiles = new Tile[length];
		for(int j = start; j <= end;j++)
		{
			tiles[j - start] = board[row][j];
		}
		return new Word(tiles, row, start, false);
	}
	private Word searchVerticalWord(int row, int col)
	{
		int start = row, end = row;
		while(isInValidBoundaries(start, col) && board[start][col] != null)
		{
			start--;
		}
		while(isInValidBoundaries(end, col) && board[end][col] != null)
		{
			end++;
		}
		if(++start >= --end)
		{
			return null;
		}
		int length = end - start + 1;
		Tile[] tiles = new Tile[length];
		for(int i = start; i <= end;i++)
		{
			tiles[i - start] = board[i][col];
		}
		return new Word(tiles, start, col, true);
	}
	private int tryFindNewWords(int row, int col, boolean vertical)
	{
		if(vertical) //if vertical, search in row
		{
			return getScore(searchHorizontalWord(row, col));
		}
		else //search in column
		{
			return getScore(searchVerticalWord(row, col));
		}
	}
	public int tryPlaceWord(Word word) {
		if(word == null || word.getTiles() == null ||  !this.boardLegal(word))
		{
			return 0;
		}
		
		Tile[] tiles = word.getTiles();
		
		int row = word.getRow(), col = word.getCol();
		int curr = 0;
		boolean vertical = word.isVertical();
		int totalScore = 0;
		while(isInValidBoundaries(row, col) && curr < tiles.length)
		{
			if(this.board[row][col] == null)
			{
				board[row][col] = tiles[curr];
				int newWordScore = tryFindNewWords(row, col, vertical);
				if(newWordScore != 0)
				{
					++this.numWords;
					totalScore += newWordScore;
				}
			}
			if(vertical)
			{
				++row;
			}
			else
			{
				++col;
			}
			++curr;
		}
		++this.numWords;
		return totalScore + getScore(word);
	}

	public Tile[][] getTiles()
	{
		Tile copyBoard[][] = new Tile[sizeBoard][sizeBoard];
		for(int i = 0; i < sizeBoard;i++)
		{
			for(int j = 0; j < sizeBoard;j++)
			{
				copyBoard[i][j] = board[i][j];
			}
		}
		return copyBoard;
	}
	
	private boolean isTripleword(int row, int col)
	{
		for(int i = 0; i < tripleWord.length;i++)
		{
			int r = tripleWord[i][0];
			int c = tripleWord[i][1];
			if ((row == r || sizeBoard - 1 - r == row) && (col == c || sizeBoard - 1 - c == col))
			{
				return true;
			}
		}
		return false;
	}
	private boolean isDoubleLetter(int row, int col)
	{
		for(int i = 0; i < duplicatedLetter.length;i++)
		{
			int r = duplicatedLetter[i][0];
			int c = duplicatedLetter[i][1];
			if ((row == r || sizeBoard - 1 - r == row) && (col == c || sizeBoard - 1 - c == col))
			{
				return true;
			}
		}
		return false;
	}
	private boolean isTripleLetter(int row, int col)
	{
		for(int i = 0; i < tripleLetter.length;i++)
		{
			int r = tripleLetter[i][0];
			int c = tripleLetter[i][1];
			if ((row == r || sizeBoard - 1 - r == row) && (col == c || sizeBoard - 1 - c == col))
			{
				return true;
			}
		}
		return false;
	}
	private boolean isDoubleWord(int row, int col)
	{
		for(int i = 0; i < doubleWord.length;i++)
		{
			int r = doubleWord[i][0];
			int c = doubleWord[i][1];
			if ((row == r || sizeBoard - 1 - r == row) && (col == c || sizeBoard - 1 - c == col))
			{
				return true;
			}
		}
		return false;
	}
	public int getScore(Word word)
	{
		if(word == null || word.getTiles() == null)
		{
			return 0;
		}
		Tile[] tiles = word.getTiles();
		int row = word.getRow(), col = word.getCol();
		int curr = 0;
		boolean vertical = word.isVertical();
		int totalScore = 0;
		boolean doubleWord = false, tripleWord = false, onStar = false;
		while(curr < tiles.length)
		{
				int score = board[row][col].score;
				totalScore += score;
				if(row == sizeBoard / 2 && col == sizeBoard / 2)
				{
					onStar = true;
				}
				if(isDoubleLetter(row, col))
				{
					totalScore += score;
				}
				if(isTripleLetter(row, col))
				{
					totalScore += score * 2;
				}
				if(isDoubleWord(row, col))
				{
					doubleWord = true;
				}
				if(isTripleword(row, col))
				{
					tripleWord = true;
				}
			if(vertical)
			{
				++row;
			}
			else
			{
				++col;
			}
			++curr;
		}
		if(this.numWords == 1 && onStar)
		{
			return totalScore * 2;
		}
		if(doubleWord)
		{
			return totalScore * 2;
		}
		if(tripleWord)
		{
			return totalScore * 3;
		}
		return totalScore;
	}

	private Board()
	{
		this.board = new Tile[sizeBoard][sizeBoard];
	}

	public static Board getBoard()
	{
		if(Board.myBoard == null)
		{
			Board.myBoard = new Board();
		}
		return Board.myBoard;
	}
}
