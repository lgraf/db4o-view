package controler;

import domain.IDbObjectMember;


public interface IControler
{

	void openDb4oFile( String filePath );

	void closeDb4oFile();

	void loadStoredClasses( boolean allClasses );

	void loadInstancesByClass( String className );

	void loadExtendedMember( IDbObjectMember object );
}
