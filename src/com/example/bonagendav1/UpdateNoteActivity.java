package com.example.bonagendav1;

import DBHelper.DBAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNoteActivity extends Activity {

	String passedTitle = null;
	String passedNote = null;
	long passedID = 0;
	DBAdapter db = new DBAdapter(this);
	EditText title;
	EditText note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_note);
		title = (EditText) findViewById(R.id.editTitle);
		note = (EditText) findViewById(R.id.editNote);
		passedTitle = getIntent().getStringExtra("title");
		passedNote = getIntent().getStringExtra("note");
		passedID = getIntent().getLongExtra("id", passedID);

		title.setText(passedTitle);
		note.setText(passedNote);
	}

	public void updateNote(View v) {
		Log.d("test", "updating");
		// get data from form
		EditText nameTxt = (EditText) findViewById(R.id.editTitle);
		EditText notesTxt = (EditText) findViewById(R.id.editNote);

		db.open();
		boolean pass = db.updateNote(passedID, nameTxt.getText().toString(),
				notesTxt.getText().toString());
		db.close();
		title.setText("");
		note.setText("");
		Toast.makeText(UpdateNoteActivity.this, "Note Updated",
				Toast.LENGTH_LONG).show();
		UpdateNoteActivity.this.finish();
	}
	
	public void cancelNote(View v){
		UpdateNoteActivity.this.finish();
	}
}
