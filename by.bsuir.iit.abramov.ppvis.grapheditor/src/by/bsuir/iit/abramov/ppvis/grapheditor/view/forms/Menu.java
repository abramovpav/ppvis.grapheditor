package by.bsuir.iit.abramov.ppvis.grapheditor.view.forms;

public enum Menu
{
	FILE("File"), EDIT("Edit"), INSTRUMENTS("Instruments");
	private String label;
	private Menu(String label) 
	{
		
		this.label = label;
	}
	
	public String getLabel() 
	{
		return label;
	}
}
