
package gui;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import models.Piece;
import utility.PieceIconUtility;
import utility.PreferenceUtility;
import utility.PreferenceUtility.PieceToolTipPreferenceChangedListener;
import controllers.GameController;
import dragNdrop.DropAdapter;
import dragNdrop.DropEvent;
import dragNdrop.DropManager;
import dragNdrop.GlassPane;

public class SquareListener extends DropAdapter implements MouseListener, PieceToolTipPreferenceChangedListener
{
	public SquareListener(SquareJLabel squareLabel, DropManager dropManager, GlassPane glassPane)
	{
		super(glassPane);
		mSquareLabel = squareLabel;
		mDropManager = dropManager;
		addDropListener(mDropManager);
		PreferenceUtility.addPieceToolTipListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent event)
	{
	}

	@Override
	public void mouseEntered(MouseEvent event)
	{
	}

	@Override
	public void mouseExited(MouseEvent event)
	{
	}

	@Override
	public void mousePressed(MouseEvent event)
	{
		// TODO: dropping from a jail currently doesn't work
		// if (m_nextMoveMustPlacePiece)
		// {
		// m_nextMoveMustPlacePiece = false;
		// getGame().nextTurn();
		// if (!m_clickedSquare.isOccupied() &&
		// m_clickedSquare.isHabitable() && m_pieceToPlace != null)
		// {
		// m_pieceToPlace.setSquare(m_clickedSquare);
		// m_clickedSquare.setPiece(m_pieceToPlace);
		// m_pieceToPlace = null;
		// m_nextMoveMustPlacePiece = false;
		// boardRefresh(getGame().getBoards());
		// getGame().genLegalDests();
		// }
		//
		// return;
		// }

		/*
		 * if (mSquareLabel.getPiece() == null || mSquareLabel.getPiece().isBlack() != getGame().isBlackMove())
		 * {
		 * return;
		 * }
		 */

		List<SquareJLabel> destinationLabels = PlayGamePanel.highlightLegalDestinations(mSquareLabel.getCoordinates());

		mDropManager.setComponentList(destinationLabels);
		mSquareLabel.hideIcon();

		Driver.getInstance().setGlassPane(mGlassPane);
		Component component = event.getComponent();

		mGlassPane.setVisible(true);

		Point point = (Point) event.getPoint().clone();
		SwingUtilities.convertPointToScreen(point, component);
		SwingUtilities.convertPointFromScreen(point, mGlassPane);

		mGlassPane.setPoint(point);

		BufferedImage image = null;

		Piece piece = mSquareLabel.getPiece();
		ImageIcon imageIcon = PieceIconUtility.getPieceIcon(piece.getPieceType().getName(), 48, piece.getTeamId(GameController.getGame()));
		int width = imageIcon.getIconWidth();
		int height = imageIcon.getIconHeight();
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = (Graphics2D) image.getGraphics();
		imageIcon.paintIcon(null, graphics2D, 0, 0);
		graphics2D.dispose();

		mGlassPane.setImage(image);

		mGlassPane.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent event)
	{
		Point point = (Point) event.getPoint().clone();
		SwingUtilities.convertPointToScreen(point, event.getComponent());

		mGlassPane.setImage(null);
		mGlassPane.setVisible(false);

		fireDropEvent(new DropEvent(point, mSquareLabel), false);
	}

	@Override
	public void onPieceToolTipPreferenceChanged()
	{
		mSquareLabel.refresh();
	}

	private SquareJLabel mSquareLabel;
	private final DropManager mDropManager;
}
