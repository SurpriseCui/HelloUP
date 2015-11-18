package com.jawbone.helloup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.jawbone.upplatformsdk.datamodel.Move_ticks;
import com.jawbone.upplatformsdk.datamodel.Moves_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MoveTicksActivity extends Activity{
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	@SuppressWarnings("deprecation")
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_move_ticks);
		
		Intent intent = this.getIntent(); 
		final Move_ticks mt = (Move_ticks)intent.getSerializableExtra("Moveticks");
		
		System.out.println(mt.getData().getItems().size());
		
		TableLayout tableLayout = (TableLayout)findViewById(R.id.TableLayout_ticks);
		
		tableLayout.setStretchAllColumns(true);
		
		for(int row = 0; row < mt.getData().getItems().size(); row ++){
//			System.out.println(row);
			TableRow tableRow = new TableRow(MoveTicksActivity.this);
			tableRow.setBackgroundColor(Color.rgb(222, 220, 210));
			
		    String content = "";  
		    
			for(int col = 0; col < 5; col ++){
				TextView tv = new TextView(MoveTicksActivity.this);
				if(col == 0){
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(mt.getData().getItems().get(row).getTime());
					
					content = mt.getData().getItems().get(row).getTime() + "	";
					System.out.println(content);
					tableRow.addView(tv);
					try {  
			            /* 根据用户提供的文件名，以及文件的应用模式，打开一个输出流.文件不存系统会为你创建一个的， 
			             * 至于为什么这个地方还有FileNotFoundException抛出，我也比较纳闷。在Context中是这样定义的 
			             *   public abstract FileOutputStream openFileOutput(String name, int mode) 
			             *   throws FileNotFoundException; 
			             * openFileOutput(String name, int mode); 
			             * 第一个参数，代表文件名称，注意这里的文件名称不能包括任何的/或者/这种分隔符，只能是文件名 
			             *          该文件会被保存在/data/data/应用名称/files/chenzheng_java.txt 
			             * 第二个参数，代表文件的操作模式 
			             *          MODE_PRIVATE 私有（只能创建它的应用访问） 重复写入时会文件覆盖 
			             *          MODE_APPEND  私有   重复写入时会在文件的末尾进行追加，而不是覆盖掉原来的文件 
			             *          MODE_WORLD_READABLE 公用  可读 
			             *          MODE_WORLD_WRITEABLE 公用 可读写 
			             *  */  
			            FileOutputStream outputStream = openFileOutput("test.txt", Context.MODE_APPEND);  
			            outputStream.write(content.getBytes());  
			            outputStream.flush();  
			            outputStream.close();  
//			            Toast.makeText(FileTest.this, "保存成功", Toast.LENGTH_LONG).show();  
			        } catch (FileNotFoundException e) {  
			            e.printStackTrace();  
			        } catch (IOException e) {  
			            e.printStackTrace();  
			        }  
				}
				else if(col == 1){
//					System.out.println(Integer.toString(ml.getData().getItems().get(row).getDate()));
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(Double.toString(mt.getData().getItems().get(row).getDistence()));
					content = Double.toString(mt.getData().getItems().get(row).getDistence())+ "	";
					System.out.println(content);
					tableRow.addView(tv);
					try {  
			            /* 根据用户提供的文件名，以及文件的应用模式，打开一个输出流.文件不存系统会为你创建一个的， 
			             * 至于为什么这个地方还有FileNotFoundException抛出，我也比较纳闷。在Context中是这样定义的 
			             *   public abstract FileOutputStream openFileOutput(String name, int mode) 
			             *   throws FileNotFoundException; 
			             * openFileOutput(String name, int mode); 
			             * 第一个参数，代表文件名称，注意这里的文件名称不能包括任何的/或者/这种分隔符，只能是文件名 
			             *          该文件会被保存在/data/data/应用名称/files/chenzheng_java.txt 
			             * 第二个参数，代表文件的操作模式 
			             *          MODE_PRIVATE 私有（只能创建它的应用访问） 重复写入时会文件覆盖 
			             *          MODE_APPEND  私有   重复写入时会在文件的末尾进行追加，而不是覆盖掉原来的文件 
			             *          MODE_WORLD_READABLE 公用  可读 
			             *          MODE_WORLD_WRITEABLE 公用 可读写 
			             *  */  
			            FileOutputStream outputStream = openFileOutput("test.txt", Context.MODE_APPEND);  
			            outputStream.write(content.getBytes());  
			            outputStream.flush();  
			            outputStream.close();  
//			            Toast.makeText(FileTest.this, "保存成功", Toast.LENGTH_LONG).show();  
			        } catch (FileNotFoundException e) {  
			            e.printStackTrace();  
			        } catch (IOException e) {  
			            e.printStackTrace();  
			        }  
				}
				else if(col == 2){
//					System.out.println(Integer.toString(ml.getData().getItems().get(row).getdetails().getSteps()));
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(Double.toString(mt.getData().getItems().get(row).getSteps()));
					content = Double.toString(mt.getData().getItems().get(row).getSteps())+ "	";
					System.out.println(content);
					tableRow.addView(tv);
					try {  
			            /* 根据用户提供的文件名，以及文件的应用模式，打开一个输出流.文件不存系统会为你创建一个的， 
			             * 至于为什么这个地方还有FileNotFoundException抛出，我也比较纳闷。在Context中是这样定义的 
			             *   public abstract FileOutputStream openFileOutput(String name, int mode) 
			             *   throws FileNotFoundException; 
			             * openFileOutput(String name, int mode); 
			             * 第一个参数，代表文件名称，注意这里的文件名称不能包括任何的/或者/这种分隔符，只能是文件名 
			             *          该文件会被保存在/data/data/应用名称/files/chenzheng_java.txt 
			             * 第二个参数，代表文件的操作模式 
			             *          MODE_PRIVATE 私有（只能创建它的应用访问） 重复写入时会文件覆盖 
			             *          MODE_APPEND  私有   重复写入时会在文件的末尾进行追加，而不是覆盖掉原来的文件 
			             *          MODE_WORLD_READABLE 公用  可读 
			             *          MODE_WORLD_WRITEABLE 公用 可读写 
			             *  */  
			            FileOutputStream outputStream = openFileOutput("test.txt", Context.MODE_APPEND);  
			            outputStream.write(content.getBytes());  
			            outputStream.flush();  
			            outputStream.close();  
//			            Toast.makeText(FileTest.this, "保存成功", Toast.LENGTH_LONG).show();  
			        } catch (FileNotFoundException e) {  
			            e.printStackTrace();  
			        } catch (IOException e) {  
			            e.printStackTrace();  
			        }  
				}
				else if(col == 3){
//					System.out.println(Double.toString(ml.getData().getItems().get(row).getdetails().getDistance()));
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(Double.toString(mt.getData().getItems().get(row).getActive_time()));
					content = Double.toString(mt.getData().getItems().get(row).getActive_time())+ "	";
					System.out.println(content);
					tableRow.addView(tv);
					try {  
			            /* 根据用户提供的文件名，以及文件的应用模式，打开一个输出流.文件不存系统会为你创建一个的， 
			             * 至于为什么这个地方还有FileNotFoundException抛出，我也比较纳闷。在Context中是这样定义的 
			             *   public abstract FileOutputStream openFileOutput(String name, int mode) 
			             *   throws FileNotFoundException; 
			             * openFileOutput(String name, int mode); 
			             * 第一个参数，代表文件名称，注意这里的文件名称不能包括任何的/或者/这种分隔符，只能是文件名 
			             *          该文件会被保存在/data/data/应用名称/files/chenzheng_java.txt 
			             * 第二个参数，代表文件的操作模式 
			             *          MODE_PRIVATE 私有（只能创建它的应用访问） 重复写入时会文件覆盖 
			             *          MODE_APPEND  私有   重复写入时会在文件的末尾进行追加，而不是覆盖掉原来的文件 
			             *          MODE_WORLD_READABLE 公用  可读 
			             *          MODE_WORLD_WRITEABLE 公用 可读写 
			             *  */  
			            FileOutputStream outputStream = openFileOutput("test.txt", Context.MODE_APPEND);  
			            outputStream.write(content.getBytes());  
			            outputStream.flush();  
			            outputStream.close();  
//			            Toast.makeText(FileTest.this, "保存成功", Toast.LENGTH_LONG).show();  
			        } catch (FileNotFoundException e) {  
			            e.printStackTrace();  
			        } catch (IOException e) {  
			            e.printStackTrace();  
			        }  
				}
				else if(col == 4){
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(Double.toString(mt.getData().getItems().get(row).getCalories()));
					content = Double.toString(mt.getData().getItems().get(row).getCalories())+ "\r\n";
					tableRow.addView(tv);
					try {  
			            /* 根据用户提供的文件名，以及文件的应用模式，打开一个输出流.文件不存系统会为你创建一个的， 
			             * 至于为什么这个地方还有FileNotFoundException抛出，我也比较纳闷。在Context中是这样定义的 
			             *   public abstract FileOutputStream openFileOutput(String name, int mode) 
			             *   throws FileNotFoundException; 
			             * openFileOutput(String name, int mode); 
			             * 第一个参数，代表文件名称，注意这里的文件名称不能包括任何的/或者/这种分隔符，只能是文件名 
			             *          该文件会被保存在/data/data/应用名称/files/chenzheng_java.txt 
			             * 第二个参数，代表文件的操作模式 
			             *          MODE_PRIVATE 私有（只能创建它的应用访问） 重复写入时会文件覆盖 
			             *          MODE_APPEND  私有   重复写入时会在文件的末尾进行追加，而不是覆盖掉原来的文件 
			             *          MODE_WORLD_READABLE 公用  可读 
			             *          MODE_WORLD_WRITEABLE 公用 可读写 
			             *  */  
			            FileOutputStream outputStream = openFileOutput("test.txt", Context.MODE_APPEND);  
			            outputStream.write(content.getBytes());  
			            outputStream.flush();  
			            outputStream.close();  
//			            Toast.makeText(FileTest.this, "保存成功", Toast.LENGTH_LONG).show();  
			        } catch (FileNotFoundException e) {  
			            e.printStackTrace();  
			        } catch (IOException e) {  
			            e.printStackTrace();  
			        }  
				}
			}
			
			
	   
			tableLayout.addView(tableRow, new TableLayout.LayoutParams(FP, WC));
		}
	}
}
