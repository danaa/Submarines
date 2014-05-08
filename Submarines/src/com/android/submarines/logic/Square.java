package com.android.submarines.logic;

public class Square 
{
	

	public enum State {SINGLE_OCC, UP_OCC, DOWN_OCC, RIGHT_OCC, BULK_OCC, LEFT_OCC, EMPTY, UNKNOWN};
	
	private State _gameState;
	private State _userState;
	private Sub _sub = null; // points at the submarine in this square, may be null
	private boolean _clickable = true;
	
	
	public Square(State gState, State uState)
	{
		_gameState = gState;
		_userState = uState;
	}
	
	public State getGameState()
	{
		return _gameState;
	}
	
	public State getUserState()
	{
		return _userState;
	}
	
	public boolean isGameOccupied()
	{
		return (_gameState.equals(State.BULK_OCC) || _gameState.equals(State.DOWN_OCC) || _gameState.equals(State.UP_OCC)
					|| _gameState.equals(State.LEFT_OCC) || _gameState.equals(State.RIGHT_OCC) || _gameState.equals(State.SINGLE_OCC));
	}
	
	public boolean isClickable()
	{
		return _clickable;
	}
	
	/*public boolean isUserOccupied()
	{
		return _userState == State.OCCUPIED;
	}
	
	public boolean isUserUnknown()
	{
		return _userState == State.UNKNOWN;
	}*/
	
	public void setGameState(State state)
	{
		_gameState = state;
	}
	
	public void setUserState(State state)
	{
		_userState = state;
	}
	
	public void setSub(final Sub sub)
	{
		_sub = sub;
	}
	
	public void setClickable(boolean val)
	{
		_clickable = val;
	}
}
