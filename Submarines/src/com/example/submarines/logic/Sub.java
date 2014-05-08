package com.example.submarines.logic;

import java.util.ArrayList;

public class Sub 
{
	
	public enum Direction{HORIZONTAL, VERTICAL};
	
	public enum Type
	{
		XS (1), 
		S (2), 
		M (3), 
		L (4);
		
		private final int _size;
		
		private Type(int size)
		{
			_size = size;
		}
		
		public int size()
		{
			return _size;
		}
	}
	
	// data members
	private Type _type;
	private Direction _direction;
	private ArrayList<Point> _position = new ArrayList<Point>();
	
	// c'tor
	public Sub(Type type, Direction direction)
	{
		_type = type;
		_direction = direction;
	}
	
	/**
	 * 
	 * @return
	 */
	public Type getType()
	{
		return _type;
	}
	
	public boolean isHorizontal()
	{
		return _direction.equals(Direction.HORIZONTAL);
	}
	
	public boolean isVertical()
	{
		return _direction.equals(Direction.VERTICAL);
	}

	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void addPoint(int x, int y)
	{
		_position.add(new Point(x,y));
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Point getPointByIndex(int index)
	{
		if(index < 0 || index >= _type.size())
			return null;
		
		return _position.get(index);
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public boolean isLegal(Sub other)
	{
		for(int i = 0; i < _type.size(); i++)
		{
			Point curr = _position.get(i);
			for(int j = 0; j < other._type.size(); j++)
			{
				Point otherCurr = other._position.get(j);
				int distX = Math.abs(curr.getX() - otherCurr.getX());
				int distY = Math.abs(curr.getY() - otherCurr.getY());
				if(distX <= 1 && distY <= 1)
					return false;
			}
		}
		return true;
	}
}
