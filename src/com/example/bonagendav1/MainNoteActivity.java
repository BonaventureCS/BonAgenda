package com.example.bonagendav1;

import DBHelper.DBAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainNoteActivity extends Activity {

	private DBAdapter dbHelper = null;
	private LAdapter adapter = null;
	private Cursor cursor = null;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_feed);
		configureFeedButton();
		configureCalendarButton();
		listView = (ListView) findViewById(R.id.listView);
		Button addBtn = (Button) findViewById(R.id.addEvent);
		addBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainNoteActivity.this,
						AddNoteActivity.class);
				startActivity(i);
			}
		});

		dbHelper = new DBAdapter(MainNoteActivity.this);
		dbHelper.open();
		cursor = dbHelper.getAllNotes();
		startManagingCursor(cursor);
		adapter = new LAdapter(cursor);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(onListClick);
		listView.setOnItemLongClickListener(onLongListClick);
		
	}

	// on click method for list
	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Cursor c = dbHelper.getNote(id);
			System.out.println("Db " + c.getString(0) + "list " + id);
			String title = c.getString(1);
			String note = c.getString(2);

			Intent i = new Intent(MainNoteActivity.this,
					UpdateNoteActivity.class);
			i.putExtra("id", id);
			i.putExtra("title", title);
			i.putExtra("note", note);

			startActivity(i);
		}
	};
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
			// AlertDialog.Builder builder = new
			// AlertDialog.Builder(MainFeedActivity.this);
			// builder.setTitle("Delete");
			// builder.setMessage("Are you sure?");
			// builder.setPositiveButton("YES", new
			// DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog, int which) {
			// // Do nothing but close the dialog
			//
			// dialog.dismiss();
			// }
			// });
			//
			// builder.setNegativeButton("NO", new
			// DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// // Do nothing
			// boolean del = false;
			// dialog.dismiss();
			// }
			// });
			//
			// AlertDialog alert = builder.create();
			// alert.show();
			return true;
		}
	};

	public void configureCalendarButton() {
		Button b = (Button) findViewById(R.id.btnCalendar);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						CalendarView.class);
				MainNoteActivity.this.finish();
				startActivity(intent);
			}
		});
	}

	public void configureFeedButton() {
		Button b = (Button) findViewById(R.id.btnFeed);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						FeedActivity.class);
				MainNoteActivity.this.finish();
				startActivity(intent);
			}
		});
	}

	public void updateList(long id) {
		dbHelper.deleteNote(id);
		runOnUiThread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub

				adapter.notifyDataSetChanged();
				listView.refreshDrawableState();
				listView.invalidate();
			}
		});
	}

	// class to place cursor into a listview
	class LAdapter extends CursorAdapter {
		public LAdapter(Cursor c) {
			super(MainNoteActivity.this, c);

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
			title.setText(r.getTitle(c));
			System.out.println("db id " + c.getString(0));

		}
	}

}
