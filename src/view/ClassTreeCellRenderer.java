package view;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import domain.IDbClass;
import domain.IDbObjectMember;


public class ClassTreeCellRenderer extends DefaultTreeCellRenderer
{

	Icon dbIcon;
	Icon classIcon;
	Icon superClassIcon;
	Icon memberSimpleIcon;
	Icon memberObjectIcon;

	public ClassTreeCellRenderer( Icon dbIcon, Icon classIcon, Icon superClassIcon,
			Icon memberSimpleIcon, Icon memberObjectIcon )
	{
		this.dbIcon = dbIcon;
		this.classIcon = classIcon;
		this.superClassIcon = superClassIcon;
		this.memberSimpleIcon = memberSimpleIcon;
		this.memberObjectIcon = memberObjectIcon;
	}

	@Override
	public Component getTreeCellRendererComponent( JTree tree, Object value, boolean sel,
			boolean expanded, boolean leaf, int row, boolean hasFocus )
	{
		super.getTreeCellRendererComponent( tree, value, sel, expanded, leaf, row, hasFocus );

		// root element
		if ( value instanceof String )
		{
			String db4oFile = (String) value;
			String[] splits = db4oFile.split( "\\\\" );

			setText( splits[splits.length - 1] );
			setIcon( dbIcon );
			setToolTipText( db4oFile );
		}
		// classes
		if ( value instanceof IDbClass )
		{
			IDbClass clazz = (IDbClass) value;
			String[] splits = clazz.getClassName().split( "\\." );

			setText( splits[splits.length - 1] );
			setToolTipText( clazz.getClassName() );

			if ( clazz.isSuperClass() )
				setIcon( superClassIcon );
			else
				setIcon( classIcon );
		}
		// members
		if ( value instanceof IDbObjectMember )
		{
			IDbObjectMember memberObject = (IDbObjectMember) value;
			String memberName = memberObject.getName();
			String storedType = memberObject.getDataType();

			// check if object ore simple datatype
			if ( storedType.contains( "." ) )
			{
				String[] splits = storedType.split( "\\." );

				setText( splits[splits.length - 1] + " - " + memberName );
				setIcon( memberObjectIcon );
				setToolTipText( storedType );
			}
			else
			{
				setText( storedType + " - " + memberName );
				setIcon( memberSimpleIcon );
				setToolTipText( storedType );
			}
		}
		return this;
	}
}
