package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;


public class SplashScreen extends JWindow implements Runnable
{

	private JFrame owner;
	private JLabel splashLabel;
	private Icon splashImage;
	private long ms;

	public SplashScreen( Icon splashImage, JFrame owner, long ms )
	{

		super( owner );

		this.splashImage = splashImage;
		this.owner = owner;
		this.ms = ms;

		splashLabel = new JLabel();
		splashLabel.setIcon( splashImage );

		setPreferredSize( new Dimension( splashImage.getIconWidth(), splashImage.getIconHeight() ) );
		getContentPane().setLayout( new BorderLayout() );
		getContentPane().add( splashLabel, BorderLayout.CENTER );

	}

	@Override
	public void run()
	{
		pack();
		setLocationRelativeTo( null );
		setVisible( true );

		try
		{
			Thread.sleep( ms );
		}
		catch ( InterruptedException e )
		{
		}

		dispose();
	}

	public JFrame getOwner()
	{
		return owner;
	}

	public void setOwner( JFrame owner )
	{
		this.owner = owner;
	}

	public JLabel getSplashLabel()
	{
		return splashLabel;
	}

	public void setSplashLabel( JLabel splashLabel )
	{
		this.splashLabel = splashLabel;
	}

	public Icon getSplashImage()
	{
		return splashImage;
	}

	public void setSplashImage( Icon splashImage )
	{
		this.splashImage = splashImage;
	}

}
