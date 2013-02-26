package by.bsuir.iit.abramov.ppvis.grapheditor.view.forms;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PLine;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PNode;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners.FormListener;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners.NodeListener;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners.NodeListener2;



public class PLayeredPane  extends JLayeredPane{
	private List<PNode> allNodes;
	private List<PNode> selectedNodes;
	private List<PLine> allLines;
	private List<PLine> selectedLines;
	private PLine currLine = null;
	final private int ID;
	private int editMode = 0;
	private JPanel panel;
	private static final int NODE = 100;
	private static final int LINE = 50;
	
	
	public PLayeredPane(int id, JPanel pan)
	{
		super();
		ID = id;
		panel = pan;
		allNodes = new ArrayList<PNode>();
		selectedNodes = new ArrayList<PNode>();
		allLines = new ArrayList<PLine>();
		selectedLines = new ArrayList<PLine>();
		addMouseListener(new FormListener());
		addMouseMotionListener(new FormListener());
	}
	
	//add
	
	public void addNode(PNode node)
	{
		System.out.println("PLayeredPane: addNode");
		add(node);
		setLayer(node, PLayeredPane.NODE);
		allNodes.add(node);
	}
	
	public void addLine(PLine line)
	{
		System.out.println("LayeredPane: addLine");
		add(line);
		setLayer(line, PLayeredPane.LINE);
		allLines.add(line);
		setCurrentLine(line);
	}
	
	public void addSelectedLines(PLine line)
	{
		System.out.println("PLayeredPane: addSelectedLine");
		selectedLines.add(line);
	}
	
	public void addSelectedNode(PNode node)
	{
		System.out.println("PLayeredPane: addSelectedNode");
		selectedNodes.add(node);
	}
	
	//get
	
	public PLine getCurrentLine()
	{
		return currLine;
	}
	
	public int getEditMode()
	{
		return editMode;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public int getSelectedCount()
	{
		return selectedNodes.size();
	}
	
	//set
	
	public void setCurrentLine(PLine line)
	{
		System.out.println("LayeredPane: setCurrentLine");
		currLine = line;
	}
	
	public void setEditMode(int mode)
	{
		if (editMode != mode)
		{
			System.out.println("setEditMode: " + mode);
			for (PNode currNode : allNodes)
			{
				currNode.setEditMode(mode);		
			}
			editMode = mode;
		}
	}
	
	//other
	
	public void deleteSelectedItems()
	{
		while(selectedLines.size() != 0)
		{
			PLine line = selectedLines.get(0);
			Rectangle rect = line.getBounds();
			line.disconnect();
			remove(line);
			repaint(rect);
			allLines.remove(line);
			selectedLines.remove(line);
		}
		while(selectedNodes.size() != 0)
		{
			PNode node = selectedNodes.get(0);
			Rectangle rect = node.getBounds();
			node.deleteLines();
			remove(node);
			repaint(rect);
			allNodes.remove(node);
			selectedNodes.remove(node);
		}
	}
	
	public void unselectAll()
	{
		System.out.println("LayeredPane + " + getID() +": unselectAll");
		while(selectedNodes.size() != 0)
		{
			selectedNodes.get(0).unselect();
			selectedNodes.remove(0);
		}
	}
	
	public void moveSelectedNodes(int aX, int aY)
	{
		System.out.println("PLayeredPane: moveSelectedNodes(" + aX + ", " + aY + ")");
		int newX = selectedNodes.get(0).getX(), newY = selectedNodes.get(0).getY(), pX = 0, pY = 0;
		for (PNode currNode : selectedNodes)
		{
			if (currNode.getX() + aX < newX)
				newX = currNode.getX() + aX;
			if (currNode.getY() + aY < newY)
				newY = currNode.getY() + aY;
		}
			if (newX > 0 && newY > 0)
			{
				for (PNode currNode : selectedNodes)
					currNode.setLocation(currNode.getX() + aX, currNode.getY() + aY);
			}
			else
			{
				if (newX < 0)
					pX = newX;
				if (newY < 0)
					pY = newY;
				panel.setBounds(panel.getX() + pX, panel.getY() + pY, panel.getWidth() + Math.abs(pX), panel.getHeight() + Math.abs(pY));
				moveNotSelectedNodes(Math.abs(pX), Math.abs(pY));
			}		
	}
	
	public void moveNotSelectedNodes(int aX, int aY)
	{
		System.out.println("PLayeredPane: moveNotSelectedNodes(" + aX + ", " + aY + ")");
		for (PNode currNode : allNodes)
		{
			if (!currNode.isSelected())
			{
				currNode.setLocation(currNode.getX() + aX, currNode.getY() + aY);
			}
		}
	}
	
	public void writeToComboBox(JComboBox combobox)
	{
		combobox.removeAllItems();
		for (PNode i : allNodes)
		{
			combobox.addItem(i);
		}
		for (PLine i : allLines)
		{
			combobox.addItem(i);
		}
	}
	
	public void removeLine(PLine line)
	{
		Rectangle rect = line.getBounds();
		selectedLines.remove(line);
		allLines.remove(line);
		remove(line);
		repaint(rect);
	}
}
