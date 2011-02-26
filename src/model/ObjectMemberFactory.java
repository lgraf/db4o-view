package model;

import com.db4o.reflect.generic.GenericObject;

import domain.DbArrayMember;
import domain.DbCollectionMember;
import domain.DbMapMember;
import domain.DbObjectMember;
import domain.DbSimpleMember;
import domain.IDbObjectMember;


public class ObjectMemberFactory
{

	public static IDbObjectMember createMember( String name, String dataType, Object obj,
			long objectId )
	{

		IDbObjectMember member = null;

		if ( obj instanceof GenericObject )
		{
			member = new DbObjectMember( name, dataType, obj, objectId );
		}
		else if ( obj instanceof java.util.Collection )
		{
			member = new DbCollectionMember( name, dataType, obj, objectId );
		}
		else if ( obj instanceof java.util.Map )
		{
			member = new DbMapMember( name, dataType, obj, objectId );
		}
		// we cast all simple array types to their wrapperType
		// then we can handle all arrays as Object[]
		else if ( obj instanceof byte[] )
		{
			byte[] castedArray = (byte[]) obj;
			Byte[] wrapperArray = new Byte[castedArray.length];
			for ( int i = 0; i < castedArray.length; i++ )
			{
				wrapperArray[i] = (Byte) castedArray[i];
			}
			member = new DbArrayMember( name, dataType, wrapperArray, objectId );
		}
		else if ( obj instanceof short[] )
		{
			short[] castedArray = (short[]) obj;
			Short[] wrapperArray = new Short[castedArray.length];
			for ( int i = 0; i < castedArray.length; i++ )
			{
				wrapperArray[i] = (Short) castedArray[i];
			}
			member = new DbArrayMember( name, dataType, wrapperArray, objectId );
		}
		else if ( obj instanceof int[] )
		{
			int[] castedArray = (int[]) obj;
			Integer[] wrapperArray = new Integer[castedArray.length];
			for ( int i = 0; i < castedArray.length; i++ )
			{
				wrapperArray[i] = (Integer) castedArray[i];
			}
			member = new DbArrayMember( name, dataType, wrapperArray, objectId );
		}
		else if ( obj instanceof long[] )
		{
			long[] castedArray = (long[]) obj;
			Long[] wrapperArray = new Long[castedArray.length];
			for ( int i = 0; i < castedArray.length; i++ )
			{
				wrapperArray[i] = (Long) castedArray[i];
			}
			member = new DbArrayMember( name, dataType, wrapperArray, objectId );
		}
		else if ( obj instanceof float[] )
		{
			float[] castedArray = (float[]) obj;
			Float[] wrapperArray = new Float[castedArray.length];
			for ( int i = 0; i < castedArray.length; i++ )
			{
				wrapperArray[i] = (Float) castedArray[i];
			}
			member = new DbArrayMember( name, dataType, wrapperArray, objectId );
		}
		else if ( obj instanceof double[] )
		{
			double[] castedArray = (double[]) obj;
			Double[] wrapperArray = new Double[castedArray.length];
			for ( int i = 0; i < castedArray.length; i++ )
			{
				wrapperArray[i] = (Double) castedArray[i];
			}
			member = new DbArrayMember( name, dataType, wrapperArray, objectId );
		}
		else if ( obj instanceof char[] )
		{
			char[] castedArray = (char[]) obj;
			Character[] wrapperArray = new Character[castedArray.length];
			for ( int i = 0; i < castedArray.length; i++ )
			{
				wrapperArray[i] = (Character) castedArray[i];
			}
			member = new DbArrayMember( name, dataType, wrapperArray, objectId );
		}
		else if ( obj instanceof boolean[] )
		{
			boolean[] castedArray = (boolean[]) obj;
			Boolean[] wrapperArray = new Boolean[castedArray.length];
			for ( int i = 0; i < castedArray.length; i++ )
			{
				wrapperArray[i] = (Boolean) castedArray[i];
			}
			member = new DbArrayMember( name, dataType, wrapperArray, objectId );
		}
		else if ( obj instanceof Object[] )
		{
			Object[] castedobj = (Object[]) obj;
			member = new DbArrayMember( name, dataType, castedobj, objectId );
		}
		// Simple Type/Member
		else
		{
			member = new DbSimpleMember( name, dataType, obj, objectId );
		}
		return member;
	}
}
