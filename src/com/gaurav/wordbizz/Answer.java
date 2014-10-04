package com.gaurav.wordbizz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Answer extends Activity implements View.OnClickListener {

	Button wrong, correct, ans;
	LinearLayout lay, lay_ans;
	TextView sound, answer, define, label;
	Bundle data;
	
	@Override
	protected void onCreate(Bundle ansState) {
		super.onCreate(ansState);
		setContentView(R.layout.answer);

		initialize();
		data = getIntent().getExtras();
		initializeLayout();
		setLayout();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ans_press:
			ans.setEnabled(false);
			lay.setVisibility(LinearLayout.VISIBLE);
			break;
		case R.id.correct:
			correct.setBackgroundResource(R.drawable.check_button_down);
			result(1);
			break;
		case R.id.wrong:
			wrong.setBackgroundResource(R.drawable.x_button_down);
			result(0);
			break;
		}
	}
	
	private void result(int i) {
		Intent result = new Intent();
		Bundle r = new Bundle();
		r.putInt("level", data.getInt("level"));
		result.putExtras(r);
		setResult(i, result);
		finish();
	}

	@Override
	public void onBackPressed() {
		if(ans.isEnabled()) {
			result(-1);
		} else {
			result(0);
		}
		super.onBackPressed();
	}
	
	private void setLayout() {	
		switch(data.getInt("level")) {
			case 0:
				setTitle("Category : GREEN (+1)");
				answer.setBackgroundResource(R.color.b_green);
				break;
			case 1:
				setTitle("Category : ORANGE (+2)");
				answer.setBackgroundResource(R.color.b_orange);
				break;
			case 2:
				setTitle("Category : RED (+3)");
				answer.setBackgroundResource(R.color.b_red);
				break;
			case 3:
				setTitle("Category : BLACK (+4 / -1)");
				label.setText("ENDS with");
				answer.setBackgroundResource(R.color.b_black);
				break;
		}
	}

	private void initializeLayout() {
		lay.setVisibility(LinearLayout.INVISIBLE);
		
		sound.setText(data.getString("sound"));
		define.setText(data.getString("define"));
		answer.setText(data.getString("answer"));
	}
	
	private void initialize() {
		lay = (LinearLayout) findViewById(R.id.layout);
		lay.setVisibility(LinearLayout.INVISIBLE);
		
		ans = (Button) findViewById(R.id.ans_press);
		ans.setOnClickListener(this);
		
		correct = (Button) findViewById(R.id.correct);
		correct.setOnClickListener(this);
		wrong = (Button) findViewById(R.id.wrong);
		wrong.setOnClickListener(this);
		
		sound = (TextView) findViewById(R.id.sound_ans);
		define = (TextView) findViewById(R.id.def_ans);
		answer = (TextView) findViewById(R.id.ans);
		label = (TextView) findViewById(R.id.label);
	}
}
