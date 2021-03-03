package com.ggl.bingo.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import com.ggl.bingo.model.BingoModel;
import com.ggl.bingo.model.Card;
import com.ggl.bingo.view.BingoFrame;

public class BallListener implements ActionListener {
	
	private final BingoFrame frame;
	
	private final BingoModel model;
	
	private Timer timer;

	public BallListener(BingoFrame frame, BingoModel model) {
		this.frame = frame;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		int value = model.callNextBall();
		int size = model.getCalledBallsSize();
		String count = String.format("%2d", size);
		String line = count + ". ";
		line += getLetter(value) + " " + value;
		line += System.lineSeparator();
		frame.getTextArea().append(line);
		
		boolean completeCard = false;
		List<Card> cards = model.getCards();
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			int[] row = card.isFiveInARow();
			if (row.length > 0) {
				frame.setFiveInARow(i, row);
				completeCard = true;
			}
		}
		
		frame.setCallNumber(value);
		frame.markCards();
		
		if (completeCard) {
			timer.stop();
			frame.resetButtonEnabled(true);
		}
	}
	
	private String getLetter(int value) {
		String letter = (value >= 1 && value <= 15) ? "B" : "";
		letter += (value >= 16 && value <= 30) ? "I" : "";
		letter += (value >= 31 && value <= 45) ? "N" : "";
		letter += (value >= 46 && value <= 60) ? "G" : "";
		letter += (value >= 61 && value <= 75) ? "O" : "";
		return letter;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
}
