package client;

import model.Db4oDatabase;
import controler.Controler;


public class Client
{

	public static void main( String[] args )
	{
		Db4oDatabase model = new Db4oDatabase();
		Controler controler = new Controler( model );
	}
}
