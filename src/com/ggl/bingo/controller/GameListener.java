package com.ggl.bingo.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.ggl.bingo.model.BingoModel;
import com.ggl.bingo.view.BingoFrame;

public class GameListener implements ActionListener {
	
	private final BingoFrame frame;
	
	private final BingoModel model;
	
	private Timer timer;

	public GameListener(BingoFrame frame, BingoModel model) {
		this.frame = frame;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		BallListener listener = new BallListener(frame, model);
		timer = new Timer(4000, listener);
		timer.setInitialDelay(0);
		listener.setTimer(timer);
		timer.start();
		
		frame.simulationButtonEnabled(false);
	}
	
}
