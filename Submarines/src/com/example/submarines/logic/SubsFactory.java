package com.example.submarines.logic;

import java.util.Random;

public class SubsFactory 
{
	//enum Direction {HORIZONTAL, VERTICAL};
	
	public static Sub createSub(Sub.Type type)
	{
		int x, y;
		
		Random rand = new Random();
		x = rand.nextInt(Game.BOARD_SIZE - type.size() + 1);
		y = rand.nextInt(Game.BOARD_SIZE);
		
		int direction = rand.nextInt(2);
		Sub sub;
		
		if(direction == Sub.Direction.VERTICAL.ordinal())
		{
			sub = new Sub(type, Sub.Direction.VERTICAL);
			int temp = x;
			x = y;
			y = temp;
		}// else remains the same
		else
		{
			sub = new Sub(type, Sub.Direction.HORIZONTAL);
		}
		
		for(int i = 0; i < type.size(); i++)
		{	
			if(direction == Sub.Direction.HORIZONTAL.ordinal())
				sub.addPoint(x + i, y);
			else // vertical
				sub.addPoint(x, y + i);
		}
		
		return sub;
	}
}
