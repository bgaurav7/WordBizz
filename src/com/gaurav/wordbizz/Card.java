package com.gaurav.wordbizz;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Card extends Activity implements View.OnClickListener {

	Button g, o, r, b, next,j,k;
	TextView g_def, o_def, r_def, b_def;
	TextView snd, eg;
	String sound = "kuk", example = "custard";
	String define[] = {"This is define 1", "This is define 2", "This is define 3", "This is define 4"},
		answer[] = {"A1", "A2", "A3", "A4"};
	Intent ansStart;
	int no_of_answers, score;
	int pscore = 1;
	String pname = "";
	SoundPool sp;
	int cardSound = 0;;
	
	@Override
	protected void onCreate(Bundle cardState) {
		super.onCreate(cardState);
		setContentView(R.layout.card);
		initializeVariables();
		initializeButton();
		initializeTextView();
		initializeLayout();
		
		if(cardSound != 0) 
			sp.play(cardSound, 1, 1, 0, 0, 1);
		no_of_answers = 0;
		score = 0;
		ansStart = new Intent("com.gaurav.wordbizz.ANSWER");
	}
	
	private void getAns(int i) {
		Bundle data = new Bundle();
		data.putString("sound", sound);
		data.putString("define", define[i]);
		data.putString("answer", answer[i]);
		data.putInt("level", i);
		ansStart.putExtras(data);
		startActivityForResult(ansStart, 0);
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent res) {
		super.onActivityResult(requestCode, resultCode, res);
		int l = res.getExtras().getInt("level");
		no_of_answers++;
		if (resultCode == 1) {
			updateScore(l);
		} else if (resultCode == 0) {
			if(l == 3)
				updateScore(-1);
		} else {
			switch (l) {
			case 0: g.setEnabled(true); break;
			case 1: o.setEnabled(true); break;
			case 2: r.setEnabled(true); break;
			case 3: b.setEnabled(true); break;
			}
			no_of_answers--;
		}
		if(no_of_answers == 4) {
			returnScore();
		}
	}

	private void returnScore() {
		setResult(score);
		finish();
	}

	private void updateScore(int level) {
		switch (level) {
		case -1: score -= 1; break;
		case 0: score += 1; break;
		case 1: score += 2; break;
		case 2: score += 3; break;
		case 3: score += 4; break;
		}
	}

	public void onClick(View v) {
		
		if(v.getId() == R.id.next) {
			returnScore();
		} else if(v.getId()== R.id.exit) {
			score = -7;
			returnScore();
		} else if(v.getId()== R.id.score) {
			Dialog sc = new Dialog(this);
			sc.setTitle("YOUR SCORE");
			TextView scr = new TextView(this);
			scr.setText(pname + " : " + pscore + "\n\n");
			scr.setGravity(Gravity.CENTER);
			sc.setContentView(scr);
			sc.show();
		} else {
			Button p = null;
			switch (v.getId()) {
			case R.id.green :
				getAns(0);
				p = g;
				break;
			case R.id.orange :
				getAns(1);
				p = o;
				break;
			case R.id.red :
				getAns(2);
				p = r;
				break;
			case R.id.black :
				getAns(3);
				p = b;
				break;
			}
			p.setEnabled(false);
		}
	}
	
	private void initializeVariables() {
		Bundle cardData = getIntent().getExtras();
		sound = cardData.getString("sound");
		example = cardData.getString("example");
		define[0] = cardData.getString("e_def");
		define[1] = cardData.getString("m_def");
		define[2] = cardData.getString("d_def");
		define[3] = cardData.getString("o_def");
		answer[0] = cardData.getString("e_word");
		answer[1] = cardData.getString("m_word");
		answer[2] = cardData.getString("d_word");
		answer[3] = cardData.getString("o_word");
		pname = cardData.getString("P_Name");
		setTitle(pname);
		pscore = cardData.getInt("pscore");
	}
	private void initializeLayout() {
		snd.setText(sound);
		eg.setText(example);
		g_def.setText(define[0]);
		o_def.setText(define[1]);
		r_def.setText(define[2]);
		b_def.setText(define[3]);
	}
	
	private void initializeTextView() {
		g_def = (TextView) findViewById(R.id.g_def);
		o_def = (TextView) findViewById(R.id.o_def);
		r_def = (TextView) findViewById(R.id.r_def);
		b_def = (TextView) findViewById(R.id.b_def);
		
		snd = (TextView) findViewById(R.id.sound);
		eg = (TextView) findViewById(R.id.example);
	}
	
	private void initializeButton() {
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		
		g = (Button) findViewById(R.id.green);
		g.setOnClickListener(this);
		
		o = (Button) findViewById(R.id.orange);
		o.setOnClickListener(this);
		
		r = (Button) findViewById(R.id.red);
		r.setOnClickListener(this);
		
		b = (Button) findViewById(R.id.black);
		b.setOnClickListener(this);
		
		j = (Button) findViewById(R.id.score);
		j.setOnClickListener(this);
		
		k = (Button) findViewById(R.id.exit);
		k.setOnClickListener(this);
		
		sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		//cardSound = sp.load(this, R.raw.cardchange, 1);
	}
}