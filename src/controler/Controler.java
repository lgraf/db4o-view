package controler;

import model.Db4oDatabase;
import model.IModel;
import view.View;

import com.db4o.ext.DatabaseFileLockedException;

import domain.IDbObjectMember;


public class Controler implements IControler
{

	private IModel model;
	private View view;

	public Controler( Db4oDatabase model )
	{
		this.model = model;
		this.view = new View( model, this );
	}

	@Override
	public void openDb4oFile( String filePath )
	{
		try
		{
			model.loadDbFile( filePath );
			view.disableOpenFileItem();
			view.enableCloseFileItem();
			view.editRightTopLabel( "Use the Left Panel to select a Class ..." );
		}
		catch ( DatabaseFileLockedException e )
		{
			view.showErrorDialog( "Database already in use!" );
		}
	}

	@Override
	public void closeDb4oFile()
	{
		view.enableOpenFileItem();
		view.disableCloseFileItem();
		view.closeDatabase();
		view.setStatusConnected( false );
		model.closeDbFile();
	}

	@Override
	public void loadStoredClasses( boolean allClasses )
	{
		model.loadStoredClasses( allClasses );
	}

	@Override
	public void loadInstancesByClass( String className )
	{
		view.setObjectTable();
		model.loadInstancesByClass( className );
	}

	@Override
	public void loadExtendedMember( IDbObjectMember object )
	{
		model.loadExtendedMember( object );
	}
}
