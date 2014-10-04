package com.gaurav.wordbizz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
	
	Database db;
	InputStream is;
	SharedPreferences sharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sharedPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
		
		if(sharedPrefs.getInt("VER", -1) == -1 ) {
			db = new Database(MainActivity.this);
			new UpdateDB().execute();
		} else {
			Intent lStart = new Intent("com.gaurav.wordbizz.PLAYERS");
			startActivity(lStart);
			finish();
		}
	}

	private class UpdateDB extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				initializeDB();
				SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
		        prefsEditor.putInt("VER", 1);
		        prefsEditor.commit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			Intent lStart = new Intent("com.gaurav.wordbizz.PLAYERS");
			startActivity(lStart);
			finish();
		}
		
	}
	
	/*private void updateDB() {
		Thread inputDB = new Thread() {
			public void run() {
				try {
					initializeDB();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		inputDB.start();
	}*/
	
	private void initializeDB() throws IOException {
		db.open();
		createDictionary();
		createCard();
		db.close();
	}
	public void createDictionary() throws IOException {
		is = getAssets().open("dictionary.csv");
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String line = "";
		while ((line = buffer.readLine()) != null) {
			String[] str = line.split("@");
			int i = Integer.parseInt(str[0]);
			db.createEntryDictionary(i, str[1], str[2]);
		}
		buffer.close();
	}
	
	public void createCard() throws IOException {
		is = getAssets().open("card.csv");
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String line = "";
		while ((line = buffer.readLine()) != null) {
			String[] str = line.split("@");
			int i = Integer.parseInt(str[0]);
			db.createEntryCard(i, str[1], str[2]);
		}
		buffer.close();
	}
}