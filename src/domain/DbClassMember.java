package domain;

public class DbClassMember implements IDbObjectMember
{

	private String name;
	private String dataType;

	public DbClassMember( String name, String dataType )
	{
		this.name = name;
		this.dataType = dataType;
	}

	@Override
	public String getDataType()
	{
		return dataType;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public long getObjectId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValueFormated()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataType( String dataType )
	{
		this.dataType = dataType;
	}

	@Override
	public void setName( String name )
	{
		this.name = name;
	}

	@Override
	public void setObjectId( long objectId )
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void setValue( String value )
	{
		// TODO Auto-generated method stub
	}

	@Override
	public int compareTo( IDbObjectMember arg0 )
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
