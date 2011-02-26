package model;

import java.util.Map;

import domain.IDbClass;
import domain.IDbObject;
import domain.IDbObjectMember;


//represents a database
public interface IModel
{

	void loadDbFile( String filePath );

	void closeDbFile();

	void loadStoredClasses( boolean allClasses );

	Map<String, IDbClass> getLoadedClasses();

	void loadInstancesByClass( String className );

	IDbClass getCurrentLoadedClass();

	void loadExtendedMember( IDbObjectMember object );

	IDbObject[] getCurrentPopupData();

	String getDbFile();

	void setDbFile( String filePath );

	long getDbSize();

	String getErrorText();
}
