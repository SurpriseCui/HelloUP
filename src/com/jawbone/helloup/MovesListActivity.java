package com.jawbone.helloup;

import com.jawbone.upplatformsdk.datamodel.Moves_list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MovesListActivity extends Activity {
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	@SuppressWarnings("deprecation")
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moves_list);
		
		Intent intent = this.getIntent(); 
		final Moves_list ml = (Moves_list)intent.getSerializableExtra("Moveslist");
		
		System.out.println(ml.getData().getItems().size());
		
		TableLayout tableLayout = (TableLayout)findViewById(R.id.TableLayout_list);
		
		tableLayout.setStretchAllColumns(true);
		
		for(int row = 0; row < ml.getData().getItems().size(); row ++){
//			System.out.println(row);
			TableRow tableRow = new TableRow(MovesListActivity.this);
			tableRow.setBackgroundColor(Color.rgb(222, 220, 210));
			
			for(int col = 0; col < 3; col ++){
				TextView tv = new TextView(MovesListActivity.this);
				if(col == 0){
//					System.out.println(Integer.toString(ml.getData().getItems().get(row).getDate()));
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(Integer.toString(ml.getData().getItems().get(row).getDate()));
					tableRow.addView(tv);
				}
				else if(col == 1){
//					System.out.println(Integer.toString(ml.getData().getItems().get(row).getdetails().getSteps()));
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(Integer.toString(ml.getData().getItems().get(row).getdetails().getSteps()));
					tableRow.addView(tv);
				}
				else if(col == 2){
//					System.out.println(Double.toString(ml.getData().getItems().get(row).getdetails().getDistance()));
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(Double.toString(ml.getData().getItems().get(row).getdetails().getDistance()));
					tableRow.addView(tv);
				}
			}
			
			tableLayout.addView(tableRow, new TableLayout.LayoutParams(FP, WC));
		}
	}

}
