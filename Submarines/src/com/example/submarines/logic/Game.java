package com.example.submarines.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.example.submarines.logic.Sub.Type;

public class Game 
{
	
	public static final int BOARD_SIZE = 10;
	private static final int NUM_SUBS= 10;
	private static final int NUM_VISIBLE = 2;
	
	private Square[][] _gameBoard = new Square[BOARD_SIZE][BOARD_SIZE];
	private int[] _horizontal = new int[BOARD_SIZE];
	private int[] _vertical = new int[BOARD_SIZE];
	private ArrayList<Sub> _subs = new ArrayList<Sub>();
	
	
	public Game()
	{
		Arrays.fill(_horizontal, 0); // not really needed
		Arrays.fill(_vertical, 0);
	}
	
	/**
	 * 
	 * @return
	 */
	public Square[][] getGameBoard()
	{
		return _gameBoard;
	}
	
	public int[] getHorizontal()
	{
		return _horizontal;
	}
	
	public int[] getVertical()
	{
		return _vertical;
	}
	
	/**
	 * 
	 */
	public void initBoard()
	{
		_subs.add(SubsFactory.createSub(Type.L));
		this.addLegalSub(Type.M);
		this.addLegalSub(Type.M);
		this.addLegalSub(Type.S);
		this.addLegalSub(Type.S);
		this.addLegalSub(Type.S);
		this.addLegalSub(Type.XS);
		this.addLegalSub(Type.XS);
		this.addLegalSub(Type.XS);
		this.addLegalSub(Type.XS);
		
		this.fillBoard();
		this.fillCounters();
	}
	
	public boolean checkWin()
	{
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			for(int j = 0; j < BOARD_SIZE; j++)
			{
				Square curr = _gameBoard[i][j];
				if(!curr.getGameState().equals(curr.getUserState()))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 */
	private void fillBoard()
	{
		// go over the subs and fill the relevant squares
		for (int i = 0; i < NUM_SUBS; i++)
		{
			Sub curr = _subs.get(i);
			
			for(int j = 0; j < curr.getType().size(); j++)
			{
				Square.State state = Square.State.BULK_OCC;
				
				if(curr.getType().equals(Sub.Type.XS)) // single square sub
				{
					state = Square.State.SINGLE_OCC;
				}
				else if(j == 0) // first square of the sub
				{
					if(curr.isHorizontal())
						state = Square.State.LEFT_OCC;
					else
						state = Square.State.UP_OCC;
				}
				else if(j == curr.getType().size() - 1) // last square of the sub
				{
					if(curr.isHorizontal())
						state = Square.State.RIGHT_OCC;
					else
						state = Square.State.DOWN_OCC;
				}
				
				Square s = new Square(state, Square.State.UNKNOWN);
				s.setSub(curr);
				Point p = curr.getPointByIndex(j);
				_gameBoard[p.getY()][p.getX()] = s;
			}
		}
		
		// go over the rest of the board and fill it with empty squares
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			for(int j = 0; j < BOARD_SIZE; j++)
			{
				if(_gameBoard[i][j] == null)
					_gameBoard[i][j] = new Square(Square.State.EMPTY, Square.State.UNKNOWN);
			}
		}
		
		// choose NUM_VISABLE subs at random
		Random rand = new Random();
		int subNum = -1;
		for(int i = 0; i < NUM_VISIBLE; i++)
		{
			subNum = rand.nextInt(NUM_SUBS);
			Sub sub = _subs.get(subNum);
			
			// choose a random square from this sub to be visible
			int squareNum = rand.nextInt(sub.getType().size());
			Point p = sub.getPointByIndex(squareNum);
			Square s = _gameBoard[p.getY()][p.getX()];
			s.setUserState(s.getGameState());
			s.setClickable(false);
		}
	}
	
	/**
	 * 
	 */
	private void fillCounters()
	{
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			for(int j = 0; j < BOARD_SIZE; j++)
			{
				if(_gameBoard[i][j].isGameOccupied())
				{
					_vertical[i]++;
					_horizontal[j]++;
				}
			}
		}
	}
	
	/**
	 * adds a submarine in a legal position
	 * @param type
	 */
	private void addLegalSub(Type type)
	{
		Sub newSub;
		
		do
		{
			newSub = null;
			newSub = SubsFactory.createSub(type);
		}
		while(!isLegalBoard(newSub));
		
		_subs.add(newSub);
	}
	
	/**
	 * 
	 * @param newSub
	 * @return
	 */
	private boolean isLegalBoard(Sub newSub)
	{
		for(int i = 0; i < _subs.size(); i++)
		{
			if(!newSub.isLegal(_subs.get(i)))
				return false;
		}
		
		return true;
	}
	
	
	

}
