package com.adrielcafe.duelodematematica;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.congrace.exp4j.ExpressionBuilder;

public class MainActivity extends Activity {
	private static final int ACTION_CHALLENGE = 1;
	private static final int ACTION_ANSWER = 2;	
	private static final int PLAYER_1 = 1;
	private static final int PLAYER_2 = 2;	
	
	private LinearLayout container;
	private CalculatorFragment fragCalculatorP1;
	private CalculatorFragment fragCalculatorP2;
	private ChallengeFragment fragChallengeP1;
	private ChallengeFragment fragChallengeP2;
	private TextView scoreP1View;
	private TextView scoreP2View;

	private int action = 0;
	private int scoreP1 = 0;
	private int scoreP2 = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_main);

		scoreP1View = (TextView) findViewById(R.id.scoreP1);
		scoreP2View = (TextView) findViewById(R.id.scoreP2);
		container = (LinearLayout) findViewById(R.id.container);
		fragCalculatorP1 = (CalculatorFragment) getFragmentManager().findFragmentById(R.id.fragCalculatorP1);
		fragCalculatorP2 = (CalculatorFragment) getFragmentManager().findFragmentById(R.id.fragCalculatorP2);
		fragChallengeP1 = (ChallengeFragment) getFragmentManager().findFragmentById(R.id.fragChallengeP1);
		fragChallengeP2 = (ChallengeFragment) getFragmentManager().findFragmentById(R.id.fragChallengeP2);

		fragCalculatorP1.getView().setVisibility(View.GONE);
		fragChallengeP1.getView().setRotation(180);
		fragChallengeP2.getView().setRotation(180);
		
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
	    BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bmp);
	    bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
	    container.setBackgroundDrawable(bitmapDrawable);
	}
	
	public void reset(View v){
		action = 0;
		scoreP1 = 0;
		scoreP2 = 0;
		fragCalculatorP1.getView().setVisibility(View.GONE);
		fragCalculatorP2.getView().setVisibility(View.VISIBLE);
		fragCalculatorP1.clearFormula();
		fragCalculatorP2.clearFormula();
		fragCalculatorP1.setChallengeMode(); 
		fragCalculatorP2.setChallengeMode(); 
		fragChallengeP1.reset();
		fragChallengeP2.reset();
		updateScore();
	}
	
	public void about(View v){
		String message = "<b>Duelo de Matemática</b> é um software livre, seu código fonte está disponível no <a href='https://github.com/adrielcafe/DueloDeMatematica'>GitHub</a>.<br><br>Desenvolvido por:<br>Adriel Café (ac@adrielcafe.com)";
		LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		TextView tv = new TextView(this);
		tv.setText(Html.fromHtml(message));
		tv.setPadding(15, 15, 15, 15);
		tv.setLayoutParams(layout);
		tv.setBackgroundColor(getResources().getColor(android.R.color.white));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setTextAppearance(this, android.R.style.TextAppearance_Medium);
		new AlertDialog.Builder(this)
			.setTitle("Sobre")
			.setView(tv)
			.show();
	}

	public void addToFormula(View v){
		int id = ((ViewGroup) ((ViewGroup) v.getParent()).getParent()).getId();
		
		if(R.id.fragCalculatorP1 == id)
			fragCalculatorP1.addToFormula(v.getTag().toString());
		else if(R.id.fragCalculatorP2 == id)
			fragCalculatorP2.addToFormula(v.getTag().toString());
	}
	
	public void backspace(View v){
		int id = ((ViewGroup) ((ViewGroup) v.getParent()).getParent()).getId();
		
		if(R.id.fragCalculatorP1 == id)
			fragCalculatorP1.backspace();
		else if(R.id.fragCalculatorP2 == id)
			fragCalculatorP2.backspace();
	}
	
	public void challengeAnswer(View v){
		int id = ((ViewGroup) ((ViewGroup) v.getParent()).getParent()).getId();
		
		if(action == 0){
			fragChallengeP1.setChallengeMode(); 
			fragChallengeP2.setChallengeMode();
			fragCalculatorP1.setAnswerMode();
			fragCalculatorP2.setAnswerMode();
		}
		
		switch (action) {
			case ACTION_ANSWER:
				action = ACTION_CHALLENGE;
				if(R.id.fragCalculatorP1 == id){
					fragCalculatorP1.setChallengeMode();
					showAnswer(PLAYER_1, fragChallengeP1.getFormula(), fragCalculatorP1.getFormula());
				} else if(R.id.fragCalculatorP2 == id){
					fragCalculatorP2.setChallengeMode();
					showAnswer(PLAYER_2, fragChallengeP2.getFormula(), fragCalculatorP2.getFormula());
				}
				break;
			case ACTION_CHALLENGE:
			default:
				action = ACTION_ANSWER;
				if(R.id.fragCalculatorP1 == id){
					fragChallengeP1.setChallengeMode(); 
					fragCalculatorP1.setAnswerMode();
					showChallenge(PLAYER_1, fragCalculatorP1.getFormula());
				} else if(R.id.fragCalculatorP2 == id) {
					fragChallengeP2.setChallengeMode();
					fragCalculatorP2.setAnswerMode();
					showChallenge(PLAYER_2, fragCalculatorP2.getFormula());
				}
				break;
		}
		
		fragCalculatorP1.clearFormula();
		fragCalculatorP2.clearFormula();
	}
	
	private void showChallenge(int player, String formula){
		switch (player) {
			case PLAYER_1:
				fragCalculatorP1.getView().setVisibility(View.GONE);
				fragCalculatorP2.getView().setVisibility(View.VISIBLE);
				fragChallengeP2.setFormula(formula);
				break;
			case PLAYER_2:
				fragCalculatorP2.getView().setVisibility(View.GONE);
				fragCalculatorP1.getView().setVisibility(View.VISIBLE);
				fragChallengeP1.setFormula(formula);
				break;
		}
	}
	
	private void showAnswer(int player, String formula, String answer) {
		Double result;
		try {
			result = new ExpressionBuilder(formula.replaceAll("×", "*").replaceAll("÷", "/")).build().calculate();
		} catch(Exception e) { 
			result = -99999999.0;
		}
		
		boolean correct = result.intValue() == Integer.parseInt(answer);
		switch (player) {
			case PLAYER_1:
				fragChallengeP1.setAnswerMode(correct, result.intValue());
				if(correct)
					scoreP1++;
				break;
			case PLAYER_2:
				fragChallengeP2.setAnswerMode(correct, result.intValue());
				if(correct)
					scoreP2++;
				break;
		}
		updateScore();
	}
	
	private void updateScore(){
		scoreP1View.setText(scoreP1+"");
		scoreP2View.setText(scoreP2+"");
	}
}