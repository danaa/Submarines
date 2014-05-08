package comm.example.submarines.events;

import com.example.submarines.logic.Square;

public class SquareChangeEvent 
{
	Square.State _newState;
	int _position;
	
	public SquareChangeEvent(Square.State newState, int position) 
	{
		_newState = newState;
		_position = position;
	}
	
	public Square.State getNewState()
	{
		return _newState;
	}
	
	public int getPosition()
	{
		return _position;
	}
}
