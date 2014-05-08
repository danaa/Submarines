package com.android.submarines.gui;


import com.android.submarines.events.SquareChangeEvent;
import com.android.submarines.logic.Game;
import com.android.submarines.logic.Square;
import com.android.submarines.logic.Square.State;
import com.android.submarines.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


public class ShipsActivity extends Activity 
{
	
	Game _game;
	
	// UI data members
	ImageAdapter _imageAdapter;
	ArrayAdapter<String> _adapterUp;
	ArrayAdapter<String> _adapterSide;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GridView gridView = (GridView)findViewById(R.id.gridView);
        GridView gridViewUp = (GridView)findViewById(R.id.gridViewup);
        GridView gridViewSide = (GridView)findViewById(R.id.gridViewSide);
        
        _imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(_imageAdapter);
        
        _adapterUp = new ArrayAdapter<String>(this, R.layout.side_list_item);
        gridViewUp.setAdapter(_adapterUp);
        
        _adapterSide = new ArrayAdapter<String>(this, R.layout.side_list_item); // custom text view because of row height
        gridViewSide.setAdapter(_adapterSide);
        
        _game = new Game();
		_game.initBoard();
		
		// set the counters
		this.setHorizontalCounters(_game.getHorizontal());
		this.setVerticalCounters(_game.getVertical());
		
		// configure button
		Button checkButton = (Button)findViewById(R.id.checkButton);
		checkButton.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				if(_game.checkWin())
				{
					Toast.makeText(ShipsActivity.this, "Congratulations! You did it!", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(ShipsActivity.this, "Incorrect, try again", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		// fill board 
		_imageAdapter.setAdapter(_game.getGameBoard());
		//_imageAdapter.SetAdapterTest(_game.getGameBoard());
		
		// on click listener for the grid
		gridView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				Square[][] board = _game.getGameBoard();
				Square s = board[position / Game.BOARD_SIZE][position % Game.BOARD_SIZE];
				Square.State ustate = s.getUserState();
				
				if(s.isClickable())
				{
			
					Square.State newState = null;
					switch(ustate)
					{
					case UNKNOWN:
						newState = State.EMPTY;
						break;
					case EMPTY:
						newState = State.BULK_OCC;
						break;
					case BULK_OCC:
						newState = State.SINGLE_OCC;
						break;
					case SINGLE_OCC:
						newState = State.UP_OCC;
						break;
					case UP_OCC:
						newState = State.LEFT_OCC;
						break;
					case LEFT_OCC:
						newState = State.DOWN_OCC;
						break;
					case DOWN_OCC:
						newState = State.RIGHT_OCC;
						break;
					case RIGHT_OCC:
						newState = State.UNKNOWN;
						break;
					}
				
					s.setUserState(newState);
					_imageAdapter.squareChanged(new SquareChangeEvent(newState,position));
				}
					
			}
			
		});
		
    }
    
    /**
     * 
     * @param counters
     */
    private void setHorizontalCounters(int[] counters)
    {
    	for(int i = 0; i < Game.BOARD_SIZE; i++)
    	{
    		_adapterUp.add(Integer.toString(counters[i]));
    	}
    	_adapterUp.notifyDataSetChanged();
    }
    
    /**
     * 
     * @param counters
     */
    private void setVerticalCounters(int[] counters)
    {
    	for(int i = 0; i < Game.BOARD_SIZE; i++)
    	{
    		_adapterSide.add(Integer.toString(counters[i]));
    	}
    	_adapterSide.notifyDataSetChanged();
    }
    
    
}