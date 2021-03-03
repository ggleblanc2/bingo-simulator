package com.ggl.bingo.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.ggl.bingo.model.BingoModel;
import com.ggl.bingo.model.Card;
import com.ggl.bingo.view.BingoFrame;

public class ResetListener implements ActionListener {
	
	private final BingoFrame frame;
	
	private final BingoModel model;

	public ResetListener(BingoFrame frame, BingoModel model) {
		this.frame = frame;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		model.resetGame();
		List<Card> cards = model.getCards();
		for (Card card : cards) {
			card.resetCalledValues();
		}
		
		for (int i = 1; i <= 75; i++) {
			frame.resetCallNumber(i);
		}
		frame.getTextArea().setText("");
		frame.resetCards();
		frame.markCards();
		frame.resetButtonEnabled(false);
		frame.simulationButtonEnabled(true);
	}

}
