package domain;

import java.util.List;


public interface IDbObject
{

	public long getObjectId();

	void setObjectId( long id );

	String getDataType();

	void setDataType( String dataType );

	List<IDbObjectMember> getMembers();

	void setMembers( List<IDbObjectMember> members );
}
