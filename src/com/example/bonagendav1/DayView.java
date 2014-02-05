package com.example.bonagendav1;

import DBHelper.DBAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class DayView extends Activity {

	private DBAdapter dbHelper = null;
	private LAdapter adapter = null;
	private Cursor cursor = null;
	private ListView listView;
	String pt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_view);
		TextView tv = (TextView) findViewById(R.id.day);
		pt = getIntent().getStringExtra("day");
		tv.setText(pt);
		listView = (ListView) findViewById(R.id.listView);
		configureBackButton();
		configureAddButton();
		
		dbHelper = new DBAdapter(DayView.this);
		dbHelper.open();
		cursor = dbHelper.getDayEvents(pt);
		startManagingCursor(cursor);
		adapter = new LAdapter(cursor);

		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(onLongListClick);
		listView.setOnItemClickListener(onListClick);
	}

	// on click method for list
		private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Cursor c = dbHelper.getNote(id);
				System.out.println("Db " + c.getString(0) + "list " + id);
				String title = c.getString(1);
				String note = c.getString(2);

				Intent i = new Intent(DayView.this,
						UpdateNoteActivity.class);
				i.putExtra("id", id);
				i.putExtra("title", title);
				i.putExtra("note", note);

				startActivity(i);
			}
		};
	
	private void configureBackButton() {
		ImageButton backButton = (ImageButton) this.findViewById(R.id.back);
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				DayView.this.finish();
			}
		});
	}
	private void configureAddButton() {
		Button backButton = (Button) this.findViewById(R.id.btnAdd);
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),CreateActivity.class);
				intent.putExtra("day", pt);
				startActivity(intent);
			}
		});
	}
	
	
	// class to place cursor into a listview
		class LAdapter extends CursorAdapter {
			public LAdapter(Cursor c) {
				super(DayView.this, c);

			}

			@Override
			public void bindView(View view, Context context, Cursor cursor) {
				StringHolder holder = (StringHolder) view.getTag();
				holder.populateFrom(cursor, dbHelper);
			}

			@Override
			public View newView(Context context, Cursor cursor, ViewGroup parent) {
				LayoutInflater inflater = getLayoutInflater();
				View row = inflater.inflate(R.layout.my_row, parent, false);
				// calls stringholder to populate each row with data
				StringHolder holder = new StringHolder(row);
				row.setTag(holder);
				return (row);
			}

		}

		// class that is used to hold data in list
		static class StringHolder {
			private TextView title = null;

			StringHolder(View row) {
				title = (TextView) row.findViewById(R.id.title);
			}

			void populateFrom(Cursor c, DBAdapter r) {
				// populates rows with data
			//	title.setText(r.getTitle(c));
				title.setText(c.getString(1));

			}
		}
		
		public void updateList(long id) {
			dbHelper.deleteEvent(id);
			runOnUiThread(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub

					adapter.notifyDataSetChanged();
					listView.refreshDrawableState();
					listView.invalidate();
				}
			});
		}
		
		
		private AdapterView.OnItemLongClickListener onLongListClick = new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				Cursor c = dbHelper.getNote(id);
				System.out.println("long " + id);
				updateList(id);
				adapter.notifyDataSetChanged();
				listView.refreshDrawableState();
				listView.invalidate();
				cursor.requery();
				return true;
			}
		};

}
