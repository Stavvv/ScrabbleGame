package test;

import java.util.Arrays;

public class Word {

	private Tile tiles[];
	private int row;
	private int col;
	private boolean isVertical;
	public Word(Tile[] tiles, int row, int col, boolean vertical) {
		super();
		this.tiles = tiles;
		this.row = row;
		this.col = col;
		this.isVertical = vertical;
	}
	/**
	 * @return the tiles
	 */
	public Tile[] getTiles() {
		return tiles;
	}
	/**
	 * @param tiles the tiles to set
	 */
	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
	}
	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}
	/**
	 * @param col the col to set
	 */
	public void setCol(int col) {
		this.col = col;
	}
	/**
	 * @return the vertical
	 */
	public boolean isVertical() {
		return isVertical;
	}
	/**
	 * @param vertical the vertical to set
	 */
	public void setVertical(boolean vertical) {
		this.isVertical = vertical;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		result = prime * result + Arrays.hashCode(tiles);
		result = prime * result + (isVertical ? 1231 : 1237);
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
		Word other = (Word) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		if (!Arrays.equals(tiles, other.tiles))
			return false;
		if (isVertical != other.isVertical)
			return false;
		return true;
	}
	
	
}
