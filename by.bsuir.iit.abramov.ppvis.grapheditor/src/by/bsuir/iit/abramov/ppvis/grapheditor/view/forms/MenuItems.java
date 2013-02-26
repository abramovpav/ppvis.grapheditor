package by.bsuir.iit.abramov.ppvis.grapheditor.view.forms;

public enum MenuItems 
{	
	OPEN("File", "Open..."), SAVE("File", "Save..."), SAVEAS("File", "Save as..."), EXIT("File", "Exit"),
	SELECT_ALL("Edit", "Select All"), UNSELECT_ALL("Edit", "Unselect All"),
	NODE("Instruments", "Node"), LINE("Instruments", "Line");
	private String label;
	private String section;
	private MenuItems(String section, String label) 
	{
		this.section = section;
		this.label = label;
	}
	
	public String getLabel() 
	{
		return label;
	}
	
	public String getSection()
	{
		return section;
	}
}
