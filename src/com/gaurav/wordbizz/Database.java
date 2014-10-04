package com.gaurav.wordbizz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

	//Column Variables
	public static final String KEY_ROWID1 = "_id";
	public static final String KEY_ROWID2 = "_id";
	public static final String KEY_ROWID3 = "_id";
	public static final String KEY_ROWID4 = "_id";
	public static final String KEY_ROWID5 = "_id";
	public static final String KEY_ROWID6 = "_id";
	public static final String KEY_ROWID7 = "_id";
	public static final String KEY_ID4 = "Difficult_id";
	public static final String KEY_ID5 = "Optional_id";
	public static final String KEY_ID3 = "Medium_id";
	public static final String KEY_ID2 = "Easy_id";
	public static final String CARD_ID = "_id";
	public static final String PLAYER_ID = "play_id";
	public static final String CARD_SCORE = "Score";
	public static final String PLAYER_NAME = "Name";
	
	public static final String DATABASE_NAME = "pro_1db";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE1 = "Dictionary";
	private static final String DATABASE_TABLE6 = "Card";
	
	public static final String DEF = "Definition";
	public static final String WORD = "Word";
	public static final String P_SOUND = "Sound";
	public static final String P_EXAMPLE = "Example";
	
	
	
	private DBHelper ourHelper;
	private Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private class DBHelper extends SQLiteOpenHelper {
		
		public DBHelper(Context context) {
			super(context, DATABASE_NAME , null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			//Tables
			db.execSQL("CREATE TABLE " + DATABASE_TABLE1 + "(" + KEY_ROWID1 + " INTEGER PRIMARY KEY," + DEF + " TEXT," + WORD + " TEXT );");
			db.execSQL("CREATE TABLE " + DATABASE_TABLE6 + "(" + KEY_ROWID6 + " INTEGER PRIMARY KEY," + P_SOUND + " TEXT," + P_EXAMPLE + " TEXT );");
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
		}
	}
		
	public Database(Context c) {
		ourContext = c;
	}
	
	public Database open() {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getReadableDatabase();
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}
	
	//Get Data From Database
	public String getSound(int CardID) {
		String[]columns = {KEY_ROWID6,P_SOUND,P_EXAMPLE};
		Cursor c = ourDatabase.query(DATABASE_TABLE6,columns, KEY_ROWID6 + "=" + CardID,null,null,null,null);
		if(c != null) {
			c.moveToFirst();
			String result = c.getString(1);
			return result;
		}
		return null;
	}
	public String getExample(int CardID) {
		String[]columns = {KEY_ROWID6,P_SOUND,P_EXAMPLE};
		Cursor c = ourDatabase.query(DATABASE_TABLE6,columns, KEY_ROWID6 + "=" + CardID,null,null,null,null);
		if(c != null) {
			c.moveToFirst();
			String result = c.getString(2);
			return result;
		}
		return null;
	}
	
	public String getWordDef(int WID) {
		String []columns = {KEY_ROWID1,DEF,WORD};
		Cursor c = ourDatabase.query(DATABASE_TABLE1,columns, KEY_ROWID1 + "=" + WID, null,null,null,null);
		if(c != null) {
			c.moveToFirst();
			String result = c.getString(1);
			return result;
		}
		return null;
	}
	public String getWordName(int WID) {
		String []columns = {KEY_ROWID1,DEF,WORD};
		Cursor c = ourDatabase.query(DATABASE_TABLE1,columns, KEY_ROWID1 + "=" + WID, null,null,null,null);
		if(c != null) {
			c.moveToFirst();
			String result = c.getString(2);
			return result;
		}
		return null;
	}
	
	//Insert into DB
	public void createEntryDictionary(int i,String def,String text) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_ROWID1,i);
		cv.put(DEF,def);
		cv.put(WORD,text);
		ourDatabase.insert(DATABASE_TABLE1,null,cv);
	}
	
	public void createEntryCard(int i,String sound,String example) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_ROWID6,i);
		cv.put(P_SOUND,sound);
		cv.put(P_EXAMPLE,example);
		ourDatabase.insert(DATABASE_TABLE6,null,cv);
	}
}
