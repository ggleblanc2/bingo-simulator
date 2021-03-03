package com.ggl.bingo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BingoModel {

	private int[] balls;
	
	private List<Card> cards;
	
	private List<Integer> calledBalls;
	
	private Random random;
	
	public BingoModel() {
		this.random = new Random();
		
		this.balls = createBalls();
		this.balls = shuffleBalls();
		
		this.cards = new ArrayList<>();
		this.calledBalls = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			cards.add(createCard());
		}
	}
	
	private int[] createBalls() {
		int[] balls = new int[75];
		for (int i = 0; i < balls.length; i++) {
			balls[i] = i + 1;
		}
		
		return balls;
	}
	
	private Card createCard() {
		int[][] cardValues = new int[5][];
		cardValues[0] = createCardColumn(1, 15);
		cardValues[1] = createCardColumn(16, 30);
		cardValues[2] = createCardColumn(31, 45);
		cardValues[3] = createCardColumn(46, 60);
		cardValues[4] = createCardColumn(61, 75);
		
		Card card = new Card();
		card.setCardValues(cardValues);
		return card;
	}
	
	private int[] createCardColumn(int minimum, int maximum) {
		int[] column = new int[5];
		int count = 0;
		while (count < 5) {
			boolean validValue = true;
			int value = random.nextInt(maximum - minimum + 1) + minimum;
			for (int i = 0; i < count; i++) {
				if (column[i] == value) {
					validValue = false;
					break;
				}
			}
			if (validValue) {
				column[count++] = value;
			}
		}
		
		return column;
	}
	
	public void resetGame() {
		calledBalls.clear();
		balls = shuffleBalls();
	}
	
	public int callNextBall() {
		int value = balls[calledBalls.size()];
		calledBalls.add(value);
		 
		for (Card card : cards) {
			card.setCalledValue(value);
		}
		
		return value;
	}
	
	private int[] shuffleBalls() {
		int limit = balls.length * 3;
		for (int i = 0; i < limit; i++) {
			int x = random.nextInt(balls.length);
			int y = random.nextInt(balls.length);
			
			int temp = balls[x];
			balls[x] = balls[y];
			balls[y] = temp;
		}
		
		return balls;
	}

	public List<Card> getCards() {
		return cards;
	}
	
	public int getCalledBallsSize() {
		return calledBalls.size();
	}
	
}
