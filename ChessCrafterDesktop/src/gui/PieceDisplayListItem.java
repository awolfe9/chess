package gui;

import javax.swing.ImageIcon;

import utility.PieceIconUtility;

public class PieceDisplayListItem
{
	public PieceDisplayListItem(String name)
	{
		this.name = name;
		lightImage = PieceIconUtility.getPieceIcon(name, false);
		darkImage = PieceIconUtility.getPieceIcon(name, true);
	}
	public ImageIcon lightImage;
	public ImageIcon darkImage;
	public String name;
	
	@Override
	public String toString()
	{
		return name;
	}
}
