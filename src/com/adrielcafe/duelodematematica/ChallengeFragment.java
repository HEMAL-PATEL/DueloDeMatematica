package com.adrielcafe.duelodematematica;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ChallengeFragment extends Fragment  {
	private Spanned correctText;
	private Spanned wrogText;

	private ImageView imageView;
	private TextView titleView;
	private TextView formulaView;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		correctText = Html.fromHtml("<font color='#26A65B'>" + getString(R.string.correct) + "</font>");
		wrogText = Html.fromHtml("<font color='#D91E18'>" + getString(R.string.wrong) + "</font>");
		
		View view = inflater.inflate(R.layout.fragment_challenge, container, false);
		imageView = (ImageView) view.findViewById(R.id.image);
		titleView = (TextView) view.findViewById(R.id.title);
		formulaView = (TextView) view.findViewById(R.id.formula);
		return view;
	}
	
	public void reset(){
		imageView.setImageResource(R.drawable.logo);
		titleView.setText(R.string.welcome_title);
		formulaView.setText(R.string.welcome_formula);
	}

	public void setChallengeMode(){
		imageView.setImageResource(R.drawable.challenge);
	}
	
	public void setAnswerMode(boolean correct, int result){
		imageView.setImageResource(correct ? R.drawable.won : R.drawable.lost);
		titleView.setText(correct ? correctText : wrogText);
		formulaView.setText(correct ? getString(R.string.you_turn) : getString(R.string.correct_answer)+ ": " + result + "\n" + getString(R.string.you_turn));
	}

	public String getFormula(){
		return formulaView.getText().toString();
	}

	public void setFormula(String formula){
		titleView.setText(R.string.what_is_the_result);
		formulaView.setText(formula);
	}
}