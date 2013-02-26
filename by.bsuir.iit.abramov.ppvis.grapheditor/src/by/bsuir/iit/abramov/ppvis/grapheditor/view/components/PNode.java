package by.bsuir.iit.abramov.ppvis.grapheditor.view.components;


import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PLine;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.forms.MainForm;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.forms.PLayeredPane;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners.NodeListener;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners.NodeListener2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;


public class PNode extends JComponent {
	private static int size = 20;
	private int n = 2;
	private int d = 3;
	private Color color = Color.BLACK;
	private List<PLine> lines;
	private int mouseX = 0;
	private int mouseY = 0;
	private Boolean selected = false;
	private PLayeredPane layeredPane;
	private int editMode = 0;
	
	public PNode(int x, int y, PLayeredPane pane)
	{
		setBounds(x - size / 2 , y - size / 2, size, size);
		layeredPane = pane;
		layeredPane.addNode(this);
		lines = new ArrayList<PLine>();
		//MainForm.addComponent(this);
	}
	
	//add
	
	public void addLine(PLine line)
	{
		lines.add(line);
	}
	
	//get
	
	public void deleteLine(PLine line)
	{
		lines.remove(line);
	}
	
	public void deleteLines()
	{
		Rectangle rec;
//		Iterator<PLine> iterator = lines.iterator();
//		while(iterator.hasNext()){
//			PLine next = iterator.next();
//		}
		while(lines.size() != 0)
		{
			rec = lines.get(0).getBounds();
			layeredPane.removeLine(lines.get(0));
			lines.get(0).deleteOtherNode(this);
			lines.remove(0);
			layeredPane.repaint(rec);
		}
	}
	
	public PLayeredPane getLayeredPane()
	{
		return layeredPane;
	}
	
	/*
	public int getLinesCount()
	{
		return lines.size();
	}*/
	
	public static int getBoundsSize()
	{
		return size;
	}
	
	public int getMouseX()
	{
		return mouseX;
	}
	
	public int getMouseY()
	{
		return mouseY;
	}
	
	public int getEditMode()
	{
		return editMode;
	}
	
	//set
	
	public void select()
	{
		selected = true;
		setColor(Color.GREEN);
		layeredPane.setComponentZOrder(this, 0);
		layeredPane.addSelectedNode(this);
		repaint();
	}
	
	public void selectRed() 
	{
		selected = true;
		setColor(Color.RED);
		getLayeredPane().setComponentZOrder(this, 0);
		repaint();
	}
	
	public void unselect()
	{
		System.out.println("Node: unselect()");
		selected = false;
		setColor(Color.BLACK);
		repaint();
	 }
	
	public void setColor(Color newColor)
	{
		color = newColor;
	}
	
	public void setProportion(int arg0, int arg1)
	{
		n = arg0;
		d = arg1;
	}
	
	public void setMouseX(int x)
	{
		mouseX = x;
	}
	
	public void setMouseY(int y)
	{
		mouseY = y;
	}
	
	public void setEditMode(int mode)
	{
		if (getEditMode() != mode)
		{
			switch(getEditMode())
			{
			case 0:
				removeMouseListener(getMouseListeners()[0]);
				removeMouseMotionListener(getMouseMotionListeners()[0]);
				NodeListener2 listener2 = new NodeListener2();
				addMouseListener(listener2);
				addMouseMotionListener(listener2);
				break;
			case 1:
				removeMouseListener(getMouseListeners()[0]);
				removeMouseMotionListener(getMouseMotionListeners()[0]);
				NodeListener listener = new NodeListener();
				addMouseListener(listener);
				addMouseMotionListener(listener);
				break;
			}
			editMode = mode;
		}
	}
	
	//other
	
	public boolean isSelected()
	{
		return selected;
	}
	
	
	//system
	
	@Override
	public void paintComponent(Graphics gr)
	{
		int diam = getWidth();
		int r2 = diam / 2;
		int r1 = (n * r2)/d;
		Graphics2D g2d = (Graphics2D)gr;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		g2d.fillOval(0, 0, diam, diam);
		g2d.setColor(Color.WHITE);
		g2d.fillOval((getWidth() - 2 * r1) / 2 , (getHeight() - 2 * r1) / 2, r1 * 2, r1 * 2);
	}
	
	@Override
	public boolean contains(int x, int y) {
		return Math.sqrt(Math.pow(x - size / 2, 2) + Math.pow(y - size / 2, 2)) < size / 2;	 
	}
}
