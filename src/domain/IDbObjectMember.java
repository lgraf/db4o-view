package domain;

public interface IDbObjectMember extends Comparable<IDbObjectMember>
{

	long getObjectId();// return 0 for simple types

	void setObjectId( long objectId );

	void setName( String name );

	String getName();

	void setDataType( String dataType );

	String getDataType();

	Object getValue();

	void setValue( String value );

	String getValueFormated();
}
