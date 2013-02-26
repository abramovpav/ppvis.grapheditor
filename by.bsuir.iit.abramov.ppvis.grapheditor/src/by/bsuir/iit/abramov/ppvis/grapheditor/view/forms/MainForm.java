package by.bsuir.iit.abramov.ppvis.grapheditor.view.forms;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.WindowStateListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import by.bsuir.iit.abramov.ppvis.grapheditor.util.Factory;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners.NewTabListener;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners.ToolButtonsKeyListener;

public class MainForm extends JFrame {

	private static List<PLayeredPane> layeredPanes;
	private static JTabbedPane tabbedPane;
	private Map<Menu,JMenu> menuMap;
	private Map<MenuItems, JMenuItem> menuItems;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final MainForm frame = new MainForm();
					frame.setVisible(true);
					frame.addWindowStateListener(new WindowStateListener(){
						@Override
						public void windowStateChanged(WindowEvent arg0) {
							System.out.println("windowState: " + arg0.getNewState());
							if (arg0.getNewState() == 6 || arg0.getNewState() == 0)
							{
								System.out.println("window setBounds: " + (frame.getWidth() - 15) + " " + (frame.getHeight() - 58));
								frame.getContentPane().getComponents()[0].setBounds(0, 0, frame.getWidth() - 15, frame.getHeight() - 58);
							}
						}
					});
					frame.getContentPane().addComponentListener(new ComponentAdapter() {
						@Override
						public void componentResized(ComponentEvent arg0) {
							System.out.println("window: componentResized");
							frame.getContentPane().getComponents()[0].setBounds(0, 0, frame.getWidth() - 15, frame.getHeight() - 58);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("Graph Editor Paul");
		initialize();
	}
	
	public void initialize()
	{
		System.out.println("MainForm: initialize()");
		
		menuMap = new  HashMap<Menu,JMenu>() ;
		menuItems = new HashMap<MenuItems, JMenuItem>();
		layeredPanes = new ArrayList<PLayeredPane>(); 
		
		//<menu>
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		for(Menu menu : Menu.values())
		{
			JMenu mnButton = new JMenu(menu.getLabel());
			menuBar.add(mnButton);
			for (MenuItems menuIt : MenuItems.values())
				if (menuIt.getSection() == menu.getLabel())
				{
					JMenuItem menuItem = new JMenuItem(menuIt.getLabel());
					mnButton.add(menuItem);
					this.menuItems.put(menuIt, menuItem);
				}
			this.menuMap.put(menu, mnButton);
		}
		
		//</menu>
		
		JPanel contentPane = Factory.createJPanel(new EmptyBorder(5, 5, 5, 5), null, null);//new JPanel();
		setContentPane(contentPane);
		
		JPanel workPanel = Factory.createJPanel(null, new BorderLayout(0,0), new Rectangle(0, 0, 435, 242));//new JPanel();
		contentPane.add(workPanel);
		
		//<ToolPanel>
		
		JPanel toolPanel = Factory.createJPanel(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null), new BorderLayout(0, 0), null);//new JPanel();
		workPanel.add(toolPanel, BorderLayout.EAST);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		toolPanel.add(toolBar);
		//</ToolPanel>
		//<TabbedPane>
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFocusable(false);
		workPanel.add(tabbedPane, BorderLayout.CENTER);
		
		Factory.createJButton("New", true, new NewTabListener(), new ToolButtonsKeyListener(), toolBar);
		
		
		Factory.createJButton("Node", false, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("NodeButton: Click");
				layeredPanes.get(tabbedPane.getSelectedIndex()).setEditMode(0);				
			}
		}, null, toolBar);
		
		Factory.createJButton("Line", false, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("LineButton: Click");
				layeredPanes.get(tabbedPane.getSelectedIndex()).setEditMode(1);	
				layeredPanes.get(tabbedPane.getSelectedIndex()).unselectAll();
			}
		}, null, toolBar);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setFocusable(false);
		toolBar.add(comboBox);
		
		Factory.createJButton("Check", false, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPanes.get(tabbedPane.getSelectedIndex()).writeToComboBox(comboBox);
			}
		}, null, toolBar);
		//</TabbedPane>
	}
	
	//set
	
	//get
	
	public static List<PLayeredPane> getLayeredPanes() {
		return layeredPanes;
	}

	public static JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public static int getIndexCurrentPage()
	{
		return tabbedPane.getSelectedIndex();
	}
	
	public static PLayeredPane getCurrentLayeredPane()
	{
		return layeredPanes.get(getIndexCurrentPage());
	}
	
	//add
	
	public static void addLayeredPane(PLayeredPane layeredPane)
	{
		layeredPanes.add(layeredPane);
	}
}
