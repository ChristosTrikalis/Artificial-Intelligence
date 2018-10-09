package gr.aueb.cs.ai.tiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class State
{
	private int dimension;
	private int [][] tiles;
    //The following variables hold the position of the empty tile
    //We use seperate variables for these to avoid searching the board every time
	private int emptyTileRow;
	private int emptyTileColumn;
	
	public State()
	{
		this.dimension = -1;
		this.tiles = null;
		this.emptyTileRow = -1;
		this.emptyTileColumn = -1;
	}
	
	public State(int [][] tiles)
	{
		this.dimension = tiles.length;
		this.tiles = new int[this.dimension][this.dimension];
		for(int i=0; i<this.dimension; i++)
		{
			for(int j=0; j<this.dimension; j++)
			{
				this.tiles[i][j] = tiles[i][j];
				if(this.tiles[i][j] == 0)
				{
					this.emptyTileRow = i;
					this.emptyTileColumn = j;
				}
			}
		}
	}
	
	/***
	 * For initialization of the problem
	 * @param dimension The dimension of the board
	 * @param randomized If true we get a random board.
	 *                   Otherwise a 3 X 3 board with fixed positions
     *                   The dimension parameter is ignored in case of a false value.
	 */
	public State(int dimension, boolean randomized)
	{
		if(randomized)
		{
			this.dimension = dimension;
			ArrayList<Integer> tilesToUse = new ArrayList<Integer>(this.dimension);
			for(int i=0; i<this.dimension * this.dimension; i++)
			{
				tilesToUse.add(i); 
			}
			this.tiles = new int[this.dimension][this.dimension];
			Random random = new Random();
			for(int i=0; i<this.dimension; i++)
			{
				for(int j=0; j<this.dimension; j++)
				{
					Collections.shuffle(tilesToUse);
					this.tiles[i][j] = tilesToUse.remove(random.nextInt(tilesToUse.size()));
					if(this.tiles[i][j] == 0)
					{
						this.emptyTileRow = i;
						this.emptyTileColumn = j;
					}
				}
			}
		}
		else
		{
			this.dimension = 3;
			this.tiles = new int[this.dimension][this.dimension];
			this.tiles[0][0] = 8;
			this.tiles[0][1] = 3;
			this.tiles[0][2] = 5;
			this.tiles[1][0] = 4;
			this.tiles[1][1] = 1;
			this.tiles[1][2] = 7;
			this.tiles[2][0] = 2;
			this.tiles[2][1] = 0;
			this.tiles[2][2] = 6;
			this.emptyTileRow = 2;
			this.emptyTileColumn = 1;
		}
	}
	
	public int getDimension()
	{
		return this.dimension;
	}
	
	public int[][] getTiles()
	{
		return this.tiles;
	}
	
	public int getEmptyTileRow()
	{
		return this.emptyTileRow;
	}
	
	public int getEmptyTileColumn()
	{
		return emptyTileColumn;
	}
	
	public void setDimension(int dimension)
	{
		this.dimension = dimension;
	}
	
	public void setTiles(int[][] tiles)
	{
		this.dimension = tiles.length;
		this.tiles = new int[this.dimension][this.dimension];
		for(int i=0; i<this.dimension; i++)
		{
			for(int j=0; j<this.dimension; j++)
			{
				this.tiles[i][j] = tiles[i][j];
				if(this.tiles[i][j] == 0)
				{
					this.emptyTileRow = i;
					this.emptyTileColumn = j;
				}
			}
		}
	}
	
	public void setEmptyTileRow(int emptyTileRow)
	{
		this.emptyTileRow = emptyTileRow;
	}
	
	public void setEmptyTileColumn(int emptyTileColumn)
	{
		this.emptyTileColumn = emptyTileColumn;
	}
	
	public boolean moveUp()
	{
		if(this.emptyTileRow == 0)
		{
			//If move is not valid return false
			return false;
		}
		//Move the tile over the empty position down a space
		this.tiles[this.emptyTileRow][this.emptyTileColumn] = this.tiles[this.emptyTileRow - 1][this.emptyTileColumn];
		//Move the empty tile up a space
		this.emptyTileRow--;
		this.tiles[this.emptyTileRow][this.emptyTileColumn] = 0;
		return true;
	}
	
	public boolean moveDown()
	{
		/**
		* Add the appropriate code
		*/
	}
	
	public boolean moveLeft()
	{
		/**
		* Add the appropriate code
		*/
	}
	
	public boolean moveRight()
	{
		/**
		* Add the appropriate code
		*/
	}

    /***
     * Generates the children-states of the this state
     * Each child-state is created by making one (and only one) possible move in the board
     */
	public ArrayList<State> getChildren()
	{
		ArrayList<State> children = new ArrayList<State>();
		State child = new State(this.tiles);
		if(child.moveUp())
		{
			children.add(child);
		}
		/**
		* Add the appropriate code to add the rest of the children
		*/
		return children;
	}

    /***
     * Checks whether a state is terminal
     * If all the tiles in the board are in the correct position, there is no
     * need for any further moves
     */
	public boolean isTerminal()
	{
		for(int i=0; i<this.dimension; i++)
		{
			for(int j=0; j<this.dimension; j++)
			{
				if((i == this.dimension - 1) && (j == this.dimension - 1))
				{
					if(this.tiles[i][j] != 0)
					{
						return false;
					}
				}
				else
				{
					if(this.tiles[i][j] != (this.dimension * i) + j + 1)
					{
						return false;
					}
				}
			}
		}
		return true;
	}

    //Prints the contents of the board
	public void print()
	{
		System.out.println("------------------------------------");
		for(int i=0; i<this.dimension; i++)
		{
			for(int j=0; j<this.dimension; j++)
			{
				if(this.tiles[i][j] == 0)
				{
					System.out.print(' ');
				}
				else
				{
					System.out.print(this.tiles[i][j]);
				}
				if(j < this.dimension - 1)
				{
					System.out.print('\t');
				}
			}
			System.out.println();
		}
		System.out.println("------------------------------------");
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this.dimension != ((State)obj).dimension)
		{
			return false;
		}
		if(this.emptyTileRow != ((State)obj).emptyTileRow)
		{
			return false;
		}
		if(this.emptyTileColumn != ((State)obj).emptyTileColumn)
		{
			return false;
		}
		for(int i=0; i<this.dimension; i++)
		{
			for(int j=0; j<this.dimension; j++)
			{
				if(this.tiles[i][j] != ((State)obj).tiles[i][j])
				{
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return this.emptyTileRow + this.emptyTileColumn + this.dimension;
	}
}
