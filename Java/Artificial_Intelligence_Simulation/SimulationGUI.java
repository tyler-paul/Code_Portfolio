import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.File;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class SimulationGUI extends JFrame {
	static Container pane;
	static final int GRID_DIM = 5;
	static final int IMAGE_HEIGHT = 100;
	static final int IMAGE_WIDTH = 100;
	static final int CONTROLS_HEIGHT = 100;
	
	static volatile boolean[] isClean = new boolean[GRID_DIM * GRID_DIM];
	static volatile int currentState = 0;
	static final BufferedImage emptyClean;
	static final BufferedImage emptyDirty;
	static final BufferedImage vacClean;
	static final BufferedImage vacDirty;
	static boolean initialStateSet = false;
	static volatile LinkedList<Integer> dirtySpots = new LinkedList<Integer>();

	//load images
	static {
		BufferedImage tmp1 = null;
		BufferedImage tmp2 = null;
		BufferedImage tmp3 = null;
		BufferedImage tmp4 = null;
		try {
			tmp1 = ImageIO.read(new File("Clean.png"));
			tmp2 = ImageIO.read(new File("Dirty.png"));
			tmp3 = ImageIO.read(new File("VacClean.png"));
			tmp4 = ImageIO.read(new File("VacDirty.png"));

		} catch (Exception e) {
		}
		emptyClean = tmp1;
		emptyDirty = tmp2;
		vacClean = tmp3;
		vacDirty = tmp4;
	}

	public void doAction(String command) {
		if (command.equals("right"))
			goRight();
		else if (command.equals("left"))
			goLeft();
		else if (command.equals("up"))
			goUp();
		else if (command.equals("down"))
			goDown();
		else if (command.equals("suck"))
			suck();
	}

	private JLabel getRoomSection(int indexInRoom) {
		return (JLabel)(((JPanel)pane.getComponent(1)).getComponent(indexInRoom));
	}
	
	public void goRight() {
		if ((currentState + 1) % GRID_DIM != 0) { //can only go left if not at the right edge of the room
			if (isClean[currentState])
				getRoomSection(currentState).setIcon(new ImageIcon(emptyClean));
			else
				getRoomSection(currentState).setIcon(new ImageIcon(emptyDirty));
			if (isClean[currentState + 1])
				getRoomSection(currentState + 1).setIcon(new ImageIcon(vacClean));
			else
				getRoomSection(currentState + 1).setIcon(new ImageIcon(vacDirty));

			// update state
			currentState += 1;
		}
	}

	public void goLeft() {
		if (currentState % GRID_DIM != 0) { //can only go left if not at the left edge of the room
			if (isClean[currentState])
				getRoomSection(currentState).setIcon(new ImageIcon(emptyClean));
			else
				getRoomSection(currentState).setIcon(new ImageIcon(emptyDirty));
			if (isClean[currentState - 1])
				getRoomSection(currentState - 1).setIcon(new ImageIcon(vacClean));
			else
				getRoomSection(currentState - 1).setIcon(new ImageIcon(vacDirty));

			currentState -= 1;
		}
	}

	public void goUp() {
		int n = GRID_DIM;
		if (currentState >= n) { //can only go left if not at the top of the room
			if (isClean[currentState])
				getRoomSection(currentState).setIcon(new ImageIcon(emptyClean));
			else
				getRoomSection(currentState).setIcon(new ImageIcon(emptyDirty));
			if (isClean[currentState - n])
				getRoomSection(currentState - n).setIcon(new ImageIcon(vacClean));
			else
				getRoomSection(currentState - n).setIcon(new ImageIcon(vacDirty));

			currentState -= n;
		}
	}

	public void goDown() { 
		int n = GRID_DIM;
		if (currentState < n * n - n) { //can only go left if not at the bottom of the room
			if (isClean[currentState])
				getRoomSection(currentState).setIcon(new ImageIcon(emptyClean));
			else
				getRoomSection(currentState).setIcon(new ImageIcon(emptyDirty));
			if (isClean[currentState + n])
				getRoomSection(currentState + n).setIcon(new ImageIcon(vacClean));
			else
				getRoomSection(currentState + n).setIcon(new ImageIcon(vacDirty));
			currentState += n;
		}
	}

	public void suck() {
		isClean[currentState] = true;
		getRoomSection(currentState).setIcon(new ImageIcon(vacClean));
	}
	
	public SimulationGUI() {
		pane = getContentPane();
		JPanel roomPanel = new JPanel(); //panel the vacuum will move around in
		setupRoom(roomPanel);
		
		JPanel controlsPanel = new JPanel(); //panel for the controls the user will use
		setupControls(controlsPanel);
		
		pane.add(controlsPanel, BorderLayout.SOUTH);
		pane.add(roomPanel, BorderLayout.NORTH);
	}
	
	private void setupRoom(JPanel roomPanel) {
		roomPanel.setLayout(new GridLayout(GRID_DIM, GRID_DIM));
		
		//room is initially clean until the user chooses dirty spots
		for (int i = 0; i < GRID_DIM * GRID_DIM; i++)
			isClean[i] = true;
		
		for (int index = 0; index < GRID_DIM * GRID_DIM; index++) {
			final int j = index;
			final JLabel picLabel = new JLabel(new ImageIcon(emptyClean));
			picLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			picLabel.addMouseListener(new MouseListener() {
				private int num = 0;

				public void mouseClicked(MouseEvent e) {
					if (!initialStateSet) { //first click sets location of the vacuum
						picLabel.setIcon(new ImageIcon(vacClean));
						initialStateSet = true;
						currentState = j;
					} else { //other clicks will set location of dirt
						if (num++ % 2 == 0) {
							isClean[j] = false;
							dirtySpots.add(j);
							if (currentState == j)
								picLabel.setIcon(new ImageIcon(vacDirty));
							else
								picLabel.setIcon(new ImageIcon(emptyDirty));
						} else {
							dirtySpots.remove(new Integer(j));
							isClean[j] = true;
							if (currentState == j)
								picLabel.setIcon(new ImageIcon(vacClean));
							else
								picLabel.setIcon(new ImageIcon(emptyClean));
						}
					}
				}
				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}
			});
			roomPanel.add(picLabel);
		}
	}
	
	private void setupControls(JPanel controls) {
		JButton startSimButton = new JButton("Start Simulation");
		startSimButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				//get actions to be performed
				LinkedList<String> actions = ActionGetter.getActions(currentState, isClean, dirtySpots);
				System.out.println(actions);
				
				//schedule for an action to be performed ever half second
				int delay = 0;
		        int period = 500;
				Timer timer = new Timer();
				class ActionIndexWrapper {
					int index = 0;
				}
				final ActionIndexWrapper wrapper = new ActionIndexWrapper();;
				TimerTask task = new TimerTask()
				{
				     public void run()
				     {
				    	 
				    	 doAction(actions.get(wrapper.index));
				    	 wrapper.index++;
				    	 if (wrapper.index == actions.size())
				    		 cancel();
				     }
				};
				timer.scheduleAtFixedRate(task, delay, period);
				
				dirtySpots.clear();
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {	
			}
			public void mouseReleased(MouseEvent e) {
			}
		});
		controls.add(startSimButton);
	}
	
	public static void main(String[] args) throws Exception {
		SimulationGUI gst = new SimulationGUI();
		gst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gst.pack();
		gst.setVisible(true);
		gst.setSize(IMAGE_WIDTH * GRID_DIM, IMAGE_HEIGHT * GRID_DIM + CONTROLS_HEIGHT);
	}
}
