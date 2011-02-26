package view;

import java.util.Map;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import domain.DbClass;
import domain.IDbClass;


public class ClassTreeModel implements TreeModel
{

	private String dbFileName;
	private Map<String, IDbClass> loadedClasses;

	public ClassTreeModel( String dbFileName )
	{
		this.dbFileName = dbFileName;
	}

	public ClassTreeModel( String dbFileName, Map<String, IDbClass> loadedClasses )
	{
		this.dbFileName = dbFileName;
		this.loadedClasses = loadedClasses;
	}

	@Override
	public Object getRoot()
	{
		return dbFileName;
	}

	@Override
	public Object getChild( Object node, int nodeIndex )
	{

		if ( node.equals( getRoot() ) )
		{// root
			IDbClass[] clazzArray = new DbClass[loadedClasses.size()];
			clazzArray = loadedClasses.values().toArray( clazzArray );
			return clazzArray[nodeIndex];
		}
		else if ( node instanceof IDbClass )
		{
			IDbClass dbCLass = (IDbClass) node;
			IDbClass clazz = loadedClasses.get( dbCLass.getClassName() );
			return clazz.getClassMember().get( nodeIndex );
		}
		else
		{ // DbObjectMember
			return 0;
		}
	}

	@Override
	public int getChildCount( Object node )
	{

		if ( node.equals( getRoot() ) && loadedClasses != null )
		{// root
			return loadedClasses.size();
		}
		else if ( node instanceof IDbClass )
		{
			IDbClass dbCLass = (IDbClass) node;
			return loadedClasses.get( dbCLass.getClassName() ).getClassMember().size();
		}
		else
		{// DbObjectMember
			return 0;
		}
	}

	@Override
	public int getIndexOfChild( Object parent, Object child )
	{
		int max = getChildCount( parent );
		for ( int i = 0; i < max; i++ )
		{
			if ( getChild( parent, i ).equals( child ) )
				return i;
		}
		return -1;
	}

	@Override
	public boolean isLeaf( Object node )
	{
		if ( getChildCount( node ) > 0 )
			return false;
		else
			return true;
	}

	@Override
	public void addTreeModelListener( TreeModelListener arg0 )
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void removeTreeModelListener( TreeModelListener arg0 )
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void valueForPathChanged( TreePath arg0, Object arg1 )
	{
		// TODO Auto-generated method stub

	}

}
