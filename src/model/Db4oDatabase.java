package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import view.IClassTreeObserver;
import view.IMetaInfoObserver;
import view.IObjectTableObserver;
import view.IPopUpWindowObserver;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.StoredClass;
import com.db4o.ext.StoredField;
import com.db4o.query.Query;
import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.generic.GenericObject;
import com.db4o.reflect.generic.GenericReflector;

import domain.DbArrayMember;
import domain.DbClass;
import domain.DbCollectionMember;
import domain.DbMapMember;
import domain.DbObject;
import domain.DbObjectMember;
import domain.IDbClass;
import domain.IDbObject;
import domain.IDbObjectMember;


public class Db4oDatabase implements IModel, IObserveable
{

	private String currentDb4oFile;
	private ObjectContainer db;

	private SortedMap<String, IDbClass> loadedClasses;

	private ReflectClass currentClass;
	private String currentClassName;
	private IDbObject[] currentPopupData;

	private String errorText;

	private List<IClassTreeObserver> classTreeObserver;
	private List<IObjectTableObserver> objectTableObserver;
	private List<IMetaInfoObserver> metaInfoObserver;
	private List<IPopUpWindowObserver> popUpWindowObserver;

	public Db4oDatabase()
	{
		classTreeObserver = new ArrayList<IClassTreeObserver>();
		objectTableObserver = new ArrayList<IObjectTableObserver>();
		metaInfoObserver = new ArrayList<IMetaInfoObserver>();
		popUpWindowObserver = new ArrayList<IPopUpWindowObserver>();
	}

	/**
	 * Open a Db4oFile AND load all classes.
	 * 
	 * @Input: String Filename
	 * @Output: none[void] -> call method public void loadStoredClasses(boolean
	 *          allClasses)
	 */
	@Override
	public void loadDbFile( String filePath ) throws DatabaseFileLockedException
	{
		if ( db != null )
			this.closeDbFile();

		this.currentDb4oFile = filePath;
		try
		{
			Db4o.configure().activationDepth( 0 );
			db = Db4o.openFile( currentDb4oFile );
			loadStoredClasses( false );
		}
		catch ( DatabaseFileLockedException e )
		{
			throw new DatabaseFileLockedException( e.getMessage() );
		}
	}

	/**
	 * Creates the main classObjects for each user class in the database [stored
	 * in TreeMap loadedClasses] Each holds information about the class like
	 * Superclass etc. Additional the fields are stored in a List that contains
	 * IDbObjectMember Objects [empty Members only Name and DataType]
	 * 
	 * @Input: boolean allClasses (DEPRECATED)
	 * @Output: none[void] -> create TreeMap loadedClasses [Field of this class]
	 */
	@Override
	public void loadStoredClasses( boolean allClasses )
	{
		try
		{
			StoredClass[] storedClasses = db.ext().storedClasses();
			loadedClasses = new TreeMap<String, IDbClass>();

			// create "static" object members for each class [in a DbClass] -->
			// we need this for the tree view
			for ( int i = 0; i < storedClasses.length; i++ )
			{

				String superClassName = "java.lang.Object";

				// use only user classes
				if ( !storedClasses[i].getName().startsWith( "com.db4o" )
						&& !storedClasses[i].getName().startsWith( "Db4o" )
						&& !storedClasses[i].getName().startsWith( "java." ) )
				{

					List<IDbObjectMember> classMember = new ArrayList<IDbObjectMember>();

					// get fields from superclass and set superClassName
					if ( storedClasses[i].getParentStoredClass() != null )
					{
						superClassName = storedClasses[i].getParentStoredClass().getName();
						_addSuperClassMember( storedClasses[i], classMember );
					}

					// get fields from the main class
					StoredField[] members = storedClasses[i].getStoredFields();
					for ( int j = 0; j < members.length; j++ )
					{
						if ( !members[j].getStoredType().getName().startsWith( "com.db4o" ) )
						{
							IDbObjectMember member = ObjectMemberFactory.createMember(
									members[j].getName(), members[j].getStoredType().getName(),
									null, 0 );
							classMember.add( member );
						}
					}

					// all DbClass Objects are stored loadedClasses [SortedMap
					// key=className]
					loadedClasses.put( storedClasses[i].getName(),
							new DbClass( storedClasses[i].getName(), superClassName, classMember ) );
				}
			}

			// add additional class information
			Iterator<IDbClass> classIt = loadedClasses.values().iterator();
			while ( classIt.hasNext() )
			{
				IDbClass dbClass = classIt.next();
				if ( dbClass.getSuperClassName() != "java.lang.Object" )
				{
					loadedClasses.get( dbClass.getSuperClassName() ).setIsSuperClass( true );
					loadedClasses.get( dbClass.getSuperClassName() ).addChildClass(
							dbClass.getClassName() );
				}
			}
			notifyClassTreeObserver();
		}
		catch ( DatabaseFileLockedException e )
		{
			e.printStackTrace();
		}

	}

	/**
	 * Helper Function to get _all_ classMember if a Superclass exists
	 * 
	 * @Input: StoredClass sClass, List<IDbObjectMember> classMember
	 * @Output: none[void] -> modifies classMember [!"Call by Reference"!]
	 */
	private void _addSuperClassMember( StoredClass sClass, List<IDbObjectMember> classMember )
	{
		StoredClass parentClass = sClass.getParentStoredClass();
		StoredField members[] = sClass.getParentStoredClass().getStoredFields();

		for ( int j = 0; j < members.length; j++ )
		{
			if ( !members[j].getStoredType().getName().startsWith( "com.db4o" ) )
			{
				IDbObjectMember member = ObjectMemberFactory.createMember( members[j].getName(),
						members[j].getStoredType().getName(), null, 0 );
				classMember.add( member );
			}
		}
		// recursive call
		if ( parentClass.getParentStoredClass() != null )
			_addSuperClassMember( parentClass, classMember );
	}

	/**
	 * Here we load all instances for one class (if we click in the tree a
	 * class) One Instance get stored as DbObjectMember with IDbObjectMember for
	 * each member after each member append to DbObject[] to get stored in his
	 * DbClass Object We load instances only one Time after we can get the
	 * members direct from the DbClass Object
	 * 
	 * @Input: String className
	 * @Output: none[void] -> modifies the DbClass Object -> set loadedInstances
	 *          + QueryTime
	 **/
	@Override
	public void loadInstancesByClass( String className )
	{
		long startTime = System.currentTimeMillis();

		if ( loadedClasses.containsKey( className ) )
		{

			// set current className
			currentClassName = className;

			// only load once
			if ( loadedClasses.get( className ).getInstances() == null )
			{

				// create generic reflector
				GenericReflector reflector = new GenericReflector( null, db.ext().reflector() );
				// get reflectClass [we need this to query the DB for a class]
				currentClass = reflector.forName( loadedClasses.get( currentClassName )
						.getClassName() );

				// query the database for the main class
				Query instancesQuery = db.query();
				instancesQuery.constrain( currentClass );
				ObjectSet<GenericObject> instancesResult = (ObjectSet<GenericObject>) instancesQuery
						.execute();

				// create array to store instances in DbClass Object
				IDbObject[] instances = new IDbObject[instancesResult.size()];

				int arrayIndex = 0;
				if ( instancesResult.size() > 0 )
				{

					List<IDbObjectMember> staticClassMember = loadedClasses.get( currentClassName )
							.getClassMember();
					while ( instancesResult.hasNext() )
					{

						GenericObject obj = (GenericObject) instancesResult.next();

						// activate the object
						if ( !db.ext().isActive( obj ) )
							db.activate( obj, 1 );

						List<IDbObjectMember> classMember = new ArrayList<IDbObjectMember>();
						for ( int i = 0; i < loadedClasses.get( currentClassName ).getClassMember()
								.size(); i++ )
						{

							if ( (obj.get( i ) instanceof Object)
									&& !db.ext().isActive( obj.get( i ) ) )
								db.activate( obj.get( i ), 1 );

							IDbObjectMember member = ObjectMemberFactory.createMember(
									staticClassMember.get( i ).getName(), staticClassMember.get( i )
											.getDataType(), obj.get( i ),
									db.ext().getID( obj.get( i ) ) );
							classMember.add( member );
						}

						// create DbObject with the members and put in the array
						IDbObject object = new DbObject( db.ext().getID( obj ), classMember );
						instances[arrayIndex] = object;
						arrayIndex++;

					}

					// set the DbObject array as members for currentClass
					loadedClasses.get( currentClassName ).setInstances( instances );

					long endTime = System.currentTimeMillis();
					long executionTime = (endTime - startTime);
					loadedClasses.get( currentClassName ).setQueryExecutionTime( executionTime );
				}
			}

		}
		notifyObjectTableObserver();
		notifyMetaInfoObserver();
	}

	/**
	 * Convert an IDbObjectMember.value to our data structure (DbObjects[DbObject->DbMember]).
	 * So we can show Objects/Collection/Maps/Arrays with the same TableModel.
	 * 
	 * @Input: IDbObjectMember memberObject
	 * @Output: none[void] -> modifies currentDialogData [Array of DbObjects]
	 **/
	@Override
	public void loadExtendedMember( IDbObjectMember memberObject )
	{

		// activate the object
		if ( (memberObject instanceof Object) && (!db.ext().isActive( memberObject )) )
			db.activate( memberObject, 1 );

		// object
		if ( memberObject instanceof DbObjectMember )
		{

			GenericObject gObject = (GenericObject) memberObject.getValue();
			IDbObject[] objectItem = new IDbObject[1];

			// cut toString --> name of the class
			String cName = gObject.toString().trim();
			cName = cName.substring( 4 );

			List<IDbObjectMember> classMember = new ArrayList<IDbObjectMember>();
			for ( int z = 0; z < loadedClasses.get( cName ).getClassMember().size(); z++ )
			{

				if ( (memberObject instanceof Object) && !db.ext().isActive( gObject.get( z ) ) )
					db.activate( gObject.get( z ), 1 );

				IDbObjectMember member = ObjectMemberFactory.createMember( loadedClasses
						.get( cName ).getClassMember().get( z ).getName(), loadedClasses
						.get( cName ).getClassMember().get( z ).getDataType(), gObject.get( z ), db
						.ext().getID( gObject.get( z ) ) );
				classMember.add( member );
			}

			IDbObject dbObject = new DbObject( db.ext().getID( gObject ), gObject.toString(),
					classMember );
			objectItem[0] = dbObject;
			currentPopupData = objectItem;
		}
		// collection
		else if ( memberObject instanceof DbCollectionMember )
		{

			Collection ls = (Collection) memberObject.getValue();
			Iterator it = ls.iterator();

			int arrayIndex = 0;
			IDbObject[] collectionItems = new IDbObject[ls.size()];

			while ( it.hasNext() )
			{
				List<IDbObjectMember> classMember = new ArrayList<IDbObjectMember>();

				Object listItem = (Object) it.next();
				// check if the items are GenericObjects -> Object/Map/Etc
				if ( listItem instanceof GenericObject )
				{
					GenericObject gObject = (GenericObject) listItem;
					// cut toString --> name of the class
					String cName = gObject.toString().trim();
					cName = cName.substring( 4 );

					for ( int z = 0; z < loadedClasses.get( cName ).getClassMember().size(); z++ )
					{

						if ( (gObject.get( z ) instanceof Object)
								&& !db.ext().isActive( gObject.get( z ) ) )
							db.activate( gObject.get( z ), 1 );

						IDbObjectMember member = ObjectMemberFactory.createMember( loadedClasses
								.get( cName ).getClassMember().get( z ).getName(), loadedClasses
								.get( cName ).getClassMember().get( z ).getDataType(),
								gObject.get( z ), db.ext().getID( gObject.get( z ) ) );
						classMember.add( member );
					}

					IDbObject dbObject = new DbObject( db.ext().getID( gObject ), ls.getClass()
							.getName(), classMember );
					collectionItems[arrayIndex] = dbObject;
				}
				else
				{
					IDbObjectMember member = ObjectMemberFactory.createMember( "Value",
							memberObject.getValue().getClass().getName(), listItem,
							memberObject.getObjectId() );
					classMember.add( member );

					IDbObject dbObject = new DbObject( memberObject.getObjectId(), ls.getClass()
							.getName(), classMember );
					collectionItems[arrayIndex] = dbObject;
				}
				arrayIndex++;
			}
			currentPopupData = collectionItems;
		}
		// map
		else if ( memberObject instanceof DbMapMember )
		{

			Map map = (Map) memberObject.getValue();
			Iterator it = map.values().iterator();

			int arrayIndex = 0;
			IDbObject[] mapItems = new DbObject[map.values().size()];
			while ( it.hasNext() )
			{
				List<IDbObjectMember> classMember = new ArrayList<IDbObjectMember>();

				Object mapItem = (Object) it.next();

				if ( mapItem instanceof GenericObject )
				{
					GenericObject gObject = (GenericObject) mapItem;
					// cut toString --> name of the class
					String cName = gObject.toString().trim();
					cName = cName.substring( 4 );

					for ( int z = 0; z < loadedClasses.get( cName ).getClassMember().size(); z++ )
					{

						if ( (gObject.get( z ) instanceof Object)
								&& !db.ext().isActive( gObject.get( z ) ) )
							db.activate( gObject.get( z ), 1 );

						IDbObjectMember member = ObjectMemberFactory.createMember( loadedClasses
								.get( cName ).getClassMember().get( z ).getName(), loadedClasses
								.get( cName ).getClassMember().get( z ).getDataType(),
								gObject.get( z ), db.ext().getID( gObject.get( z ) ) );
						classMember.add( member );
					}

					IDbObject dbObject = new DbObject( db.ext().getID( gObject ), map.getClass()
							.getName(), classMember );
					mapItems[arrayIndex] = dbObject;
				}
				else
				{
					IDbObjectMember member = ObjectMemberFactory.createMember( "Value",
							memberObject.getValue().getClass().getName(), mapItem,
							memberObject.getObjectId() );
					classMember.add( member );

					IDbObject dbObject = new DbObject( memberObject.getObjectId(), map.getClass()
							.getName(), classMember );
					mapItems[arrayIndex] = dbObject;
				}
				arrayIndex++;
			}
			currentPopupData = mapItems;
		}
		// array
		else if ( memberObject instanceof DbArrayMember )
		{
			// in MemberFactory all simple arrays get wrapped to arrays of
			// Objects example: int -> Integer
			// so all DbArrayMember.value can cast to Object[]
			Object[] array = (Object[]) memberObject.getValue();

			IDbObject[] arrayItems = new DbObject[array.length];
			for ( int i = 0; i < array.length; i++ )
			{
				List<IDbObjectMember> classMember = new ArrayList<IDbObjectMember>();

				Object arrayItem = array[i];

				IDbObjectMember member = ObjectMemberFactory.createMember( "Value",
						memberObject.getDataType(), arrayItem, memberObject.getObjectId() );
				classMember.add( member );

				IDbObject dbObject = new DbObject( memberObject.getObjectId(),
						memberObject.getDataType(), classMember );
				arrayItems[i] = dbObject;

			}
			currentPopupData = arrayItems;
		}
		notifyPopUpWindowObserver();
	}

	/**
	 * Close DB and set values to null
	 * 
	 * @Input: none
	 * @Output: none[void]
	 */
	@Override
	public void closeDbFile()
	{
		if ( this.db != null )
			db.close();

		// set all DB depending values to null
		currentDb4oFile = "";
		db = null;

		currentClass = null;
		currentClassName = null;
		currentPopupData = null;

		errorText = null;

		loadedClasses = null;
	}

	/**
	 * Getters & Setters
	 */
	@Override
	public void setDbFile( String filePath )
	{
		currentDb4oFile = filePath;
	}

	@Override
	public String getDbFile()
	{
		return currentDb4oFile;
	}

	@Override
	public long getDbSize()
	{
		File file = new File( currentDb4oFile );
		return file.length();
	}

	@Override
	public Map<String, IDbClass> getLoadedClasses()
	{
		return loadedClasses;
	}

	@Override
	public IDbClass getCurrentLoadedClass()
	{
		return loadedClasses.get( currentClassName );
	}

	@Override
	public IDbObject[] getCurrentPopupData()
	{
		return currentPopupData;
	}

	@Override
	public String getErrorText()
	{
		return errorText;
	}

	/**
	 * Observable functions do all we need to observer the model
	 */
	public void addObserver( IClassTreeObserver obs )
	{
		classTreeObserver.add( obs );
	}

	public void addObserver( IObjectTableObserver obs )
	{
		objectTableObserver.add( obs );
	}

	public void addObserver( IMetaInfoObserver obs )
	{
		metaInfoObserver.add( obs );
	}

	public void addObserver( IPopUpWindowObserver obs )
	{
		popUpWindowObserver.add( obs );
	}

	public void removeObserver( IClassTreeObserver obs )
	{
		int index = classTreeObserver.indexOf( obs );
		classTreeObserver.remove( index );
	}

	public void removeObserver( IObjectTableObserver obs )
	{
		int index = objectTableObserver.indexOf( obs );
		objectTableObserver.remove( index );
	}

	public void removeObserver( IMetaInfoObserver obs )
	{
		int index = metaInfoObserver.indexOf( obs );
		metaInfoObserver.remove( index );
	}

	public void removeObserver( IPopUpWindowObserver obs )
	{
		int index = popUpWindowObserver.indexOf( obs );
		popUpWindowObserver.remove( index );
	}

	public void notifyClassTreeObserver()
	{
		for ( int i = 0; i < classTreeObserver.size(); i++ )
		{
			IClassTreeObserver obs = (IClassTreeObserver) classTreeObserver.get( i );
			obs.updateClassTree();
		}
	}

	public void notifyObjectTableObserver()
	{
		for ( int i = 0; i < objectTableObserver.size(); i++ )
		{
			IObjectTableObserver obs = (IObjectTableObserver) objectTableObserver.get( i );
			obs.updateObjectTable();
		}
	}

	public void notifyMetaInfoObserver()
	{
		for ( int i = 0; i < metaInfoObserver.size(); i++ )
		{
			IMetaInfoObserver obs = (IMetaInfoObserver) metaInfoObserver.get( i );
			obs.updateMetaInfo();
		}
	}

	public void notifyPopUpWindowObserver()
	{
		for ( int i = 0; i < popUpWindowObserver.size(); i++ )
		{
			IPopUpWindowObserver obs = (IPopUpWindowObserver) popUpWindowObserver.get( i );
			obs.updatePopUpWindow();
		}
	}
}
