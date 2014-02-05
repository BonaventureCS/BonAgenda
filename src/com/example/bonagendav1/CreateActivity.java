package com.example.bonagendav1;

import DBHelper.DBAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends Activity {

	DBAdapter db = new DBAdapter(this);
	EditText title;
	EditText date;
	EditText time;
	EditText type;
	EditText description;
	String pt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		title = (EditText) findViewById(R.id.title);
		date = (EditText) findViewById(R.id.date);
		time = (EditText) findViewById(R.id.time);
		type = (EditText) findViewById(R.id.type);
		description = (EditText) findViewById(R.id.description);
		 pt = getIntent().getStringExtra("day");
		date.setText(pt);

	}

	public void addEvent(View v) {
		Log.d("test", "adding");
		// get data from form
		// EditText nameTxt = (EditText)findViewById(R.id.editTitle);
		// EditText notesTxt = (EditText)findViewById(R.id.editNote);

		db.open();
		long id = db.insertEvent(title.getText().toString(), date.getText()
				.toString(), description.getText().toString(), time.getText()
				.toString(), type.getText().toString());
		db.close();

		// title.setText("");
		// note.setText("");
		Toast.makeText(CreateActivity.this, "Event Added", Toast.LENGTH_LONG)
				.show();
		this.finish();

	}

}
