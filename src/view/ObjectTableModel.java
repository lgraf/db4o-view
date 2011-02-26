package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.IDbObject;
import domain.IDbObjectMember;


public class ObjectTableModel extends AbstractTableModel
{

	private IDbObject[] data;

	public ObjectTableModel( IDbObject[] data )
	{
		this.data = data;
	}

	@Override
	public int getColumnCount()
	{
		if ( data.length == 0 )
			return 0;
		else
			return data[0].getMembers().size();
	}

	@Override
	public int getRowCount()
	{
		return data.length;
	}

	@Override
	public String getColumnName( int columnIndex )
	{
		List<IDbObjectMember> members = data[0].getMembers();
		return members.get( columnIndex ).getName();
	}

	@Override
	public Class<?> getColumnClass( int columnIndex )
	{
		if ( getValueAt( 0, columnIndex ).getClass() != Boolean.class )
			return getValueAt( 0, columnIndex ).getClass();
		else
			return String.class;
	}

	@Override
	public Object getValueAt( int rowIndex, int columnIndex )
	{
		List<IDbObjectMember> members = data[rowIndex].getMembers();

		if ( members.get( columnIndex ) == null )
			return null;
		else
			return members.get( columnIndex );
	}

}
