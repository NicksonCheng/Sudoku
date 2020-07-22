
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
	private int firstEmpty = 0;
	private JButton newGame = new JButton("新遊戲");
	private JButton state = new JButton("暫停");
	private JButton hintBtn = new JButton("主頁面");
	private Thread thread = new Thread();

	private SolveSudoku solve;
	private boolean timeState = true;
	private JOptionPane win = new JOptionPane();
	private FocusListener focus;
	private mainWindow main;

	public Game(mainWindow m) {
		solve = new SolveSudoku();
		// gain mainwindow pointer
		main = m;
		// TODO Auto-generated constructor stub
		this.setSize(600, 600);
		// BorderLayout border=new BorderLayout();
		this.setLayout(null);
		// Border border = new LineBorder(Color.black, 3, true);
		// this.setBorder(border);
		timeWatcher = new JLabel("時間:");
		timeWatcher.setSize(50, 50);
		this.add(timeWatcher);
		this.add(win);
		judgeJLabel.setSize(50, 50);
		judgeJLabel.setForeground(Color.red);
		judgeJLabel.setLocation(450, 200);
		// timeWatcher.setLocation(450,450);

		this.add(judgeJLabel);

		newGame.setSize(100, 50);
		state.setSize(100, 50);
		hintBtn.setSize(100, 50);
		newGame.setLocation(450, 400);
		state.setLocation(450, 450);
		hintBtn.setLocation(450, 500);
		newGame.addActionListener(this);
		state.addActionListener(this);
		hintBtn.addActionListener(this);
		this.add(newGame);
		this.add(state);
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
				// System.out.println(timeState);
				if (timeState) { // TODO Auto-generated method stub
					++time;

					timeWatcher.setText("時間:" + Integer.toString(time));
				}

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
		focus = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				int editAble = 0;
				for (int i = 0; i < 81; ++i) {
					if (!boardFinal[i].isEditable())
						++editAble;
				}
				if (editAble >= 81) {
					JOptionPane win = new JOptionPane();
					win.showConfirmDialog(null, "你贏了!!", "勝利", JOptionPane.OK_OPTION);
					for (int i = 0; i < 81; ++i) {
						boardFinal[i].removeFocusListener(focus);
					}
				} else {
					for (int i = 0; i < 81; ++i) {

						if (e.getSource() == boardFinal[i]) {
							String input = boardFinal[i].getText();
							String ans = Integer.toString(board[i]);
							if (!input.isEmpty()) {

								// System.out.println(boardFinal[i].getText());
								System.out.println(Integer.toString(board[i]));
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
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("in!!");
			}
		};
		for (int i = 0; i < 81; ++i) {

			boardFinal[i].addFocusListener(focus);

		}

	}

	public void actionPerformed(ActionEvent e) {
		// System.out.println(e.getActionCommand());
		if (e.getActionCommand() == "新遊戲") {
			initBoard();
		} else if (e.getActionCommand() == "暫停") {
			for (int i = 0; i < 81; ++i) {
				if (boardFinal[i].isEditable()) {
					boardFinal[i].setBackground(Color.yellow);
					boardFinal[i].setText("E");
					boardFinal[i].setEditable(false);
				}

			}
			this.state.setText("開始");
			this.timeWatcher.setText("時間:0");
			timeState = false;
			time = 0;
		} else if (e.getActionCommand() == "開始") {
			for (int i = 0; i < 81; ++i) {
				if (boardFinal[i].getText().equals("E")) {
					boardFinal[i].setBackground(Color.white);
					boardFinal[i].setText("");
					boardFinal[i].setEditable(true);
				}

			}
			this.state.setText("暫停");
			timeState = true;
		} else if (e.getActionCommand() == "主頁面") {
			this.setVisible(false);
			main.game = null;
			main.startBtn.setVisible(true);
			main.endBtn.setVisible(true);
			main.judgeBtn.setVisible(true);
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
		boolean flag;
		do {

			randomInit();
			// for (int i = 0; i < 81; i += 9) {
			// for (int j = 0; j < 9; ++j)
			// System.out.print(board[i + j]);
			// System.out.println();
			// }
			// System.out.println();

			for (int i = 0; i < 81; ++i) {
				if (board[i] < 0) {
					leaveIndex.add(i);

				}

			}
			int n = 0;
			flag = solve.solveSudoku(n, leaveIndex, board);

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
		// boardFinal[0].setText("");
		// boardFinal[0].setEditable(true);
		// digBoard=Arrays.copyOf(board, 81);

		ArrayList<Integer> num = new ArrayList<Integer>();
		for (int i = 0; i < 81; ++i)
			num.add(i);
		for (int i = 0; i < 45; ++i) {
			int n = random.nextInt(num.size());
			int index = num.get(n);
			boardFinal[index].setText("");
			boardFinal[index].setEditable(true);
			num.remove(n);
		}

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
