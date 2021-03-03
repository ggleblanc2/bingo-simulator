package com.ggl.bingo;

import javax.swing.SwingUtilities;

import com.ggl.bingo.model.BingoModel;
import com.ggl.bingo.view.BingoFrame;

public class Bingo implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Bingo());
	}

	@Override
	public void run() {
		new BingoFrame(new BingoModel());
	}

}
