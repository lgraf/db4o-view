package domain;

import java.util.Map;


public class DbMapMember implements IDbObjectMember
{

	private long objectId; // internal db4o objectId
	private String name;
	private String dataType;
	private Object value;

	public DbMapMember( String name, String dataType )
	{
		this.name = name;
		this.dataType = dataType;
	}

	public DbMapMember( String name, String dataType, Object value, long dbObjectId )
	{
		this.name = name;
		this.dataType = dataType;
		this.value = value;
		this.objectId = dbObjectId;
	}

	@Override
	public long getObjectId()
	{
		return objectId;
	}

	@Override
	public void setObjectId( long objectId )
	{
		this.objectId = objectId;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName( String name )
	{
		this.name = name;
	}

	@Override
	public String getDataType()
	{
		return dataType;
	}

	@Override
	public void setDataType( String dataType )
	{
		this.dataType = dataType;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public void setValue( String value )
	{
		this.value = value;
	}

	@Override
	public String getValueFormated()
	{
		if ( this.value == null )
			return "null";
		else
		{
			Map map = (Map) this.value;
			return "Map: " + map.size() + "items";
		}
	}

	@Override
	public int compareTo( IDbObjectMember o )
	{
		return this.getValueFormated().compareTo( o.getValueFormated() );
	}
}
