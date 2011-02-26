package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import domain.IDbObjectMember;


public class ObjectMemberCellRenderer extends DefaultTableCellRenderer
{

	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column )
	{

		IDbObjectMember member = (IDbObjectMember) value;
		super.getTableCellRendererComponent( table, member.getValueFormated(), isSelected,
				hasFocus, row, column );
		setForeground( new Color( 0, 0, 255 ) );

		return this;
	}
}