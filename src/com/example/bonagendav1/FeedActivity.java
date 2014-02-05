package com.example.bonagendav1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FeedActivity extends ListActivity {

	String[] event = {"Comp Theory HW", "Math", "Meeting in Plassman", "Cheese"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.mytext,event));
		 configureCalendarButton();
		 configureNotesButton();
		 Button note = (Button) findViewById(R.id.btnFeed);
		 note.setClickable(false);
	}

	
	public void onListItemClick(ListView l, View v, int position, long id){
		Toast.makeText(this, "You have selected " + event[position], Toast.LENGTH_LONG).show();
	}

	

	public void configureCalendarButton() {
		Button b = (Button) findViewById(R.id.btnCalendar);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						CalendarView.class);
				startActivity(intent);
			}
		});
	}

	public void configureNotesButton() {
		Button b = (Button) findViewById(R.id.btnNotes);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						MainNoteActivity.class);
				startActivity(intent);
			}
		});
	}
}
