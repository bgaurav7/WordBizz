package com.gaurav.wordbizz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Name extends Activity {

	int p = 1;
	EditText pName1, pName2, pName3, pName4, pName5, pName6;
	LinearLayout pL1, pL2, pL3, pL4, pL5, pL6;
	Button submit;
	String [] name = {"", "", "", "", "", "", ""};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.name);
		setTitle("Enter Names");
		
		initialize();
		
		Bundle play = new Bundle();
		play = getIntent().getExtras();
		p = play.getInt("no_of_players");
		
		initializeLayout();
		
		
	}

	private void initialize() {
		pName1 = (EditText) findViewById(R.id.pName1);
		pName2 = (EditText) findViewById(R.id.pName2);
		pName3 = (EditText) findViewById(R.id.pName3);
		pName4 = (EditText) findViewById(R.id.pName4);
		pName5 = (EditText) findViewById(R.id.pName5);
		pName6 = (EditText) findViewById(R.id.pName6);
		
		pL1 = (LinearLayout) findViewById(R.id.pNameL1);
		pL2 = (LinearLayout) findViewById(R.id.pNameL2);
		pL3 = (LinearLayout) findViewById(R.id.pNameL3);
		pL4 = (LinearLayout) findViewById(R.id.pNameL4);
		pL5 = (LinearLayout) findViewById(R.id.pNameL5);
		pL6 = (LinearLayout) findViewById(R.id.pNameL6);
		
		submit = (Button) findViewById(R.id.submt_name);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				insertPlayerName();
				Thread game = new Thread() {
					public void run() {
						Intent gStart = new Intent("com.gaurav.wordbizz.GAME");
						Bundle play = new Bundle();
						play.putInt("no_of_players", p);
						play.putStringArray("name", name);
						gStart.putExtras(play);
						startActivity(gStart);
					}
				};
				game.start();
				finish();
			}

			private void insertPlayerName() {
				
				switch(p) {
				case 6: name[5] = pName6.getText().toString();
				case 5: name[4] = pName5.getText().toString();
				case 4: name[3] = pName4.getText().toString();
				case 3: name[2] = pName3.getText().toString();
				case 2: name[1] = pName2.getText().toString();
				case 1: name[0] = pName1.getText().toString();
				}
			}
		});
	}

	private void initializeLayout() {
		switch(p) {
		case 1: pL2.setVisibility(LinearLayout.INVISIBLE);
		case 2: pL3.setVisibility(LinearLayout.INVISIBLE);
		case 3: pL4.setVisibility(LinearLayout.INVISIBLE);
		case 4: pL5.setVisibility(LinearLayout.INVISIBLE);
		case 5: pL6.setVisibility(LinearLayout.INVISIBLE);
		}
	}

}
