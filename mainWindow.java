
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class mainWindow extends JFrame implements ActionListener {
	private Game game;
	private helpSolve help;
	private JButton startBtn = new JButton("開始遊戲");
	private JButton endBtn = new JButton("結束遊戲");
	private JButton judgeBtn = new JButton("線上解題");

	public mainWindow() {
		// TODO Auto-generated constructor stub
		this.setSize(600, 600);
		this.setVisible(true);
		this.setLayout(null);
		help = new helpSolve();

		startBtn.setSize(100, 50);
		endBtn.setSize(100, 50);
		judgeBtn.setSize(100, 50);
		startBtn.setLocation(450, 400);
		endBtn.setLocation(450, 450);
		judgeBtn.setLocation(450, 500);
		startBtn.addActionListener(this);
		endBtn.addActionListener(this);
		judgeBtn.addActionListener(this);
		this.add(judgeBtn);
		this.add(startBtn);
		this.add(endBtn);

	}

	public void actionPerformed(ActionEvent e) {
		// System.out.println(e.getActionCommand());
		if (e.getActionCommand() == "開始遊戲") {
			game = new Game();
			this.add(game);
			startBtn.setVisible(false);
			endBtn.setVisible(false);
			judgeBtn.setVisible(false);

		}

		// else if(e.getActionCommand()=="線上解題")

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mainWindow m = new mainWindow();

	}

}
