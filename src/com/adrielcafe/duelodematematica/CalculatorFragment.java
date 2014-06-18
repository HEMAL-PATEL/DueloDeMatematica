package com.adrielcafe.duelodematematica;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorFragment extends Fragment  {
	private static final String OP_SUM = "+";
	private static final String OP_SUBTRACT = "-";
	private static final String OP_MULTIPLY = "x";
	private static final String OP_DIVIDE = "÷";

	private TextView formulaView;
	private Button sumView;
	private Button multiplyView;
	private Button divideView;
	private Button challengeAnswerView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_calculator, container, false);
		formulaView = (TextView) view.findViewById(R.id.formula);
		sumView = (Button) view.findViewById(R.id.sum);
		multiplyView = (Button) view.findViewById(R.id.multiply);
		divideView = (Button) view.findViewById(R.id.divide);
		challengeAnswerView = (Button) view.findViewById(R.id.challengeAnswer);
		return view;
	}

	public void setChallengeMode(){
		sumView.setEnabled(true);
		multiplyView.setEnabled(true);
		divideView.setEnabled(true);
		challengeAnswerView.setText(R.string.challenge);
	}

	public void setAnswerMode(){
		sumView.setEnabled(false);
		multiplyView.setEnabled(false);
		divideView.setEnabled(false);
		challengeAnswerView.setText(R.string.answer);
	}
	
	public String getFormula(){
		return formulaView.getText().toString();
	}

	public void setFormula(String formula){
		formulaView.setText(formula);
	}
	
	public void addToFormula(String formulaItem){
		String formula = getFormula();
		if(formula.length() > 0 && isOperator(formulaItem)){
			char lastItem = formula.charAt(formula.length() - 1);
			if(isOperator(lastItem+""))
				return;
		}
		formulaView.setText(formula + formulaItem);
	}

	public void clearFormula(){
		formulaView.setText("");
	}

	public void backspace(){
		String formula = getFormula();
		if(formula.length() > 0)
			setFormula(formula.substring(0, formula.length() - 1));
	}
	
	private boolean isOperator(String formulaItem){
		if(formulaItem.equals(OP_SUM) || formulaItem.equals(OP_SUBTRACT) || formulaItem.equals(OP_MULTIPLY) || formulaItem.equals(OP_DIVIDE))
			return true;
		else 
			return false;
	}
}