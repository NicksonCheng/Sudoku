import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TimerTask;

import javax.naming.NoInitialContextException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import javax.swing.SwingUtilities;

public class helpSolve extends JPanel implements ActionListener {
	private SolveSudoku solve = new SolveSudoku();
	private JTextField[] ansBoard = new JTextField[81];
	private mainWindow main;

	public helpSolve(mainWindow m) {
		// TODO Auto-generated constructor stub
		main = m;
		this.setSize(600, 600);
		this.setLayout(null);
		JButton output = new JButton("送出");
		JButton last = new JButton("回到主頁");
		output.setSize(100, 50);
		output.setLocation(450, 400);
		output.addActionListener(this);
		last.setSize(100, 50);
		last.setLocation(450, 450);
		last.addActionListener(this);
		this.add(last);
		this.add(output);

		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 9; ++j) {
				ansBoard[j + 9 * i] = new JTextField("E");
				ansBoard[j + 9 * i].setSize(50, 50);
				ansBoard[j + 9 * i].setLocation(0 + 50 * j, 50 + 50 * i);
				if (i % 3 == 0 && j % 3 == 0) {
					ansBoard[j + 9 * i].setBorder(new MatteBorder(4, 4, 1, 1, Color.black));
				} else if (i % 3 == 0)
					ansBoard[j + 9 * i].setBorder(new MatteBorder(4, 1, 1, 1, Color.black));
				else if (j % 3 == 0) {
					ansBoard[j + 9 * i].setBorder(new MatteBorder(1, 4, 1, 1, Color.black));
				} else {
					ansBoard[j + 9 * i].setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
				}
				this.add(ansBoard[j + 9 * i]);
			}

		}
		/*
		 * for (int i = 0; i < 81; ++i) { ansBoard[i].addFocusListener(new
		 * FocusListener() { public void focusGained(FocusEvent e) {
		 * System.out.println("gain!!!"); }
		 * 
		 * public void focusLost(FocusEvent e) { // displayMessage("Focus lost", e); }
		 * });
		 * 
		 * }
		 */
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if (e.getActionCommand() == "送出") {
			int count = 0;
			for (int i = 0; i < 81; ++i) {

				if (!ansBoard[i].getText().equals("E"))
					++count;
			}
			if (count <= 17) {
				JOptionPane error = new JOptionPane();
				error.showMessageDialog(null, "請輸入17個以上數字!!", "錯誤", JOptionPane.WARNING_MESSAGE);

			} else {

				// 開始解題
				ArrayList<Integer> leaveIndex = new ArrayList<Integer>();
				int[] board = new int[81];
				for (int i = 0; i < 81; ++i) {
					board[i] = -1;
					if (ansBoard[i].getText().equals("E")) {
						// System.out.println(i);
						leaveIndex.add(i);
					}

					else {
						System.out.println("hey" + ansBoard[i].getText());
						board[i] = Integer.valueOf(ansBoard[i].getText()) - 1;
					}

				}
				for (int i = 0; i < 81; i += 9) {
					for (int j = 0; j < 9; ++j)
						System.out.print(board[i + j]);
					System.out.println();
				}

				boolean flag = solve.solveSudoku(0, leaveIndex, board);
				// System.out.println(flag);
				if (flag) {
					// 有解
					for (int i = 0; i < 81; ++i)
						ansBoard[i].setText(Integer.toString(board[i]) + 1);
				} else {
					JOptionPane wrongAns = new JOptionPane();
					wrongAns.showMessageDialog(null, "無解!", "錯誤", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		} else if (e.getActionCommand() == "回到主頁") {
			this.setVisible(false);
			main.help = null;
			main.startBtn.setVisible(true);
			main.endBtn.setVisible(true);
			main.judgeBtn.setVisible(true);
		}
	}

}
