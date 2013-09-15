package com.example.project1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

	private EditText savedText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		savedText = (EditText) findViewById (R.id.text_save);
	}
	
	/**
	 * Taken from edumobile.org
	 * http://www.edumobile.org/android/android-beginner-tutorials/state-persistence/
	 * 
	 * Preserves the cursor state and content of an editable text field
	 */
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs = getPreferences(0);
		String restoredText = prefs.getString("text", null);
		if(restoredText != null) {
			savedText.setText(restoredText, TextView.BufferType.EDITABLE);
			
			int selectionStart = prefs.getInt("selection-start", -1);
			int selectionEnd = prefs.getInt("selection-end", -1);
			if(selectionStart != -1 && selectionEnd != -1) {
				savedText.setSelection(selectionStart, selectionEnd);
			}
		}
	}
	
	protected void onPause() {
		super.onPause();
		SharedPreferences.Editor editor = getPreferences(0).edit();
		editor.putString("text", savedText.getText().toString());
		editor.putInt("selection-start", savedText.getSelectionStart());
		editor.putInt("selection-end", savedText.getSelectionEnd());
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Enters another activity when button is pressed */
	public void openList (View view) {
		Intent intent = new Intent (this, PictureField.class);
		startActivity(intent);
	}

}
