package com.ggl.bingo.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.ggl.bingo.model.Card;

public class BingoCard extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int cellWidth;
	private int margin;
	
	private int[] index;
	
	private Card card;
	
	public BingoCard(Card card) {
		this.card = card;
		this.cellWidth = 30;
		this.margin = 5;
		this.index = new int[0];
		
		int width = cellWidth * 5 + margin * 2;
		int height = cellWidth * 6 + margin;
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE, margin));
	}
	
	public void setIndex(int[] index) {
		this.index = index;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		int textHeight = paintTitle(g2d);
		paintGrid(g2d, textHeight, card);
	}

	private int paintTitle(Graphics2D g2d) {
		Font font = getFont().deriveFont(Font.BOLD, 20f);
		String[] letters = { "B", "I", "N", "G", "O" };
		int x = 5;
		int y = 5;
		for (int i = 0; i < letters.length; i++) {
			fillCell(g2d, letters[i], font, Color.BLUE, Color.WHITE, 
					x - 1, y - 1, cellWidth + 2);
			x += cellWidth;
		}
        
        return cellWidth;
	}
	
	private void paintGrid(Graphics2D g2d, int initialY, Card card) {
		int[][] grid = card.getCardValues();
		
		int x1 = 5;
		int x2 = x1 + cellWidth * grid.length;
		int y1 = initialY;
		int y2 = y1 + cellWidth * grid[0].length;

		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(3f));
		paintHorizontalLines(g2d, grid, x1, x2, y1, cellWidth);
		paintVerticalLines(g2d, grid, x1, y1, y2, cellWidth);
		paintGridValues(g2d, card, x1, y1);
	}

	private void paintVerticalLines(Graphics2D g2d, int[][] grid, 
			int x1, int y1, int y2, int cellWidth) {
		int x = x1;
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				g2d.drawLine(x, y1, x, y2);
				x += cellWidth;
			}
			g2d.drawLine(x, y1, x, y2);
			x = x1;
		}
	}

	private void paintHorizontalLines(Graphics2D g2d, int[][] grid, 
			int x1, int x2, int y1, int cellWidth) {
		int y = y1;
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				g2d.drawLine(x1, y, x2, y);
				y += cellWidth;
			}
			g2d.drawLine(x1, y, x2, y);
			y = y1;
		}
	}
	
	private void paintGridValues(Graphics2D g2d, Card card, int x1, 
			int y1) {
		int[][] grid = card.getCardValues();
		boolean[][] called = card.getCalledValues();
		Font font = getFont().deriveFont(Font.BOLD, 16f);
		
		int x = x1;
		int y = y1;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				String value = Integer.toString(grid[i][j]);
				if ((i == 2) && (j == 2)) {
					value = "F";
				} 
				if (called[i][j]) {
					fillCell(g2d, value, font, Color.BLACK, Color.WHITE, 
							x, y, cellWidth);
				} else {
					fillCell(g2d, value, font, Color.WHITE, Color.BLACK, 
							x, y, cellWidth);
				}
				
				y += cellWidth;
			}
			y = y1;
			x += cellWidth;
		}
		
		for (int i = 0; i < index.length; i++) {
			int a = index[i] / 5;
			int b = index[i] % 5;
			x = x1 + a * cellWidth;
			y = y1 + b * cellWidth;
			String value = Integer.toString(grid[a][b]);
			if ((a == 2) && (b == 2)) {
				value = "F";
			}
			fillCell(g2d, value, font, Color.GREEN, Color.BLACK, 
					x, y, cellWidth);
		}
	}
	
	private void fillCell(Graphics2D g2d, String value, Font font, 
			Color backgroundColor, Color foregroundColor, 
			int x, int y, int cellWidth) {
		FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(value, frc);
        int textWidth = (int) Math.round(r2D.getWidth());
        int textHeight = (int) Math.round(r2D.getHeight());

        int a = (cellWidth - textWidth) / 2 + x;
        int b = y + cellWidth - textHeight / 2;
        
        g2d.setColor(backgroundColor);
        g2d.fillRect(x + 1, y + 1, cellWidth - 2, cellWidth - 2);
        g2d.setFont(font);
        g2d.setColor(foregroundColor);
        g2d.drawString(value, a, b);
	}
	
}
