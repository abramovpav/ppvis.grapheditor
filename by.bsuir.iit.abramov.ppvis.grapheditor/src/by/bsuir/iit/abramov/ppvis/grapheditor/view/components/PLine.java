package by.bsuir.iit.abramov.ppvis.grapheditor.view.components;

import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PNode;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.forms.PLayeredPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JComponent;


public class PLine extends JComponent{
	private Color color = Color.BLACK;
	private PNode begNode = null;
	private PNode endNode = null;
	private int endX = 0;
	private int endY = 0;
	private Polygon polygon;
	private boolean selected = false;
	private PLayeredPane layeredPane;
	
	public PLine(PNode node, PLayeredPane lPane)
	{
		layeredPane = lPane;
		System.out.println("PLine()");
		endNode = null; 
		begNode = node;
		endX = begNode.getX();
		endY = begNode.getY();
		polygon = new Polygon();
		setBounds(begNode.getX(), begNode.getY(), endX, endY);
		begNode.addLine(this);
		begNode.getLayeredPane().addLine(this);
	}
	
	public void select()
	{
		setColor(Color.GREEN);
		selected = true;
		layeredPane.addSelectedLines(this);
		repaint();
	}
	
	public void unselect()
	{
		setColor(Color.BLACK);
		selected = false;
		repaint();
	}
	
	//get
	
	public PLayeredPane getLayeredPane()
	{
		return layeredPane;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	//set
	
	public void deleteOtherNode(PNode node)
	{
		if (node == begNode)
			endNode.deleteLine(this);
		else
			begNode.deleteLine(this);
	}
	
	public void disconnect()
	{
		begNode.deleteLine(this);
		endNode.deleteLine(this);
	}
	
	public void setEndCoord(int x, int y)
	{
		endX = x;
		endY = y;
		repaint();
	}
	
	public void setEndNode(PNode node)
	{
		if (begNode != node)
		{
			endNode = node;
			node.addLine(this);
			node.getLayeredPane().setCurrentLine(null);
			begNode.unselect();
			repaint();
		}
	}
	
	public void setColor(Color newColor)
	{
		color = newColor;
	}
	
	//other
	
	public void updateBounds()
	{
		int begX = begNode.getX(), begY = begNode.getY();
		if (endNode != null)
		{
			endX = endNode.getX();
			endY = endNode.getY();
		}
		int k = PNode.getBoundsSize() / 2;
		setBounds(Math.min(begX, endX), Math.min(begY, endY), Math.max(begX, endX) + k, Math.max(begY, endY) + k);
	}
	
	@Override
	public void paintComponent(Graphics g) { 
		Graphics2D g2d = (Graphics2D)g; 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(color);
		int begX = begNode.getX(), begY = begNode.getY();
		if (endNode != null)
		{
			endX = endNode.getX();
			endY = endNode.getY();
		}
		int width = Math.abs(begX - endX), height = Math.abs(begY - endY);
		int k = PNode.getBoundsSize() / 2;
		int x1, y1; 
		x1 = y1 = k;
		int x2 = width, y2 = height;
		if (begX > endX)
		{
			x1 += width;
			x2 -= width;
		}
		if (begY > endY)
		{
			y1  += height;
			y2 -= height;
		}
		setBounds(Math.min(begX, endX), Math.min(begY, endY), Math.max(begX, endX) + k, Math.max(begY, endY) + k);
		System.out.println("PLine getBounds():" + getBounds());
		g2d.drawLine(x1, y1, x2 + k, y2 + k);
		polygon.reset();
		if (x1-x2-k != 0)
		{
			double alpha = (double)Math.atan((double)(y1-y2-k)/(double)(x1-x2-k));
			System.out.println("alpha = " + Math.abs(y1-y2-k) + " /" + Math.abs(x1-x2-k) + " = " + alpha);
			int km = (int)Math.sin(alpha), cm = (int)Math.cos(alpha);
			//System.out.println(km + " " + cm);
			if (alpha < 0.644 && alpha > -0.644)
			{
				System.out.println("3");
				polygon.addPoint(x1, y1 -1);
				polygon.addPoint(x2 + k, y2 + k - 1);
				polygon.addPoint(x2 + k, y2 + k + 1);
				polygon.addPoint(x1, y1 + 1);
			}
			else
				if (alpha < 1.03 && alpha > 0.644)
				{
					System.out.println("4");
					polygon.addPoint(x1 + 1, y1 - 1);
					polygon.addPoint(x2 + k + 1, y2 + k - 1);
					polygon.addPoint(x2 + k - 1, y2 + k + 1);
					polygon.addPoint(x1 - 1, y1 + 1);
				}
				else
					if (alpha < -0.644 && alpha > -1.03)
					{
						System.out.println("2");
						polygon.addPoint(x1 - 1, y1 - 1);
						polygon.addPoint(x1 + 1, y1 + 1);
						polygon.addPoint(x2 + k + 1, y2 + k + 1);
						polygon.addPoint(x2 + k - 1, y2 + k - 1);
					}
					else
					{
						System.out.println("1");
						polygon.addPoint(x1 - 1, y1);
						polygon.addPoint(x1 + 1, y1);
						polygon.addPoint(x2 + k + 1, y2 + k);
						polygon.addPoint(x2 + k - 1, y2 + k + 1);
					}
		}
		else
		{
			System.out.println("1");
			polygon.addPoint(x1 - 1, y1);
			polygon.addPoint(x1 + 1, y1);
			polygon.addPoint(x2 + k + 1, y2 + k);
			polygon.addPoint(x2 + k - 1, y2 + k + 1);
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		return polygon.contains(x, y);
	}

}
