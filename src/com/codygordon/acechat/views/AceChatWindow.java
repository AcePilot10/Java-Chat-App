package com.codygordon.acechat.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JMenuBar;
import java.awt.GridLayout;

public class AceChatWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	public JPanel contentScreen;
	
	private int xx, xy;

	public AceChatWindow() {
		setResizable(false);
		setUndecorated(true);
		setTitle("Ace Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				AceChatWindow.this.setLocation(x - xx, y - xy);
			}
		});
		
		contentScreen = new JPanel();
		contentScreen.setBackground(Color.LIGHT_GRAY);
		contentScreen.setBounds(10, 56, 686, 432);
		contentPane.add(contentScreen);
		contentScreen.setLayout(new GridLayout(1, 0, 0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 612, 22);
		contentPane.add(menuBar);
		
		
		JLabel lblX = new JLabel("X");
		lblX.setBounds(682, 11, 14, 20);
		contentPane.add(lblX);
		lblX.setHorizontalAlignment(SwingConstants.TRAILING);
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(1);
			}
		});
		lblX.setForeground(Color.RED);
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 24));
	}
}
