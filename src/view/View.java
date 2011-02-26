package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.TreeSelectionModel;

import model.Db4oDatabase;
import model.IModel;
import controler.Controler;
import controler.IControler;
import domain.DbArrayMember;
import domain.DbCollectionMember;
import domain.DbMapMember;
import domain.DbObjectMember;
import domain.DbSimpleMember;
import domain.IDbClass;
import domain.IDbObject;


/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class View extends javax.swing.JFrame implements IClassTreeObserver, IObjectTableObserver,
		IMetaInfoObserver, IPopUpWindowObserver
{

	{
		// Set Look & Feel
		try
		{
			javax.swing.UIManager.setLookAndFeel( "com.jgoodies.looks.plastic.PlasticLookAndFeel" );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public IModel model;
	public IControler controler;

	private JButton aboutDialogClose;
	private JLabel aboutDialogImgLabel;

	private JLabel statusDBLabel;
	private JLabel statusConnectedLabel;
	private JLabel statusLabel;

	private JMenuBar menuBar;
	private JPanel rightBottomPanel;
	private JPanel rightTopPanel;
	private JSplitPane rightVerticalSplit;
	private JPanel rightPanel;
	private JPanel leftTreePanel;
	private JPanel statusPanel;
	private JSplitPane horizontalSplit;
	private JMenuItem helpAboutItem;
	private JMenu helpMenu;
	private JMenuItem fileExitItem;
	private JSeparator fileMenuSeperator;
	private JMenuItem fileCloseDBItem;
	private JMenuItem fileOpenDBItem;
	private JMenu fileMenu;

	private JScrollPane treeScrollPane;
	private JTree classTree;

	private JLabel rightTopLabel;
	private JScrollPane tableScrollPane;
	private JTable objectTable;

	private JLabel metaQueryTimeLabel;
	private JLabel metaObjectCountLabel;
	private JLabel metaClassNameLabel;
	private JLabel metaSuperClassNameLabel;

	private JDialog aboutDialog;

	private JFileChooser fileChooser;

	private AbstractAction closeAppAction;
	private AbstractAction closeDbFileAction;
	private AbstractAction openDb4oFileAction;
	private AbstractAction openAboutAction;
	private AbstractAction aboutCloseAction;

	private JDialog errorDialog;
	private JLabel errorLabel;
	private JButton errorDialogButton;
	private AbstractAction errorDialogCloseAction;

	public View( Db4oDatabase model, Controler controler )
	{
		super();
		this.model = model;
		this.controler = controler;

		// add view as 4 Observers (as each "interface type")
		model.addObserver( (IClassTreeObserver) this );
		model.addObserver( (IObjectTableObserver) this );
		model.addObserver( (IMetaInfoObserver) this );
		model.addObserver( (IPopUpWindowObserver) this );

		initGUI();
		this.setLocationRelativeTo( null );
		this.setVisible( true );
	}

	/**
	 * Build up the gui most of them generated from Jigloo
	 */
	private void initGUI()
	{
		try
		{
			setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
			this.setPreferredSize( new java.awt.Dimension( 1024, 768 ) );
			this.setTitle( " db4oView" );
			this.addWindowListener( new WindowAdapter()
			{

				public void windowClosing( WindowEvent e )
				{
					controler.closeDb4oFile();
					System.exit( 0 );
				}
			} );

			{
				horizontalSplit = new JSplitPane();
				getContentPane().add( horizontalSplit, BorderLayout.CENTER );
				{
					leftTreePanel = new JPanel();
					BorderLayout leftTreePanelLayout = new BorderLayout();
					leftTreePanel.setLayout( leftTreePanelLayout );
					horizontalSplit.add( leftTreePanel, JSplitPane.LEFT );
					leftTreePanel.setPreferredSize( new java.awt.Dimension( 200, 669 ) );
					leftTreePanel.add( getTreeScrollPane(), BorderLayout.CENTER );
				}
				{
					rightPanel = new JPanel();
					BorderLayout rightPanelLayout = new BorderLayout();
					rightPanel.setLayout( rightPanelLayout );
					horizontalSplit.add( rightPanel, JSplitPane.RIGHT );
					rightPanel.setPreferredSize( new java.awt.Dimension( 807, 669 ) );
					rightPanel.add( getRightVerticalSplit(), BorderLayout.CENTER );
				}
			}
			{
				statusPanel = new JPanel();
				GroupLayout statusPanelLayout = new GroupLayout( (JComponent) statusPanel );
				statusPanel.setLayout( statusPanelLayout );
				getContentPane().add( statusPanel, BorderLayout.SOUTH );
				statusPanel.setPreferredSize( new java.awt.Dimension( 1016, 22 ) );
				statusPanelLayout.setHorizontalGroup( statusPanelLayout
						.createSequentialGroup()
						.addGap( 6 )
						.addComponent( getStatusLabel(), GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE )
						.addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
						.addComponent( getStatusConnectedLabel(), GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE )
						.addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
						.addComponent( getStatusDBLabel(), 0, 911, Short.MAX_VALUE ) );
				statusPanelLayout.setVerticalGroup( statusPanelLayout
						.createParallelGroup( GroupLayout.Alignment.BASELINE )
						.addComponent( getStatusLabel(), GroupLayout.Alignment.BASELINE, 0, 22,
								Short.MAX_VALUE )
						.addComponent( getStatusConnectedLabel(), GroupLayout.Alignment.BASELINE,
								0, 22, Short.MAX_VALUE )
						.addComponent( getStatusDBLabel(), GroupLayout.Alignment.BASELINE, 0, 22,
								Short.MAX_VALUE ) );
			}
			{
				menuBar = new JMenuBar();
				setJMenuBar( menuBar );
				{
					fileMenu = new JMenu();
					menuBar.add( fileMenu );
					fileMenu.setText( "File" );
					{
						fileOpenDBItem = new JMenuItem();
						fileMenu.add( fileOpenDBItem );
						fileOpenDBItem.setText( "Open Database" );
						fileOpenDBItem.setAction( getOpenDbFileAction() );
					}
					{
						fileCloseDBItem = new JMenuItem();
						fileMenu.add( fileCloseDBItem );
						fileCloseDBItem.setText( "Close Database" );
						fileCloseDBItem.setAction( getCloseDbFileAction() );
						fileCloseDBItem.setEnabled( false );
					}
					{
						fileMenuSeperator = new JSeparator();
						fileMenu.add( fileMenuSeperator );
					}
					{
						fileExitItem = new JMenuItem();
						fileMenu.add( fileExitItem );
						fileExitItem.setAction( getCloseAppAction() );
						fileExitItem.setText( "Exit" );
					}
				}
				{
					helpMenu = new JMenu();
					menuBar.add( helpMenu );
					helpMenu.setText( "Help" );
					{
						helpAboutItem = new JMenuItem();
						helpMenu.add( helpAboutItem );
						helpAboutItem.setAction( getOpenAboutAction() );
						helpAboutItem.setText( "About ..." );
					}
				}
			}
			pack();
			this.setSize( 1024, 768 );

			// show splashScreen
			SplashScreen sScreen = new SplashScreen( new ImageIcon( "img/splash.gif" ), this, 2500 );
			Thread splashThread = new Thread( sScreen );
			splashThread.start();

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * JTree the ClassTree
	 * 
	 */
	private JScrollPane getTreeScrollPane()
	{
		if ( treeScrollPane == null )
		{
			treeScrollPane = new JScrollPane();
			treeScrollPane.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );
			treeScrollPane.setViewportView( getClassTree() );
		}
		return treeScrollPane;
	}

	private JTree getClassTree()
	{
		if ( classTree == null )
		{
			ClassTreeModel treeModel = new ClassTreeModel( "No Database selected ..." );
			classTree = new JTree( treeModel );

			Icon dbIcon = new ImageIcon( "img/db.gif" );
			Icon classIcon = new ImageIcon( "img/class.gif" );
			Icon superClassIcon = new ImageIcon( "img/superClass.gif" );
			Icon memberSimpleIcon = new ImageIcon( "img/simpleMember.gif" );
			Icon memberObjectIcon = new ImageIcon( "img/objectMember.gif" );

			if ( dbIcon != null && classIcon != null && superClassIcon != null
					&& memberSimpleIcon != null && memberObjectIcon != null )
			{
				classTree.setCellRenderer( new ClassTreeCellRenderer( dbIcon, classIcon,
						superClassIcon, memberSimpleIcon, memberObjectIcon ) );
				ToolTipManager.sharedInstance().registerComponent( classTree );
			}

			classTree.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION );
			classTree.setShowsRootHandles( true );

			TreeSelectionListener tsl = new TreeSelectionListener()
			{

				public void valueChanged( TreeSelectionEvent e )
				{
					Object node = classTree.getLastSelectedPathComponent();

					// load instances for selected class (not for the root
					// element)
					if ( node instanceof IDbClass )
					{
						IDbClass clazz = (IDbClass) node;
						controler.loadInstancesByClass( clazz.getClassName() );
					}
				}
			};
			classTree.addTreeSelectionListener( tsl );
		}
		return classTree;
	}

	/**
	 * The containers stuff
	 * 
	 */
	private JSplitPane getRightVerticalSplit()
	{
		if ( rightVerticalSplit == null )
		{
			rightVerticalSplit = new JSplitPane();
			rightVerticalSplit.setOrientation( JSplitPane.VERTICAL_SPLIT );
			rightVerticalSplit.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );
			rightVerticalSplit.add( getRightTopPanel(), JSplitPane.TOP );
			rightVerticalSplit.add( getRightBottomPanel(), JSplitPane.BOTTOM );
		}
		return rightVerticalSplit;
	}

	private JPanel getRightTopPanel()
	{
		if ( rightTopPanel == null )
		{
			rightTopPanel = new JPanel();
			BorderLayout rightTopPanelLayout = new BorderLayout();
			rightTopPanel.setLayout( rightTopPanelLayout );
			rightTopPanel.setPreferredSize( new java.awt.Dimension( 807, 525 ) );
			rightTopPanel.add( getRightTopLabel(), BorderLayout.CENTER );
		}
		return rightTopPanel;
	}

	private JPanel getRightBottomPanel()
	{
		if ( rightBottomPanel == null )
		{
			rightBottomPanel = new JPanel();
			GroupLayout rightBottomPanelLayout = new GroupLayout( (JComponent) rightBottomPanel );
			rightBottomPanel.setLayout( rightBottomPanelLayout );
			rightBottomPanel.setPreferredSize( new java.awt.Dimension( 807, 141 ) );
			rightBottomPanel.setSize( 807, 141 );
			rightBottomPanelLayout
					.setHorizontalGroup( rightBottomPanelLayout
							.createSequentialGroup()
							.addContainerGap()
							.addGroup(
									rightBottomPanelLayout
											.createParallelGroup()
											.addGroup(
													GroupLayout.Alignment.LEADING,
													rightBottomPanelLayout
															.createSequentialGroup()
															.addComponent( getMetaClassNameLabel(),
																	GroupLayout.PREFERRED_SIZE,
																	783, GroupLayout.PREFERRED_SIZE )
															.addGap( 17 ) )
											.addGroup(
													GroupLayout.Alignment.LEADING,
													rightBottomPanelLayout
															.createSequentialGroup()
															.addComponent(
																	getMetaSuperClassNameLabel(),
																	GroupLayout.PREFERRED_SIZE,
																	782, GroupLayout.PREFERRED_SIZE )
															.addGap( 18 ) )
											.addGroup(
													GroupLayout.Alignment.LEADING,
													rightBottomPanelLayout
															.createSequentialGroup()
															.addComponent(
																	getMetaObjectCountLabel(),
																	GroupLayout.PREFERRED_SIZE,
																	791, GroupLayout.PREFERRED_SIZE )
															.addGap( 9 ) )
											.addComponent( getMetaQueryTimeLabel(),
													GroupLayout.Alignment.LEADING, 0, 800,
													Short.MAX_VALUE ) ) );
			rightBottomPanelLayout.setVerticalGroup( rightBottomPanelLayout
					.createSequentialGroup()
					.addContainerGap()
					.addComponent( getMetaClassNameLabel(), GroupLayout.PREFERRED_SIZE,
							GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE )
					.addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED )
					.addComponent( getMetaSuperClassNameLabel(), GroupLayout.PREFERRED_SIZE, 17,
							GroupLayout.PREFERRED_SIZE )
					.addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED )
					.addComponent( getMetaObjectCountLabel(), GroupLayout.PREFERRED_SIZE, 19,
							GroupLayout.PREFERRED_SIZE )
					.addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED )
					.addComponent( getMetaQueryTimeLabel(), GroupLayout.PREFERRED_SIZE, 16,
							GroupLayout.PREFERRED_SIZE ).addContainerGap( 547, 547 ) );
		}
		return rightBottomPanel;
	}

	/**
	 * Label for ObjectTable(if no Table shown)
	 * 
	 */
	private JLabel getRightTopLabel()
	{
		if ( rightTopLabel == null )
		{
			rightTopLabel = new JLabel();
			rightTopLabel.setHorizontalAlignment( JLabel.CENTER );
			rightTopLabel.setText( "Use the File Menu to select a Database ..." );
		}
		return rightTopLabel;
	}

	public void editRightTopLabel( String text )
	{
		rightTopPanel.removeAll();
		rightTopPanel.add( getRightTopLabel() );
		getRightTopLabel().setText( text );
		rightTopPanel.validate();
	}

	/**
	 * Main Object Table(we set it once without model) model are set in: public
	 * void updateObjectTable()
	 */
	private JScrollPane getObjectTable()
	{
		if ( objectTable == null )
		{
			objectTable = new JTable();

			objectTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
			objectTable.setColumnSelectionAllowed( true );
			objectTable.setRowSelectionAllowed( true );

			ListSelectionModel selModel = objectTable.getSelectionModel();
			selModel.addListSelectionListener( new ObjectTableListSelectionListener( objectTable,
					controler ) );

			objectTable.setDefaultRenderer( DbSimpleMember.class, new SimpleMemberCellRenderer() );
			objectTable.setDefaultRenderer( DbArrayMember.class, new ObjectMemberCellRenderer() );
			objectTable.setDefaultRenderer( DbObjectMember.class, new ObjectMemberCellRenderer() );
			objectTable.setDefaultRenderer( DbCollectionMember.class,
					new ObjectMemberCellRenderer() );
			objectTable.setDefaultRenderer( DbMapMember.class, new ObjectMemberCellRenderer() );

			tableScrollPane = new JScrollPane();
			tableScrollPane.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );
			tableScrollPane.setViewportView( objectTable );
		}
		return tableScrollPane;
	}

	public void setObjectTable()
	{
		rightTopPanel.removeAll();
		rightTopPanel.add( getObjectTable(), BorderLayout.CENTER );
		rightTopPanel.validate();
		rightTopPanel.repaint(); // repaint bug!?
	}

	/**
	 * Dialog(PopUp) for "extended" objects used from: public void
	 * updatePopUpWindow()
	 */
	private JScrollPane getDialogObjectTable( IDbObject[] dialogTableData )
	{
		JTable dialogObjectTable = new JTable();

		dialogObjectTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		dialogObjectTable.setColumnSelectionAllowed( true );
		dialogObjectTable.setRowSelectionAllowed( true );

		ListSelectionModel selModel = dialogObjectTable.getSelectionModel();
		selModel.addListSelectionListener( new ObjectTableListSelectionListener( dialogObjectTable,
				controler ) );

		// use own renderer for special classes
		dialogObjectTable.setDefaultRenderer( DbSimpleMember.class, new SimpleMemberCellRenderer() );
		dialogObjectTable.setDefaultRenderer( DbArrayMember.class, new ObjectMemberCellRenderer() );
		dialogObjectTable.setDefaultRenderer( DbObjectMember.class, new ObjectMemberCellRenderer() );
		dialogObjectTable.setDefaultRenderer( DbCollectionMember.class,
				new ObjectMemberCellRenderer() );
		dialogObjectTable.setDefaultRenderer( DbMapMember.class, new ObjectMemberCellRenderer() );

		// set the table model
		ObjectTableModel dialogTableModel = new ObjectTableModel( dialogTableData );
		dialogObjectTable.setModel( dialogTableModel );

		// set rowsorter
		TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<ObjectTableModel>();
		dialogObjectTable.setRowSorter( sorter );
		sorter.setModel( dialogTableModel );

		JScrollPane dialogTableScrollPane = new JScrollPane();
		dialogTableScrollPane.setViewportView( dialogObjectTable );

		return dialogTableScrollPane;
	}

	private void showObjectTableDialog( String title, IDbObject[] dialogTableData )
	{
		JDialog objectTableDialog = new JDialog( this );

		objectTableDialog.setTitle( " " + title );
		objectTableDialog.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		objectTableDialog.setPreferredSize( new java.awt.Dimension( 800, 200 ) );
		objectTableDialog.getContentPane().add( getDialogObjectTable( dialogTableData ),
				BorderLayout.CENTER );
		objectTableDialog.setSize( 800, 200 );

		objectTableDialog.pack();
		objectTableDialog.setLocationRelativeTo( null );
		objectTableDialog.setVisible( true );
	}

	/**
	 * infoLabels and methods to edit the labels this function sometimes get
	 * called from controler
	 */
	private JLabel getMetaClassNameLabel()
	{
		if ( metaClassNameLabel == null )
		{
			metaClassNameLabel = new JLabel();
			metaClassNameLabel.setText( "" );
			metaClassNameLabel.setFont( new java.awt.Font( "Tahoma", 0, 12 ) );
		}
		return metaClassNameLabel;
	}

	private JLabel getMetaSuperClassNameLabel()
	{
		if ( metaSuperClassNameLabel == null )
		{
			metaSuperClassNameLabel = new JLabel();
			metaSuperClassNameLabel.setText( "" );
			metaSuperClassNameLabel.setFont( new java.awt.Font( "Tahoma", 0, 12 ) );
		}
		return metaSuperClassNameLabel;
	}

	private JLabel getMetaObjectCountLabel()
	{
		if ( metaObjectCountLabel == null )
		{
			metaObjectCountLabel = new JLabel();
			metaObjectCountLabel.setText( "" );
			metaObjectCountLabel.setFont( new java.awt.Font( "Tahoma", 0, 12 ) );
		}
		return metaObjectCountLabel;
	}

	private JLabel getMetaQueryTimeLabel()
	{
		if ( metaQueryTimeLabel == null )
		{
			metaQueryTimeLabel = new JLabel();
			metaQueryTimeLabel.setText( "" );
			metaQueryTimeLabel.setFont( new java.awt.Font( "Tahoma", 0, 12 ) );
		}
		return metaQueryTimeLabel;
	}

	private JLabel getStatusLabel()
	{
		if ( statusLabel == null )
		{
			statusLabel = new JLabel();
			statusLabel.setText( "Status:" );
			statusLabel.setHorizontalAlignment( SwingConstants.LEFT );
		}
		return statusLabel;
	}

	private JLabel getStatusConnectedLabel()
	{
		if ( statusConnectedLabel == null )
		{
			statusConnectedLabel = new JLabel();
			statusConnectedLabel.setHorizontalAlignment( SwingConstants.LEFT );
			statusConnectedLabel.setText( "Disconnected" );
			statusConnectedLabel.setForeground( new java.awt.Color( 255, 0, 0 ) );
		}
		return statusConnectedLabel;
	}

	private JLabel getStatusDBLabel()
	{
		if ( statusDBLabel == null )
		{
			statusDBLabel = new JLabel();
			statusDBLabel.setHorizontalAlignment( SwingConstants.LEFT );
		}
		return statusDBLabel;
	}

	public void setStatusConnected( boolean connected )
	{
		if ( connected )
		{
			getStatusConnectedLabel().setText( "Connected" );
			getStatusConnectedLabel().setForeground( new java.awt.Color( 34, 139, 34 ) );
		}
		else
		{
			getStatusConnectedLabel().setText( "Disconnected" );
			getStatusConnectedLabel().setForeground( new java.awt.Color( 255, 0, 0 ) );
			getStatusDBLabel().setText( "" );
		}
	}

	public void setStatusConnected( boolean connected, String fileName, long fileSize )
	{
		if ( connected )
		{
			getStatusConnectedLabel().setText( "Connected" );
			getStatusConnectedLabel().setForeground( new java.awt.Color( 34, 139, 34 ) );
			getStatusDBLabel().setText( "[ " + fileName + " (" + fileSize + " Byte) ]" );
		}
	}

	/**
	 * functions for: enable/disable items in gui this function often get called
	 * from controler
	 */
	public void disableOpenFileItem()
	{
		fileOpenDBItem.setEnabled( false );
	}

	public void enableOpenFileItem()
	{
		fileOpenDBItem.setEnabled( true );
	}

	public void disableCloseFileItem()
	{
		fileCloseDBItem.setEnabled( false );
	}

	public void enableCloseFileItem()
	{
		fileCloseDBItem.setEnabled( true );
	}

	public void closeDatabase()
	{
		// set tree to default
		ClassTreeModel treeModel = new ClassTreeModel( "No Database selected ..." );
		classTree.setModel( treeModel );

		// set topLabel
		editRightTopLabel( "Use the File Menu to select a Database ..." );

		// metainfo
		getRightBottomPanel().setBackground( new java.awt.Color( 212, 208, 200 ) );
		getMetaClassNameLabel().setText( "" );
		getMetaSuperClassNameLabel().setText( "" );
		getMetaObjectCountLabel().setText( "" );
		getMetaQueryTimeLabel().setText( "" );
	}

	/**
	 * AboutDialog
	 * 
	 */

	private JDialog getAboutDialog()
	{
		if ( aboutDialog == null )
		{
			aboutDialog = new JDialog( this );
			GroupLayout aboutDialogLayout = new GroupLayout(
					(JComponent) aboutDialog.getContentPane() );
			aboutDialog.getContentPane().setLayout( aboutDialogLayout );
			aboutDialog.setPreferredSize( new java.awt.Dimension( 271, 185 ) );
			aboutDialog.setSize( 342, 244 );
			aboutDialog.setTitle( " About" );
			aboutDialogLayout.setHorizontalGroup( aboutDialogLayout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
							aboutDialogLayout
									.createParallelGroup()
									.addComponent( getAboutDialogImgLabel(),
											GroupLayout.Alignment.LEADING, 0, 239, Short.MAX_VALUE )
									.addGroup(
											GroupLayout.Alignment.LEADING,
											aboutDialogLayout
													.createSequentialGroup()
													.addGap( 0, 198, Short.MAX_VALUE )
													.addComponent( getAboutDialogClose(),
															GroupLayout.PREFERRED_SIZE,
															GroupLayout.PREFERRED_SIZE,
															GroupLayout.PREFERRED_SIZE ) ) )
					.addContainerGap() );
			aboutDialogLayout.setVerticalGroup( aboutDialogLayout
					.createSequentialGroup()
					.addContainerGap()
					.addComponent( getAboutDialogImgLabel(), 0, 102, Short.MAX_VALUE )
					.addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED )
					.addComponent( getAboutDialogClose(), GroupLayout.PREFERRED_SIZE,
							GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE )
					.addContainerGap() );
		}
		return aboutDialog;
	}

	private JLabel getAboutDialogImgLabel()
	{
		if ( aboutDialogImgLabel == null )
		{
			aboutDialogImgLabel = new JLabel();
			aboutDialogImgLabel.setBackground( new java.awt.Color( 255, 255, 255 ) );
			aboutDialogImgLabel.setIcon( new ImageIcon( "img/about.gif" ) );
		}
		return aboutDialogImgLabel;
	}

	private JButton getAboutDialogClose()
	{
		if ( aboutDialogClose == null )
		{
			aboutDialogClose = new JButton();
			aboutDialogClose.setText( "Close" );
			aboutDialogClose.setAction( getAboutCloseAction() );
		}
		return aboutDialogClose;
	}

	/**
	 * Error Dialog Functions Called from controller exception by open the DB
	 */
	public void showErrorDialog( String errorText )
	{
		getErrorDialog().setTitle( "Error" );
		getErrorLabel().setText( errorText );
		getErrorDialog().pack();
		getErrorDialog().setLocationRelativeTo( null );
		getErrorDialog().setVisible( true );
	}

	private JDialog getErrorDialog()
	{
		if ( errorDialog == null )
		{
			errorDialog = new JDialog( this );
			GroupLayout dataBaseLockedDialogLayout = new GroupLayout(
					(JComponent) errorDialog.getContentPane() );
			errorDialog.getContentPane().setLayout( dataBaseLockedDialogLayout );
			errorDialog.setPreferredSize( new java.awt.Dimension( 285, 175 ) );
			errorDialog.setSize( 285, 175 );
			dataBaseLockedDialogLayout.setHorizontalGroup( dataBaseLockedDialogLayout
					.createSequentialGroup()
					.addContainerGap( 70, 70 )
					.addComponent( getErrorLabel(), GroupLayout.PREFERRED_SIZE, 132,
							GroupLayout.PREFERRED_SIZE )
					.addGap( 0, 8, Short.MAX_VALUE )
					.addComponent( getErrorDialogButton(), GroupLayout.PREFERRED_SIZE, 56,
							GroupLayout.PREFERRED_SIZE ).addContainerGap() );
			dataBaseLockedDialogLayout.setVerticalGroup( dataBaseLockedDialogLayout
					.createSequentialGroup()
					.addContainerGap( 33, 33 )
					.addComponent( getErrorLabel(), GroupLayout.PREFERRED_SIZE, 53,
							GroupLayout.PREFERRED_SIZE )
					.addGap( 0, 29, Short.MAX_VALUE )
					.addComponent( getErrorDialogButton(), GroupLayout.PREFERRED_SIZE, 22,
							GroupLayout.PREFERRED_SIZE ).addContainerGap() );
		}
		return errorDialog;
	}

	private JButton getErrorDialogButton()
	{
		if ( errorDialogButton == null )
		{
			errorDialogButton = new JButton();
			errorDialogButton.setText( "Close" );
			errorDialogButton.setAction( getErrorDialogCloseAction() );
		}
		return errorDialogButton;
	}

	private JLabel getErrorLabel()
	{
		if ( errorLabel == null )
		{
			errorLabel = new JLabel();
			errorLabel.setHorizontalAlignment( SwingConstants.CENTER );
		}
		return errorLabel;
	}

	/***********************************************/
	/* Action Handler */
	/*   */
	/***********************************************/
	private AbstractAction getOpenDbFileAction()
	{
		if ( openDb4oFileAction == null )
		{
			openDb4oFileAction = new AbstractAction( "Open Database", null )
			{

				public void actionPerformed( ActionEvent evt )
				{
					fileChooser = new JFileChooser( "." );
					FileNameExtensionFilter filter = new FileNameExtensionFilter( ".yap files",
							"yap" );
					fileChooser.setFileFilter( filter );

					// open dialog and check
					if ( fileChooser.showOpenDialog( null ) == fileChooser.APPROVE_OPTION )
					{
						String filePath = fileChooser.getSelectedFile().getAbsolutePath();
						controler.openDb4oFile( filePath );
					}
				}
			};
		}
		return openDb4oFileAction;
	}

	private AbstractAction getCloseDbFileAction()
	{
		if ( closeDbFileAction == null )
		{
			closeDbFileAction = new AbstractAction( "Close Database", null )
			{

				public void actionPerformed( ActionEvent evt )
				{
					controler.closeDb4oFile();
				}
			};
		}
		return closeDbFileAction;
	}

	private AbstractAction getCloseAppAction()
	{
		if ( closeAppAction == null )
		{
			closeAppAction = new AbstractAction( "Exit", null )
			{

				public void actionPerformed( ActionEvent evt )
				{
					controler.closeDb4oFile();
					System.exit( 0 );
				}
			};
		}
		return closeAppAction;
	}

	private AbstractAction getAboutCloseAction()
	{
		if ( aboutCloseAction == null )
		{
			aboutCloseAction = new AbstractAction( "Close", null )
			{

				public void actionPerformed( ActionEvent evt )
				{
					getAboutDialog().dispose();
				}
			};
		}
		return aboutCloseAction;
	}

	private AbstractAction getOpenAboutAction()
	{
		if ( openAboutAction == null )
		{
			openAboutAction = new AbstractAction( "About ...", null )
			{

				public void actionPerformed( ActionEvent evt )
				{
					getAboutDialog().pack();
					getAboutDialog().setLocationRelativeTo( null );
					getAboutDialog().setVisible( true );
				}
			};
		}
		return openAboutAction;
	}

	private AbstractAction getErrorDialogCloseAction()
	{
		if ( aboutCloseAction == null )
		{
			errorDialogCloseAction = new AbstractAction( "Close", null )
			{

				public void actionPerformed( ActionEvent evt )
				{
					getErrorDialog().dispose();
				}
			};
		}
		return errorDialogCloseAction;
	}

	/**
	 * Model call this functions on dataUpdates Remember we Implement 4
	 * Interfaces (4 update types)
	 */
	@Override
	public void updateClassTree()
	{
		// set classTreeModel
		ClassTreeModel treeModel = new ClassTreeModel( model.getDbFile(), model.getLoadedClasses() );
		classTree.setModel( treeModel );
		// set as connected
		setStatusConnected( true, model.getDbFile(), model.getDbSize() );
	}

	@Override
	public void updateObjectTable()
	{
		IDbClass dbClass = model.getCurrentLoadedClass();
		// set new table model
		if ( dbClass.getInstances() != null )
		{
			ObjectTableModel tableModel = new ObjectTableModel( dbClass.getInstances() );
			objectTable.setModel( tableModel );
			// set rowsorter (we must set it for each model new)
			TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<ObjectTableModel>();
			sorter.setModel( tableModel );
			objectTable.setRowSorter( sorter );
		}
	}

	@Override
	public void updateMetaInfo()
	{
		IDbClass dbClass = model.getCurrentLoadedClass();
		getRightBottomPanel().setBackground( new java.awt.Color( 255, 255, 255 ) );

		String classStr = dbClass.getClassName();
		if ( dbClass.isSuperClass() )
			classStr = classStr + " (Is SuperClass from: " + dbClass.getChildClassesAsString()
					+ ")";

		getMetaClassNameLabel().setText( "Class: " + classStr );
		getMetaSuperClassNameLabel().setText( "SuperClass: " + dbClass.getSuperClassName() );
		getMetaObjectCountLabel().setText( "StoredInstances: " + dbClass.getInstancesCount() );
		getMetaQueryTimeLabel().setText( "QueryTime(ms): " + dbClass.getQueryExecutionTime() );
	}

	@Override
	public void updatePopUpWindow()
	{
		IDbObject[] dialogTableData = model.getCurrentPopupData();
		if ( dialogTableData != null )
		{
			String title = "Empty";
			// title of the window is dataType of the selected
			// object/list/map/array
			if ( dialogTableData.length > 0 )
				title = dialogTableData[0].getDataType();
			showObjectTableDialog( title, dialogTableData );
		}
	}
}
