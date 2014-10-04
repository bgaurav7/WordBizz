package com.gaurav.wordbizz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Players extends Activity {

	Button play;
	Spinner spinner;
	ImageView v;
	int players;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.players);
		
		initialize();
		
		play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String p = String.valueOf(spinner.getSelectedItem());
				players = Integer.parseInt(p.substring(0, 1));
				Toast.makeText(Players.this, "No of Players : " + players , Toast.LENGTH_SHORT).show();
				Intent nStart = new Intent("com.gaurav.wordbizz.NAME");
				Bundle play = new Bundle();
				play.putInt("no_of_players", players);
				nStart.putExtras(play);
				startActivity(nStart);
			}
		});

	}

	private void initialize() {
		v = (ImageView) findViewById(R.id.logo);
		play = (Button) findViewById(R.id.play);
		spinner = (Spinner) findViewById(R.id.no_of_players);
	}
}
