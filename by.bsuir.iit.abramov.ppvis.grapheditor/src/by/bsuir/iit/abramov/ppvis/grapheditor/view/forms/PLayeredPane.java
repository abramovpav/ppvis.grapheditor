package by.bsuir.iit.abramov.ppvis.grapheditor.view.forms;

import java.util.Iterator;
import java.util.List;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;

import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PLine;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PNode;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners.FormListener;



public class PLayeredPane  extends JLayeredPane{
	private static final int NODE = 100;
	private static final int LINE = 50;
	private List<PNode> selectedNodes;
	private List<PLine> selectedLines;
	private List<PNode> allNodes;
	private List<PLine> allLines;
	private PLine currLine = null;
	private int editMode = 0;
	private final int ID;
	private JPanel panel;
	
	
	
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
		Iterator<PLine> iterator = selectedLines.iterator();
		while(iterator.hasNext())
		{
			PLine line = iterator.next();
			Rectangle rect = line.getBounds();
			line.disconnect();
			remove(line);
			repaint(rect);
			allLines.remove(line);
			iterator.remove();
		}
		Iterator<PNode> iteratorNode = selectedNodes.iterator();
		while(iteratorNode.hasNext())
		{
			System.out.println(selectedNodes.size());
			PNode node = iteratorNode.next();
			Rectangle rect = node.getBounds();
			node.deleteLines();
			remove(node);
			repaint(rect);
			allNodes.remove(node);
			iteratorNode.remove();
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
				currNode.linesUpdateBounds();
			}
		}
	}
	
	public void unselectNode(PNode node)
	{
		selectedNodes.remove(node);
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
	
	public void removeLine(PLine line)
	{
		Rectangle rect = line.getBounds();
		selectedLines.remove(line);
		allLines.remove(line);
		remove(line);
		repaint(rect);
	}
	
	public void unselectAll()
	{
		System.out.println("LayeredPane + " + getID() +": unselectAll");
		Iterator<PLine> itrLine = selectedLines.iterator();  
		while(itrLine.hasNext())
		{
			itrLine.next().unselect();
			itrLine.remove();
		}
		Iterator<PNode> itrNode = selectedNodes.iterator();
		while(itrNode.hasNext())
		{
			itrNode.next().unselect();
			itrNode.remove();
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
}
