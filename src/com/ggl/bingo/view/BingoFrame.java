package com.ggl.bingo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ggl.bingo.controller.GameListener;
import com.ggl.bingo.controller.ResetListener;
import com.ggl.bingo.model.BingoModel;
import com.ggl.bingo.model.Card;

public class BingoFrame {
	
	private BingoCard[] bingoCards;
	
	private BingoModel model;
	
	private JButton resetButton;
	private JButton simulationButton;
	
	private JLabel[] callLabels;
	
	private JTextArea textArea;

	public BingoFrame(BingoModel model) {
		this.model = model;
		createAndShowGUI();
	}
	
	private void createAndShowGUI() {
		JFrame frame = new JFrame("Bingo Simulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(createCalledBoardPanel(), BorderLayout.BEFORE_FIRST_LINE);
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		
		System.out.println(frame.getSize());
	}

	private JPanel createCalledBoardPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel titlePanel = new JPanel(new FlowLayout());
		titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font font = titlePanel.getFont().deriveFont(Font.BOLD, 24f);
		
		JLabel label = new JLabel("Called Board");
		label.setFont(font);
		titlePanel.add(label);
		
		panel.add(titlePanel, BorderLayout.BEFORE_FIRST_LINE);
		panel.add(createCalledPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createCalledPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 16, 8, 8));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		Font font = panel.getFont().deriveFont(Font.PLAIN, 16f);
		Font boldFont = panel.getFont().deriveFont(Font.BOLD, 16f);
		
		callLabels = new JLabel[80];
		int labelCount = 0;
		String[] letters = { "B", "I", "N", "G", "O" };
		int count = 0;
		for (int i = 0; i < 75; i++) {
			if (i % 15 == 0) {
				JPanel cellPanel = new JPanel();
				cellPanel.setPreferredSize(new Dimension(35, 35));
				
				callLabels[labelCount] = new JLabel(letters[count++]);
				callLabels[labelCount].setForeground(Color.RED);
				callLabels[labelCount].setFont(boldFont);
				cellPanel.add(callLabels[labelCount++]);
				
				panel.add(cellPanel);
			}
			JPanel cellPanel = new JPanel();
			cellPanel.setPreferredSize(new Dimension(35, 35));
			
			callLabels[labelCount] = new JLabel(String.format("%2d", i + 1));
			callLabels[labelCount].setFont(font);
			cellPanel.add(callLabels[labelCount++]);
			
			panel.add(cellPanel);
		}
		
		return panel;
	}
	
	private JPanel createCenterPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createCardBoardPanel(), BorderLayout.BEFORE_LINE_BEGINS);
		
		JPanel innerPanel = new JPanel(new FlowLayout());
		innerPanel.add(createCallingBoardPanel());
		panel.add(innerPanel, BorderLayout.AFTER_LINE_ENDS);
		
		return panel;
	}
	
	private JPanel createCardBoardPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel titlePanel = new JPanel(new FlowLayout());
		titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font font = titlePanel.getFont().deriveFont(Font.BOLD, 24f);
		
		JLabel label = new JLabel("Cards Board");
		label.setFont(font);
		titlePanel.add(label);
		
		panel.add(titlePanel, BorderLayout.BEFORE_FIRST_LINE);
		panel.add(createCardPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createCardPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 3, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		List<Card> cards = model.getCards();
		bingoCards = new BingoCard[cards.size()];
		for (int i = 0; i <cards.size(); i++) {
			bingoCards[i] = new BingoCard(cards.get(i));
			panel.add(bingoCards[i]);
		}
		
		return panel;
	}
	
	private JPanel createCallingBoardPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JPanel titlePanel = new JPanel(new FlowLayout());
		titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font font = titlePanel.getFont().deriveFont(Font.BOLD, 24f);
		
		JLabel label = new JLabel("Calling Board");
		label.setFont(font);
		titlePanel.add(label);
		
		panel.add(titlePanel, gbc);
		
		gbc.gridy++;
		panel.add(createCallingPanel(), gbc);
		
		gbc.gridy++;
		simulationButton = new JButton("Start Round");
		simulationButton.addActionListener(new GameListener(this, model));
		panel.add(simulationButton, gbc);
		
		gbc.gridy++;
		resetButton = new JButton("Reset Game");
		resetButton.addActionListener(new ResetListener(this, model));
		resetButton.setEnabled(false);
		panel.add(resetButton, gbc);
		
		return panel;
	}
	
	private JPanel createCallingPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		textArea = new JTextArea(11, 10);
		textArea.setEditable(false);
		textArea.setFont(new Font("Courier New", Font.BOLD, 16));
		textArea.setMargin(new Insets(5, 5, 5, 5));
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		Dimension d = textArea.getPreferredSize();
		panel.setPreferredSize(new Dimension(d.width + 50, d.height));
		
		return panel;
	}

	public JTextArea getTextArea() {
		return textArea;
	}
	
	public void simulationButtonEnabled(boolean b) {
		simulationButton.setEnabled(b);
	}
	
	public void resetButtonEnabled(boolean b) {
		resetButton.setEnabled(b);
	}
	
	public void markCards() {
		for (BingoCard bingoCard : bingoCards) {
			bingoCard.repaint();
		}
	}
	
	public void setFiveInARow(int index, int[] indexes) {
		bingoCards[index].setIndex(indexes);
	}
	
	public void resetCards() {
		for (BingoCard bingoCard : bingoCards) {
			bingoCard.setIndex(new int[0]);
		}
	}
	
	public void setCallNumber(int number) {
		Color backgroundColor = Color.BLACK;
		Color foregroundColor = Color.WHITE;
		setCellLabel(number, backgroundColor, foregroundColor);
	}
	
	public void resetCallNumber(int number) {
		Color backgroundColor = new Color(238, 238, 238);
		Color foregroundColor = Color.BLACK;
		setCellLabel(number, backgroundColor, foregroundColor);
	}

	private void setCellLabel(int number, Color backgroundColor, 
			Color foregroundColor) {
		if (number >= 1 && number <= 15) {
			callLabels[number].setForeground(foregroundColor);
			callLabels[number].getParent().setBackground(backgroundColor);
		} else if (number >= 16 && number <= 30) {
			callLabels[number + 1].setForeground(foregroundColor);
			callLabels[number + 1].getParent().setBackground(backgroundColor);
		} else if (number >= 31 && number <= 45) {
			callLabels[number + 2].setForeground(foregroundColor);
			callLabels[number + 2].getParent().setBackground(backgroundColor);
		} else if (number >= 46 && number <= 60) {
			callLabels[number + 3].setForeground(foregroundColor);
			callLabels[number + 3].getParent().setBackground(backgroundColor);
		} else if (number >= 61 && number <= 75) {
			callLabels[number + 4].setForeground(foregroundColor);
			callLabels[number + 4].getParent().setBackground(backgroundColor);
		}
	}
	
}
