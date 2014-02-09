package com.example.bonagendav1;

import DBHelper.DBAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends Activity {

	DBAdapter db = new DBAdapter(this);
	EditText title;
	EditText note;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_note);
		title = (EditText)findViewById(R.id.editTitle);
		note = (EditText)findViewById(R.id.editNote);
	}

	public void addNote(View v)
	{
		Log.d("test", "adding");
	   	//get data from form
	  // 	EditText nameTxt = (EditText)findViewById(R.id.editTitle);
	  // 	EditText notesTxt = (EditText)findViewById(R.id.editNote);

	       db.open();        
	       long id = db.insertNote(title.getText().toString(), note.getText().toString());        
	       db.close();
	       
	    title.setText("");
	    note.setText("");
        Toast.makeText(AddNoteActivity.this,"Note Added", Toast.LENGTH_LONG).show();
        
        this.finish();

	}
}
