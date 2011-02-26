package domain;

import java.util.ArrayList;
import java.util.List;


public class DbClass implements IDbClass
{

	private String className;
	private String superClassName;
	private boolean isSuperClass;
	private List<String> childClasses;
	private long queryExecutionTime;

	private List<IDbObjectMember> classMember;
	private IDbObject[] instances;

	public DbClass( String name, String superClassName, List<IDbObjectMember> classMember )
	{
		this.className = name;
		this.superClassName = superClassName;
		this.classMember = classMember;
	}

	@Override
	public String getClassName()
	{
		return className;
	}

	@Override
	public void setClassName( String className )
	{
		this.className = className;
	}

	@Override
	public String getSuperClassName()
	{
		return superClassName;
	}

	@Override
	public void setSuperClassName( String superClassName )
	{
		this.superClassName = superClassName;
	}

	@Override
	public boolean getIsSuperClass()
	{
		return isSuperClass;
	}

	@Override
	public void setIsSuperClass( boolean isSuperClass )
	{
		this.isSuperClass = isSuperClass;
	}

	@Override
	public boolean isSuperClass()
	{
		if ( getIsSuperClass() )
			return true;
		else
			return false;
	}

	@Override
	public List<String> getChildClasses()
	{
		return childClasses;
	}

	@Override
	public String getChildClassesAsString()
	{
		if ( this.childClasses == null )
			return "";
		else
			return this.childClasses.toString();
	}

	@Override
	public void addChildClass( String childClass )
	{
		if ( this.childClasses == null )
			this.childClasses = new ArrayList<String>();
		this.childClasses.add( childClass );
	}

	@Override
	public long getQueryExecutionTime()
	{
		return queryExecutionTime;
	}

	@Override
	public void setQueryExecutionTime( long queryExecutionTime )
	{
		this.queryExecutionTime = queryExecutionTime;
	}

	@Override
	public int getInstancesCount()
	{
		return instances.length;
	}

	@Override
	public List<IDbObjectMember> getClassMember()
	{
		return classMember;
	}

	@Override
	public void setClassMember( List<IDbObjectMember> classMember )
	{
		this.classMember = classMember;
	}

	@Override
	public IDbObject[] getInstances()
	{
		return instances;
	}

	@Override
	public void setInstances( IDbObject[] instances )
	{
		this.instances = instances;
	}
}
