package com.ggl.bingo.model;

public class Card {
	
	private boolean[][] calledValues;
	
	private int[][] cardValues;
	
	public Card() {
		this.cardValues = new int[5][5];
		this.calledValues = new boolean[5][5];
	}

	public void setCardValues(int[][] cardValues) {
		this.cardValues = cardValues;
		setCalledValue(2, 2, true);
	}

	public int[][] getCardValues() {
		return cardValues;
	}
	
	public boolean[][] getCalledValues() {
		return calledValues;
	}

	public void resetCalledValues() {
		for (int i = 0; i < cardValues.length; i++) {
			for (int j = 0; j < cardValues[i].length; j++) {
				calledValues[i][j] = false;
			}
		}
		setCalledValue(2, 2, true);
	}
	
	public void setCalledValue(int value) {
		for (int i = 0; i < cardValues.length; i++) {
			for (int j = 0; j < cardValues[i].length; j++) {
				if (value == cardValues[i][j]) {
					calledValues[i][j] = true;
				}
			}
		}
	}
	
	public void setCalledValue(int i, int j, boolean b) {
		this.calledValues[i][j] = b;
	}
	
	public boolean isCalledValue(int i, int j) {
		return calledValues[i][j];
	}
	
	public int[] isFiveInARow() {
		int[][] indexes = { { 0, 1, 2, 3, 4 }, { 5, 6, 7, 8, 9 }, 
				{ 10, 11, 12, 13, 14 }, { 15, 16, 17, 18, 19 },
				{ 20, 21, 22, 23, 24 }, { 0, 5, 10, 15, 20 }, 
				{ 1, 6, 11, 16, 21 }, { 2, 7, 12, 17, 22 },
				{ 3, 8, 13, 18, 23 }, { 4, 9, 14, 19, 24 }, 
				{ 0, 6, 12, 18, 24 }, { 4, 8, 12, 16, 20 },
				{ 0, 4, 12, 20, 24 } };
		
		for (int i = 0; i < indexes.length; i++) {
			boolean inARow = true;
			for (int j = 0; j < indexes[i].length; j++) {
				int x = indexes[i][j] / 5;
				int y = indexes[i][j] - x * 5;
				if (!calledValues[x][y]) {
					inARow = false;
					break;
				}
			}
			if (inARow) {
				return indexes[i];
			}
		}
		
		return new int[0];
	}

}
