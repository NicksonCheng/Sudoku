
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

@SuppressWarnings("serial")
public class Game extends JPanel implements ActionListener {
	private JTextField[] boardFinal = new JTextField[81];
	private int[] board = new int[81];
	private Random random = new Random();
	private ArrayList<Integer> leaveIndex = new ArrayList<Integer>();
	private Timer timer;
	private JLabel timeWatcher;
	private JLabel judgeJLabel = new JLabel("");
	private int time = 0;
	private JButton submitBtn = new JButton("確定");
	private JButton giveupBtn = new JButton("我放棄...");
	private JButton hintBtn = new JButton("提示");
	private Thread thread = new Thread();
	private int firstEmpty = 0;

	public Game() {
		// TODO Auto-generated constructor stub
		this.setSize(600, 600);
		// BorderLayout border=new BorderLayout();
		this.setLayout(null);
		// Border border = new LineBorder(Color.black, 3, true);
		// this.setBorder(border);
		timeWatcher = new JLabel("時間:");
		timeWatcher.setSize(50, 50);
		judgeJLabel.setSize(50, 50);
		judgeJLabel.setForeground(Color.red);
		judgeJLabel.setLocation(450, 200);
		// timeWatcher.setLocation(450,450);
		this.add(timeWatcher);
		this.add(judgeJLabel);

		submitBtn.setSize(100, 50);
		giveupBtn.setSize(100, 50);
		hintBtn.setSize(100, 50);
		submitBtn.setLocation(450, 400);
		giveupBtn.setLocation(450, 450);
		hintBtn.setLocation(450, 500);
		submitBtn.addActionListener(this);
		giveupBtn.addActionListener(this);
		hintBtn.addActionListener(this);
		this.add(submitBtn);
		this.add(giveupBtn);
		this.add(hintBtn);

		// System.out.print("successful");
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 9; ++j) {
				boardFinal[j + 9 * i] = new JTextField(" ");
				boardFinal[j + 9 * i].setSize(50, 50);
				boardFinal[j + 9 * i].setLocation(0 + 50 * j, 50 + 50 * i);
				if (i % 3 == 0 && j % 3 == 0) {
					boardFinal[j + 9 * i].setBorder(new MatteBorder(4, 4, 1, 1, Color.black));
				} else if (i % 3 == 0)
					boardFinal[j + 9 * i].setBorder(new MatteBorder(4, 1, 1, 1, Color.black));
				else if (j % 3 == 0) {
					boardFinal[j + 9 * i].setBorder(new MatteBorder(1, 4, 1, 1, Color.black));
				} else {
					boardFinal[j + 9 * i].setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
				}
				this.add(boardFinal[j + 9 * i]);
				board[j + 9 * i] = -1;
			}

		}

		initBoard();
		// add jtextfield focus
		jtextfieldListener();
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				++time;

				timeWatcher.setText("時間:" + Integer.toString(time));
			}
		}, 0, 1000);
		this.setVisible(true);
	}

	public void jtextfieldListener() {
		// int firstEmpty = 0;
		for (int i = 0; i < 81; ++i) {
			if (boardFinal[i].getText().isEmpty()) {
				firstEmpty = i;
				break;
			}
		}
		// just search later
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				boardFinal[firstEmpty].requestFocus();
			}
		});
		for (int i = 0; i < 81; ++i) {

			boardFinal[i].addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					for (int i = 0; i < 81; ++i) {

						if (e.getSource() == boardFinal[i]) {
							String input = boardFinal[i].getText();
							String ans = Integer.toString(board[i]);
							if (!input.isEmpty()) {

								// System.out.println(boardFinal[i].getText());
								// System.out.println(Integer.toString(board[i]));
								Timer t = new Timer();
								if (!input.equals(ans)) {
									judgeJLabel.setText("錯誤!");
									boardFinal[i].setText("");

								} else {
									judgeJLabel.setText("正確!");
									boardFinal[i].setEditable(false);
								}

							}

						}
						// System.out.println(boardFinal[index].getText());
					}

				}

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					// System.out.println("in!!");
				}
			});

		}
	}

	public void actionPerformed(ActionEvent e) {
		// System.out.println(e.getActionCommand());
		if (e.getActionCommand() == "蝣箏��") {

		}

	}

	public void randomInit() {
		int pos;
		int num = -1;

		// ����������
		for (int i = 0; i < 81; ++i)
			board[i] = -1;
		leaveIndex.clear();
		for (int i = 0; i < 25; ++i) {
			pos = random.nextInt(81);
			if (board[pos] < 0) {
				do {

					int j = random.nextInt(9);
					if (boardRule(pos, j))
						num = j;

				} while (num < 0);
				// System.out.println(num);
				board[pos] = num;
				num = -1;
			} else {
				--i;
				continue;
			}

		}

	}

	public void initBoard() {

		/*
		 * randomInit(); for(int i=0;i<=72;i+=9) { for(int j=0;j<9;++j) {
		 * System.out.print(board[i+j]+1+" "); } System.out.println(); }
		 */
		boolean flag;
		do {

			// System.out.println("restart");
			randomInit();
			// for (int i = 0; i <= 72; i += 9) {
			// for (int j = 0; j < 9; ++j) {
			// System.out.print(board[i + j] + 1 + " ");
			// }
			// System.out.println();
			// }
			// System.out.println();
			for (int i = 0; i < 81; ++i) {
				if (board[i] < 0) {
					leaveIndex.add(i);

				}

			}
			int n = 0;
			flag = solveSudoku(n);

		} while (!flag);
		// digBoard
		String s;
		for (int i = 0; i < 81; ++i) {
			// change to normal num
			++board[i];
			if (board[i] > 0) {
				s = Integer.toString(board[i]);
				boardFinal[i].setText(s);
				boardFinal[i].setEditable(false);
			}

		}
		// digBoard=Arrays.copyOf(board, 81);

		for (int i = 0; i < 45; ++i) {
			int n = random.nextInt(81);

			boardFinal[n].setText("");
			boardFinal[n].setEditable(true);

		}

	}

	public boolean solveSudoku(int n) {

		// teminate����摨��
		if (n >= leaveIndex.size())
			return true;

		int pos = leaveIndex.get(n);
		boolean correct = true;
		ArrayList<Integer> exist = new ArrayList<Integer>();
		for (int i = 0; i < 9; ++i)
			exist.add(i);
		// ���row鋆⊿��蝑�
		int rowPos = pos / 9;
		// 瘥�olumn瑼Ｘ
		for (int j = rowPos * 9; j < rowPos * 9 + 9; ++j) {
			// System.out.print(board[j]+" ");
			if (board[j] >= 0 && exist.indexOf(board[j]) >= 0) {
				int index = exist.indexOf(board[j]);
				exist.remove(index);
			}

		}
		// System.out.println();

		// ���column��蝑�
		int columnPos = pos % 9;
		// System.out.println("column=" + columnPos);

		for (int i = 0; i <= 72; i += 9) {
			// System.out.println(board[columnPos + i]);
			if (board[columnPos + i] >= 0 && exist.indexOf(board[columnPos + i]) >= 0) {
				int index = exist.indexOf(board[columnPos + i]);
				exist.remove(index);
			}

		}
		int boxPos = 0;
		// ���銋悅���蝑�
		for (int k = 0; k <= 6; k += 3) {
			for (int p = 0; p <= 54; p += 27) {
				if (pos == k + p)
					boxPos = k + p;
				else if (pos == k + p + 1)
					boxPos = k + p;
				else if (pos == k + p + 2)
					boxPos = k + p;
				else if (pos == k + p + 9)
					boxPos = k + p;
				else if (pos == k + p + 10)
					boxPos = k + p;
				else if (pos == k + p + 11)
					boxPos = k + p;
				else if (pos == k + p + 18)
					boxPos = k + p;
				else if (pos == k + p + 19)
					boxPos = k + p;
				else if (pos == k + p + 20)
					boxPos = k + p;

			}
		}
		// System.out.println("boxPos=" + boxPos);
		for (int i = 0; i <= 18; i += 9) {
			for (int j = 0; j < 3; ++j) {
				// System.out.println(board[i + j + boxPos]);
				if (board[i + j + boxPos] >= 0 && exist.indexOf(board[i + j + boxPos]) >= 0) {
					// System.out.println("successful");
					int index = exist.indexOf(board[i + j + boxPos]);
					exist.remove(index);
				}
			}
		}

		// ��xist
		/*
		 * System.out.print("exist="); for (int i = 0; i < exist.size(); ++i)
		 * System.out.print(exist.get(i) + 1); System.out.println();
		 */

		// 憛怠
		if (exist.size() > 0) {

			board[pos] = exist.get(0);
			exist.remove(0);

			// ��oard��靘�
			/*
			 * for (int i = 0; i < 81; i += 9) { for (int j = 0; j < 9; ++j) { if (i + j ==
			 * pos) System.out.print(board[i + j] + 1 + "* "); else System.out.print(board[i
			 * + j] + 1 + " "); } System.out.println(); } System.out.println();
			 */

			correct = solveSudoku(n + 1);
		} else {
			// System.out.println("empty!!!!!!!!!!!!");
			return false;
		}
		while (!correct) {
			if (exist.size() <= 0) {
				board[pos] = -1;
				// System.out.println("empty!!!!!!!!!!!!");
				return false;

			}

			else {
				board[pos] = exist.get(0);
				exist.remove(0);
				correct = solveSudoku(n + 1);
			}

		}
		return true;

	}

	public boolean boardRule(int pos, int num) {

		// ���row鋆⊿��蝑�
		int rowPos = pos / 9;
		// 瘥�olumn瑼Ｘ
		for (int j = rowPos * 9; j < rowPos * 9 + 9; ++j) {
			// System.out.print(board[j]+" ");
			if (board[j] == num)
				return false;
		}
		// System.out.println();

		// ���column��蝑�
		int columnPos = pos % 9;
		for (int i = 0; i <= 72; i += 9) {

			if (board[columnPos + i] == num)
				return false;
		}

		int boxPos = 0;
		// ���銋悅���蝑�
		for (int k = 0; k <= 6; k += 3) {
			for (int p = 0; p <= 54; p += 27) {
				if (pos == k + p)
					boxPos = k + p;
				else if (pos == k + p + 1)
					boxPos = k + p;
				else if (pos == k + p + 2)
					boxPos = k + p;
				else if (pos == k + p + 9)
					boxPos = k + p;
				else if (pos == k + p + 10)
					boxPos = k + p;
				else if (pos == k + p + 11)
					boxPos = k + p;
				else if (pos == k + p + 18)
					boxPos = k + p;
				else if (pos == k + p + 19)
					boxPos = k + p;
				else if (pos == k + p + 20)
					boxPos = k + p;

			}
		}

		for (int i = 0; i < 18; i += 9) {
			for (int j = 0; j < 3; ++j) {

				if (board[i + j + boxPos] == num) {
					// System.out.println("hey");
					return false;
				}

			}
		}
		return true;

	}

}
