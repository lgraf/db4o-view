package domain;

import java.util.Date;


public class DbSimpleMember implements IDbObjectMember
{

	private long objectId = 0; // internal db4o objectId -> simpleTypes=0
	private String name;
	private String dataType;
	private Object value;

	public DbSimpleMember( String name, String dataType )
	{
		this.name = name;
		this.dataType = dataType;
	}

	public DbSimpleMember( String name, String dataType, Object value, long dbObjectId )
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
			return this.value.toString();
	}

	/**
	 * Compare Numbers and boolean as the real type Compare the rest as Strings
	 */
	@Override
	public int compareTo( IDbObjectMember o )
	{
		if ( getDataType() == "java.lang.Short" || getDataType() == "short" )
		{
			Short a = (Short) this.getValue();
			Short b = (Short) o.getValue();
			return a.compareTo( b );
		}
		else if ( getDataType() == "java.lang.Integer" || getDataType() == "int" )
		{
			Integer a = (Integer) this.getValue();
			Integer b = (Integer) o.getValue();
			return a.compareTo( b );
		}
		else if ( getDataType() == "java.lang.Long" || getDataType() == "long" )
		{
			Long a = (Long) this.getValue();
			Long b = (Long) o.getValue();
			return a.compareTo( b );
		}
		else if ( getDataType() == "java.lang.Float" || getDataType() == "float" )
		{
			Float a = (Float) this.getValue();
			Float b = (Float) o.getValue();
			return a.compareTo( b );
		}
		else if ( getDataType() == "java.lang.Double" || getDataType() == "double" )
		{
			Double a = (Double) this.getValue();
			Double b = (Double) o.getValue();
			return a.compareTo( b );
		}
		else if ( getDataType() == "java.util.Date" )
		{
			Date a = (Date) this.getValue();
			Date b = (Date) o.getValue();
			return a.compareTo( b );
		}
		else if ( getDataType() == "java.lang.Boolean" )
		{
			Boolean a = (Boolean) this.getValue();
			Boolean b = (Boolean) o.getValue();
			return a.compareTo( b );
		}
		else
		{
			String a = (String) this.getValue();
			String b = (String) o.getValue();
			return a.compareTo( b );
		}
	}
}
