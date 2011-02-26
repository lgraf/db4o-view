package view;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controler.IControler;
import domain.DbSimpleMember;
import domain.IDbObjectMember;


public class ObjectTableListSelectionListener implements ListSelectionListener
{

	private JTable dataTable;
	private IControler controler;

	public ObjectTableListSelectionListener( JTable dataTable, IControler controler )
	{
		this.dataTable = dataTable;
		this.controler = controler;
	}

	@Override
	public void valueChanged( ListSelectionEvent e )
	{
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();

		if ( !e.getValueIsAdjusting() && !lsm.isSelectionEmpty() )
		{
			// get object from clicked cell
			IDbObjectMember member = (IDbObjectMember) dataTable.getValueAt( dataTable
					.getSelectionModel().getLeadSelectionIndex(), dataTable.getColumnModel()
					.getSelectionModel().getLeadSelectionIndex() );

			// open window only for extendedObjects
			if ( !(member instanceof DbSimpleMember) )
			{
				controler.loadExtendedMember( member );
			}
			lsm.clearSelection();
		}
	}

	public JTable getDataTable()
	{
		return dataTable;
	}

	public void setDataTable( JTable dataTable )
	{
		this.dataTable = dataTable;
	}

}
