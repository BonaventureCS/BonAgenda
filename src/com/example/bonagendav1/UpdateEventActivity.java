package com.example.bonagendav1;

import DBHelper.DBAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateEventActivity extends Activity {

	String passedDate = null;
	String passedTitle = null;
	String passedTime = null;
	String passedType = null;
	String passedDesc = null;
	long passedID = 0;
	EditText date;
	EditText title;
	EditText time;
	EditText type;
	EditText desc;
	DBAdapter db = new DBAdapter(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_event);

		date = (EditText) findViewById(R.id.date);
		title = (EditText) findViewById(R.id.title);
		time = (EditText) findViewById(R.id.time);
		type = (EditText) findViewById(R.id.type);
		desc = (EditText) findViewById(R.id.description);

		passedDate = getIntent().getStringExtra("date");
		passedTitle = getIntent().getStringExtra("title");
		passedTime = getIntent().getStringExtra("note");
		passedType = getIntent().getStringExtra("type");
		passedDesc = getIntent().getStringExtra("desc");
		passedID = getIntent().getLongExtra("id", passedID);

		date.setText(passedDate);
		title.setText(passedTitle);
		time.setText(passedTime);
		type.setText(passedType);
		desc.setText(passedDesc);
	}

	public void updateEvent(View v) {
		Log.d("test", "updating");
		// get data from form
		db.open();
		boolean pass = db.updateEvent(passedID, date.getText().toString(),
				title.getText().toString(), time.getText().toString(), type
						.getText().toString(), desc.getText().toString());
		db.close();
		
		Toast.makeText(UpdateEventActivity.this, "Event Updated",
				Toast.LENGTH_LONG).show();
		UpdateEventActivity.this.finish();
	}

	public void cancelEvent(View v) {
		UpdateEventActivity.this.finish();
	}

}
