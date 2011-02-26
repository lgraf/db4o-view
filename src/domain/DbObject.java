package domain;

import java.util.List;


public class DbObject implements IDbObject
{

	private long objectId; // internal db4o objectId
	private String dataType;
	private List<IDbObjectMember> members;

	public DbObject( long id )
	{
		this.objectId = id;
		this.members = null;
	}

	public DbObject( long id, List<IDbObjectMember> members )
	{
		this.objectId = id;
		this.members = members;
	}

	public DbObject( long id, String dataType, List<IDbObjectMember> members )
	{
		this.objectId = id;
		this.dataType = dataType;
		this.members = members;
	}

	@Override
	public long getObjectId()
	{
		return objectId;
	}

	@Override
	public void setObjectId( long id )
	{
		this.objectId = id;
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
	public List<IDbObjectMember> getMembers()
	{
		return members;
	}

	@Override
	public void setMembers( List<IDbObjectMember> members )
	{
		this.members = members;
	}

}
