package com.android.submarines.gui;


import java.util.Arrays;
import com.android.submarines.events.SquareChangeEvent;
import com.android.submarines.logic.Game;
import com.android.submarines.logic.Square;
import com.android.submarines.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter 
{

	private static final int SQUARE_SIZE = 60;
	
	private Context _mContext;
	private Integer[] _mThumbIds;
	
	public ImageAdapter(Context c)
	{
		_mThumbIds = new Integer[Game.BOARD_SIZE * Game.BOARD_SIZE];
		Arrays.fill(_mThumbIds, R.drawable.unknown);
		_mContext = c;
	}

	public int getCount() 
	{
		return _mThumbIds.length;
	}

	public Object getItem(int position) 
	{
		return _mThumbIds[position];
	}

	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ImageView view = new ImageView(_mContext);
        view.setImageResource(_mThumbIds[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new GridView.LayoutParams(SQUARE_SIZE, SQUARE_SIZE));
        return view;
	}
	
	
	/**
	 * 
	 * @param board
	 */
	public void setAdapter(Square[][] board)
	{
		for(int i = 0; i < _mThumbIds.length; i++)
		{
			Square curr = board[i / Game.BOARD_SIZE][i % Game.BOARD_SIZE];
			Square.State state = curr.getUserState();
			
			this.changeStateByIndex(i, state);
			
			this.notifyDataSetChanged();
		}
	}
	
	public void SetAdapterTest(Square[][] board)
	{
		for(int i = 0; i < _mThumbIds.length; i++)
		{
			Square curr = board[i / Game.BOARD_SIZE][i % Game.BOARD_SIZE];
			Square.State state = curr.getGameState();
			
			this.changeStateByIndex(i, state);
			
			this.notifyDataSetChanged();
		}
	}

	
	// called by the game activity when user clicks on a square
	public void squareChanged(SquareChangeEvent event)
	{
		Square.State state = event.getNewState();
		int pos = event.getPosition();
		
		this.changeStateByIndex(pos, state);
		
		this.notifyDataSetChanged();
	}
	
	private void changeStateByIndex(int index, Square.State state) {
		switch(state)
		{
		case UNKNOWN:
			_mThumbIds[index] = R.drawable.unknown;
			break;
		case EMPTY:
			_mThumbIds[index] = R.drawable.blue;
			break;
		case BULK_OCC:
			_mThumbIds[index] = R.drawable.black;
			break;
		case SINGLE_OCC:
			_mThumbIds[index] = R.drawable.one;
			break;
		case UP_OCC:
			_mThumbIds[index] = R.drawable.up;
			break;
		case LEFT_OCC:
			_mThumbIds[index] = R.drawable.left;
			break;
		case DOWN_OCC:
			_mThumbIds[index] = R.drawable.down;
			break;
		case RIGHT_OCC:
			_mThumbIds[index] = R.drawable.right;
			break;
		}
	}

}
