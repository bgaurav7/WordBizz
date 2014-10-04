package com.gaurav.wordbizz;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity {
	
	Intent cStart;
	int no_of_players = 1;
	int currentPlayer;
	int cardSet;
	Database db;
	String[] name;
	int [] pscore = {0, 0, 0, 0, 0, 0};
	
	@Override
	protected void onCreate(Bundle gameState) {
		super.onCreate(gameState);
		setContentView(R.layout.game);
		
		Bundle play = new Bundle();
		play = getIntent().getExtras();
		no_of_players = play.getInt("no_of_players");
		name = play.getStringArray("name");
		currentPlayer = 1;
		cardSet = 5;
		db = new Database(Game.this);
		db.open();
		
		cStart = new Intent("com.gaurav.wordbizz.CARD");
		playCard();
	}

	private void playCard() {
		if(cardSet > 0) {
			int c = (int) (Math.random() * 499);
			Toast.makeText(Game.this, "TURN : " + name[currentPlayer - 1] + " ", Toast.LENGTH_SHORT).show();
			Bundle cardData = new Bundle();
			cardData.putString("P_Name", name[currentPlayer - 1]);
			cardData.putInt("pscore", pscore[currentPlayer - 1]);
			cardData.putString("sound", db.getSound(c));
			cardData.putString("example", db.getExample(c));
			cardData.putString("e_def", db.getWordDef(c));
			cardData.putString("e_word", db.getWordName(c));
			c = c + 499;
			cardData.putString("m_def", db.getWordDef(c));
			cardData.putString("m_word", db.getWordName(c));
			c = c + 499;
			cardData.putString("d_def", db.getWordDef(c));
			cardData.putString("d_word", db.getWordName(c));
			c = c + 499;
			cardData.putString("o_def", db.getWordDef(c));
			cardData.putString("o_word", db.getWordName(c));
			cStart.putExtras(cardData);
			startActivityForResult(cStart, 0);
		} else {
			endGame();
		}
		
	}

	 private void endGame() {
		 	db.close();
			Dialog sc = new Dialog(this);
			sc.setTitle("FINAL SCORE");
			sort();
			TextView r = new TextView(this);
			String result = "";
			for(int i = 0; i < no_of_players ; i++) {
				result += "Rank : " + (int)(i  + 1) + "    Name : " + name[i] + "    Score  : " + pscore[i] + "\n\n"; 
			}
			r.setText(result);
			r.setGravity(Gravity.CENTER);
			sc.setContentView(r);
			sc.show();
			sc.setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface arg0) {
					finish();
				}
			});	
	}

	private void sort() {
		 for(int i = 0; i < no_of_players-1 ; i++) {
			 int tmp1;
	         String tmp2;
	         for(int j = i + 1; j < no_of_players; j++) {
	                if(pscore[i]<pscore[j]) {
	                    tmp1=pscore[i];
	                    pscore[i]=pscore[j];
	                    pscore[j]=tmp1;
	                    tmp2=name[i];
	                    name[i]=name[j];
	                    name[j]=tmp2;
	                
	                }
	            }
	         }
		 }

	@Override
	protected void onActivityResult(int requestCode, int score, Intent data) {
		super.onActivityResult(requestCode, score, data);
		//PlayerScore Update Here
		if(score == -7) {
			endGame();
		} else {
			pscore[currentPlayer - 1] += score;
			currentPlayer++;
			if(currentPlayer == no_of_players + 1) {
				currentPlayer = 1;
				cardSet--;
			}
			playCard();
		}
	}
}
