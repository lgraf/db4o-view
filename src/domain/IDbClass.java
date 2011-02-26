package domain;

import java.util.List;


public interface IDbClass
{

	String getClassName();

	void setClassName( String className );

	String getSuperClassName();

	boolean getIsSuperClass();

	void setSuperClassName( String superClassName );

	void setIsSuperClass( boolean isSuperClass );

	public boolean isSuperClass();

	List<String> getChildClasses();

	String getChildClassesAsString();

	void addChildClass( String childClass );

	long getQueryExecutionTime();

	void setQueryExecutionTime( long queryExecutionTime );

	List<IDbObjectMember> getClassMember();

	void setClassMember( List<IDbObjectMember> classMember );

	IDbObject[] getInstances();

	void setInstances( IDbObject[] instances );

	int getInstancesCount();
}
