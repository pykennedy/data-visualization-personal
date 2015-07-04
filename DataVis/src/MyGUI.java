// small portions that interact with Parser.java and RawToArr.java written by Peter Kennedy

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.io.File;

@SuppressWarnings("serial")
public class MyGUI extends JFrame implements ActionListener
{
	private static ArrayList<PagePost> page = new ArrayList<PagePost>(); 
	private static int drawingType;
	private  ProccesingSketch sketch;
	private static int notes=-1;
	private static String sourceURL=null;
	private static String timeParams=null;
	private static String[] types=new String[8];
	private static String tags=null;
	private static String path=null;
	
	private javax.swing.JPanel sketchPanel;
	private JPanel buttonPanel;
	private JPanel titlePanel;
	private JPanel mainPanel;
	
	private JLabel titleLabel;
	private JLabel sketchLabel;
	
	private JButton lineButton;
	private JButton mosaicButton;
	private JButton pieButton;
	private JButton barButton;
	private JButton maxButton;
	private JButton	miniButton;
	private JButton exitButton;
	private JButton mosaic2;
	
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmNewRip;
	private JMenuItem mntmLoadRip;
	private JMenu mnFilter;
	private JMenuItem mntmAddFilter;
	private JMenuItem mntmClearFilter;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component horizontalStrut_3;
	private Component horizontalStrut_4;
	private Component horizontalStrut_5;
	private Component horizontalStrut_6;
	private Component horizontalStrut_7;
	
	//private Form1 form1;
	
	
	public MyGUI()
	{ 
		//page=RawToArr.txtToArr(page,path);
		setLocation(350,150);
		//setUndecorated(true);
		setSize(1200,900);
		setMinimumSize(new Dimension(1200,600));
		setPreferredSize(new Dimension(1300, 800));
		setTitle("Data Visualization");
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		//this.setTitle("Data Visualization");
		//this.setForeground(Color.WHITE);
		//this.setLayout(new BorderLayout());
		
		//-------------------------
		// PANELS
		//-------------------------
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(null);
		
		sketchPanel = new JPanel();
		sketchPanel.setBackground(null);
		
		titlePanel = new JPanel();
		titlePanel.setBackground(null);
		
		mainPanel = new JPanel();
		//mainPanel.setSize(1200, 700);
		mainPanel.setBackground(new Color(45, 45, 48));
	    mainPanel.setBorder(BorderFactory.createMatteBorder(5,5,5,5, new Color(20, 20, 40, 200)));
		sketchLabel = new JLabel();
		
		//-------------------------
		// BUTTONS
		//-------------------------
		
		lineButton = new JButton("Line");
		lineButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lineButton.addActionListener(this);
		lineButton.setPreferredSize(new Dimension(220,40));		
		mosaicButton = new JButton("Clockface");
		mosaicButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mosaicButton.setPreferredSize(new Dimension(220,40));
		pieButton = new JButton("Pie");
		pieButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pieButton.setPreferredSize(new Dimension(220,40));
		
		barButton = new JButton("Bar");
		barButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		barButton.setPreferredSize(new Dimension(220,40));
		
		mosaic2 = new JButton("Mosaic");
		mosaic2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mosaic2.setPreferredSize(new Dimension(220,40));
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.add(mosaicButton);		
		buttonPanel.add(lineButton);
		buttonPanel.add(barButton);
		buttonPanel.add(pieButton);
		buttonPanel.add(mosaic2);
		/*GridBagConstraints a = new GridBagConstraints();
	    a.gridx 	= 0;
	    a.gridy 	= 0;
	    a.weightx 	= 1.0;
	    buttonPanel.add(mosaicButton, a);
	    GridBagConstraints a1 = new GridBagConstraints();
	    a.gridx 	= 1;
	    a.gridy 	= 0;
	    a.weightx 	= 1.0;
	    buttonPanel.add(lineButton, a1);
	    GridBagConstraints a2 = new GridBagConstraints();
	    a.gridx 	= 2;
	    a.gridy 	= 0;
	    a.weightx	= 1.0;
	    buttonPanel.add(pieButton, a2);
	    GridBagConstraints a3 = new GridBagConstraints();
	    a.gridx 	= 3;
	    a.gridy 	= 0;
	    a.weightx	= 1.0; */
	    //buttonPanel.add(otherButton, a3);
		
		//-------------------------
		//SKETCH
		//-------------------------
		sketch = new ProccesingSketch();
		sketchPanel.setSize(1200, 700);
		sketchPanel.setLayout(new BorderLayout(0, 0));
		sketchPanel.add(sketch);
		
	    GridBagLayout gbl_titlePanel = new GridBagLayout();
	    gbl_titlePanel.columnWidths = new int[]{412, 62, 542, 0, 0, 26, 27, -104, 0};
	    gbl_titlePanel.rowHeights = new int[]{12, 12, 0};
	    gbl_titlePanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	    gbl_titlePanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
	    titlePanel.setLayout(gbl_titlePanel);
	    
	    //-------------------------
	    // FILE MENU
	    //-------------------------
	    
	    menuBar = new JMenuBar();
	    
	    menuBar.setOpaque(true);
	    menuBar.setBorder(null);
	    menuBar.setBackground(null);
		GridBagConstraints gbc_menuBar = new GridBagConstraints();
		gbc_menuBar.fill = GridBagConstraints.BOTH;
		gbc_menuBar.insets = new Insets(0, 0, 0, 5);
		gbc_menuBar.gridheight = 2;
		gbc_menuBar.gridx = 0;
		gbc_menuBar.gridy = 0;
		titlePanel.add(menuBar, gbc_menuBar);
	    
	    mnFile = new JMenu("File");
	    mnFile.getPopupMenu().setLightWeightPopupEnabled(false);
	    mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 17));
	    menuBar.add(mnFile);
	    mnFile.setHorizontalAlignment(SwingConstants.LEFT);
	    mnFile.setForeground(Color.WHITE);
	    
	    mntmNewRip = new JMenuItem("New Rip");
	    mntmNewRip.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {//same as LOAD
	    		 final JFrame ripf = new JFrame();
	    		ripf.setPreferredSize(new Dimension(400,150));
	    		ripf.setLocation(750,400);
	    		ripf.setResizable(false);
	    		JPanel ripp = new JPanel();
	    		ripp.setPreferredSize(new Dimension(200,100));
	    		JLabel pathlabell = new JLabel("Enter the URL of the tumbler page in the area below");
	    		final JTextField  riptf = new JTextField("",30);
	    		
	    		
	    		//String pathlabel = "Enter the URL of the tumbler page in the area below";
	    		//pathlabell.setText(pathlabel);
	    		JButton oK = new JButton("Proceed");	
	    		oK.addActionListener(new ActionListener() {
	    	    	public void actionPerformed(ActionEvent arg0) { 
	    	    		Scraper scrap = new Scraper();
	    	    		Scraper.postList(riptf.getText());
	    	    		titleLabel.setText("tumblr: " + scrap.gettumblrName());
	    	    		ripf.dispose();
	    	    	} 
	    	    	});
	    		
	    		
	    		
	    		
	    		
	    		ripf.add(ripp);
	    		ripp.add(pathlabell);
	    		ripp.add(riptf);
	    		ripp.add(oK);
	    		
	    		
	    		ripf.pack();
	    		ripf.setVisible(true);
	    	}
	    });
	    mntmNewRip.setFont(new Font("Segoe UI", Font.PLAIN, 17));
	    mnFile.add(mntmNewRip);
	    
	    mntmLoadRip = new JMenuItem("Load Rip");
	    mntmLoadRip.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) { //trying to make a new window that takes a path to a file to load
	    		JFrame loadf = new JFrame();
	    		String pathname=null;
	    		JFileChooser chooser = new JFileChooser();
    		    int returnVal = chooser.showOpenDialog(loadf);
    		    if(returnVal == JFileChooser.APPROVE_OPTION) {
    		       pathname= chooser.getSelectedFile().getPath();
    		       titleLabel.setText("tumblr: " + chooser.getName(chooser.getSelectedFile()));
    		    }
	    	path=pathname;	
	    	page=RawToArr.txtToArr(page,path); 
	    		loadf.pack();
	    		loadf.setVisible(false);	    		
	    		
	    	}
	    });
	    mntmLoadRip.setFont(new Font("Segoe UI", Font.PLAIN, 17));
	    mnFile.add(mntmLoadRip);
	    
	    horizontalStrut = Box.createHorizontalStrut(20);
	    menuBar.add(horizontalStrut);
	    
	    mnFilter = new JMenu("Filter");
	    mnFilter.getPopupMenu().setLightWeightPopupEnabled(false);
	    mnFilter.setFont(new Font("Segoe UI", Font.PLAIN, 17));
	    menuBar.add(mnFilter);
	    mnFilter.setForeground(Color.WHITE);
	    
	    mntmAddFilter = new JMenuItem("Add Filter");
	    mntmAddFilter.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) { // Same as others
	    		final JFrame addf = new JFrame();
	    		JPanel addp = new JPanel();
	    		addf.setLocation(700,300);
	    		//addf.setUndecorated(true);
	    		addf.setResizable(false);
	    		JPanel maincont = new JPanel(new FlowLayout(FlowLayout.LEFT));//main container
	    		FlowLayout flow = new FlowLayout();
	    		GridLayout grid = new GridLayout(2,4);
	    		final JCheckBox Type = new JCheckBox("Type");	    		
	    		
	    		addf.getContentPane().add(maincont);
	    		addp.setAlignmentY(JComponent.LEFT_ALIGNMENT);
	    		addp.add(Type);
	    		addp.add(horizontalStrut);
	    		maincont.add(addp);
	    		maincont.setPreferredSize(new Dimension(375,300));
	    		
	    		final JRadioButton Text = new JRadioButton("Text");
	            final JRadioButton Photo = new JRadioButton("Photo");	
	            final JRadioButton Quotes = new JRadioButton("Quotes");
	            final JRadioButton Video = new JRadioButton("Video");
	            final JRadioButton Audio = new JRadioButton("Audio");
	            final JRadioButton Link = new JRadioButton("Link");
	            final JRadioButton Chat = new JRadioButton("Chat");
	            final JRadioButton Answer = new JRadioButton("Answer");
	            
	            addp.add(Text);
	            addp.add(Photo);
	           // addp.add(horizontalStrut);
	            addp.add(Quotes);
	            addp.add(Video);
	            addp.add(Audio);
	            addp.add(Link);
	            addp.add(Chat);
	            addp.add(Answer);
	            
	            
	            
	           // JPanel radioPanel = new JPanel(new GridLayout(0, 1));
	            JPanel radioPanel = new JPanel();
	            radioPanel.setLayout(grid);
	            radioPanel.add(Text);
	            radioPanel.add(Photo);
	            radioPanel.add(Quotes);
	            radioPanel.add(Video);
	            radioPanel.add(Audio);
	            radioPanel.add(Link);
	            radioPanel.add(Chat);
	            radioPanel.add(Answer);
	            
	            maincont.add(radioPanel, BorderLayout.LINE_START);
	           // addp.add(radioPanel);
	           // addp.add(new JSeparator());
	    		
	    		addf.getContentPane().add(new JSeparator(),
	    		          BorderLayout.LINE_START);
	    		addf.pack();
	    		addf.setResizable(false);
	    		
	    		JPanel typep =new JPanel();
	    		//typep.setBackground(Color.WHITE);
	    		
	    		JPanel notep = new JPanel();
	    		//notep.setBackground(Color.RED);
	    		
	    		JPanel timep = new JPanel();
	    		
	    		JPanel sourcep = new JPanel();
	    		//sourcep.setBackground(Color.BLUE);
	    		JPanel buttonp = new JPanel();
	    		JPanel labelp = new JPanel();
	    		
	    		JLabel tagslabel = new JLabel("Tags:",  SwingConstants.LEFT);
	    		final JLabel noteslabel = new JLabel("At least");
	    		JLabel notestail = new JLabel("notes.");
	    		JLabel sourcelabel = new JLabel("http://");
	    		final JLabel sourcetail = new JLabel(".tumblr.com");
	    		
	    		
	    		final JCheckBox Tags = new JCheckBox("Tags");
	    		final JCheckBox Notes = new JCheckBox("Notes");
	    		final JCheckBox Source = new JCheckBox("Source");
	    		final JCheckBox Time = new JCheckBox("Time");
	    		
	    		final JTextField say = new JTextField("whatever",20);
	    		final JTextField notef = new JTextField("notes",5);
	    		final JTextField sourcet = new JTextField("source url",13);
	    		final JTextField timef = new JTextField("Time",25);
	    		
	    		typep.setLayout(flow);
	    		typep.setAlignmentY(JComponent.LEFT_ALIGNMENT);
	    		notep.setLayout(flow);
	    		notep.setAlignmentY(JComponent.LEFT_ALIGNMENT);
	    		timep.setLayout(flow);
	    		timep.setAlignmentY(JComponent.LEFT_ALIGNMENT);
	    		sourcep.setLayout(flow);
	    		
	    		
	    		horizontalStrut_3 = Box.createHorizontalStrut(27);
	    		horizontalStrut_4 = Box.createHorizontalStrut(23);
	    		horizontalStrut_5 = Box.createHorizontalStrut(15);
	    		horizontalStrut_6 = Box.createHorizontalStrut(23);
	    		
	    		typep.add(Tags);
	    		typep.add(horizontalStrut_3);
	    		typep.add(tagslabel);
	    		typep.add(say);
	    		
	    		notep.add(Notes);
	    		notep.add(horizontalStrut_4);
	    		notep.add(noteslabel);
	    		notep.add(notef);
	    		notep.add(notestail);
	    		
	    		timep.add(Time);
	    		timep.add(horizontalStrut_6);
	    		//timep.add(noteslabel);
	    		timep.add(timef);
	    		//timep.add(notestail);
	    		
	    		sourcep.add(Source);
	    		sourcep.add(horizontalStrut_5);
	    		sourcep.add(sourcelabel);
	    		sourcep.add(sourcet);
	    		final JLabel errorlabel = new JLabel();
	    		sourcep.add(sourcetail);
	    		
	    		JButton Filter = new JButton("Filter");
	    		//Filter.addActionListener(this);

	    		Filter.addActionListener(new ActionListener(){
	    			   public void actionPerformed(ActionEvent ae){
	    				   if (Notes.isSelected() == false && Time.isSelected() == false && Tags.isSelected() == false && Source.isSelected() == false && Type.isSelected() == false)
	   						{
	   							errorlabel.setText("***NO CHECK BOX SELECTED***");
	   						}
	   					    
	   						else 
	   						{   				   
	    				   if(Notes.isSelected() == true)
	    		    		{
	    		    			notes = Integer.parseInt(notef.getText());
	    		    			
	    		    		}
	    				   if(Time.isSelected() == true)
	    		    		{
	    		    			timeParams = timef.getText();
	    		    			
	    		    		}
	    				   
	    				   if(Tags.isSelected() == true)
	    		    		{
	    					    tags=say.getText();
	    		    		}
	    				   
	    				   if(Source.isSelected() == true)
	    		    		{
	    		    			sourceURL = sourcet.getText();
	    		    			//sourceURL = sourceURL + sourcetail;
	    		    		}
	    				   
	    				   if(Type.isSelected() == true)
	    		    		{ 
	    					   int i=0;
	    					   
	    		    			if(Text.isSelected()==true)
	    		    			{
	    		    				types[i] = "text";
	    		    				i++;
	    		    			}
	    		    			if(Photo.isSelected()==true)
	    		    			{
	    		    				types[i] = "photo";
	    		    				i++;
	    		    			}
	    		    			if(Audio.isSelected()==true)
	    		    			{
	    		    				types[i] = "audio";
	    		    				i++;
	    		    			}
	    		    			if(Video.isSelected()==true)
	    		    			{
	    		    				types[i] = "video";
	    		    				i++;
	    		    			}
	    		    			if(Quotes.isSelected()==true)
	    		    			{
	    		    				types[i] = "quotes";
	    		    				i++;
	    		    			}
	    		    		}
	    				   Parser parse = new Parser();
	    				     				  
	    				  try {
	    					   page=parse.runFilter(page, types, notes, sourceURL, timeParams, tags);
	    						notes=-1;
	    						sourceURL=null;
	    						timeParams=null;
	    						types=new String[8];
	    						tags=null;
	    					  } catch (BadFormatException e) {
	    						  JOptionPane pane = new JOptionPane();
	  							JOptionPane.showMessageDialog(pane, e.getError());
	    					   //System.out.println(e.getError());
	    					  } catch (BadRootException e) {
	    						  JOptionPane pane = new JOptionPane();
	  							JOptionPane.showMessageDialog(pane, e.getError());
	    					  // System.out.println(e.getError());
	    					  }
	    				  
	    				  
	    				  addf.dispose();
	    			   }}
	    			});
//Stuck here
	    		
	    		horizontalStrut_6 = Box.createHorizontalStrut(145);
	    		buttonp.add(horizontalStrut_6);
	    		buttonp.add(Filter);
	    		
	    		horizontalStrut_7 = Box.createHorizontalStrut(90);
	    		labelp.add(horizontalStrut_7);
	    		labelp.add(errorlabel);
	    		
	    		maincont.add(typep);
	    		maincont.add(notep);
	    		maincont.add(timep);
	    		maincont.add(sourcep);
	    		maincont.add(buttonp);
	    		maincont.add(labelp);
	    		addf.setVisible(true);	    		
	    		
	    	}
	    });
	    mntmAddFilter.setFont(new Font("Segoe UI", Font.PLAIN, 17));
	    mnFilter.add(mntmAddFilter);
	    
	    mntmClearFilter = new JMenuItem("Clear Filter");
	    mntmClearFilter.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) { 
	    		Parser parse = new Parser();
	    		parse.includeAll(page);
	    		//same as others, not sure what this does, so i left blank
	    	}
	    });
	    mntmClearFilter.setFont(new Font("Segoe UI", Font.PLAIN, 17));
	    mnFilter.add(mntmClearFilter);
	    
	    horizontalStrut_1 = Box.createHorizontalStrut(20);
	    menuBar.add(horizontalStrut_1);
	    
	    mnHelp = new JMenu("Help");
	    mnHelp.getPopupMenu().setLightWeightPopupEnabled(false);
	    mnHelp.setFont(new Font("Segoe UI", Font.PLAIN, 17));
	    menuBar.add(mnHelp);
	    mnHelp.setForeground(Color.WHITE);
	    
	    mntmAbout = new JMenuItem("About");
	    mntmAbout.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent arg0) { //Add event for clicking
	    		JFrame about = new JFrame();
	    		about.setResizable(false);
	    		about.setPreferredSize(new Dimension(300,75));
	    		about.setLocation(850,450);
	    		//about.setUndecorated(true);
	    		JPanel aboutp = new JPanel();
	    		
	    		String[] blurb = {"asd,asd,asd","fart much?"};
	    		JScrollPane scroll = new JScrollPane(aboutp);
	    		JLabel aboutmes = new JLabel("Read the Read me");
	    		about.getContentPane().add(scroll);
	    		String Verbage;
	    		Verbage = "blah blah\n" + "bleh bleh\n" + "Bluh bluh";
	    		//fluff.add(Verbage);
	    		aboutp.add(aboutmes);
	    		//aboutp.add(scroll);
	    		//aboutp.setPreferredSize(new Dimension(300,500));
	    		about.pack();
	    		about.setVisible(true);
	    		
	    	
	    	}
	    });
	    mntmAbout.setFont(new Font("Segoe UI", Font.PLAIN, 17));
	    mnHelp.add(mntmAbout);
	    
	    //-------------------------
	    // LABELS
	    //-------------------------
	    
	    titleLabel = new JLabel("Data Visulalization");
	    titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    titleLabel.setForeground(new Color(220,220,220));
	    titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	    
	    //--------------------------
	    // TOP PANEL ADJUSTMENTS
	    // adding the menu to top panel
	    //--------------------------
	    
	    GridBagConstraints tc = new GridBagConstraints();
	    tc.anchor = GridBagConstraints.WEST;
	    tc.gridheight = 2;
	    tc.gridx = 5;
	    tc.gridy = 0;
	    tc.weightx = 1.0;
	    tc.gridx = 1;
	    tc.gridy = 0;
	    tc.weightx = 1.0;
	    tc.insets = new Insets(0, 0, 0, 5);
	    GridBagConstraints gbc_titleLabel = new GridBagConstraints();
	    gbc_titleLabel.anchor = GridBagConstraints.EAST;
	    gbc_titleLabel.gridheight = 2;
	    gbc_titleLabel.fill = GridBagConstraints.VERTICAL;
	    gbc_titleLabel.gridx = 2;
	    gbc_titleLabel.gridy = 0;
	    gbc_titleLabel.insets = new Insets(0, 0, 0, 5);
	    titlePanel.add(titleLabel, gbc_titleLabel);
	    tc.gridx = 2;
	    tc.gridy = 0;
	    tc.weightx = 0.0;
	    tc.gridx = 3;
	    tc.gridy = 0;
	    tc.weightx = 0.0;
	    tc.gridx = 4;
	    tc.gridy = 0;
	    tc.gridx = 5;
	    tc.gridy = 0;
	    /*
	    Icon icon = new ImageIcon(getClass().getResource("min-icon34.png"));
	    Icon icon2 = new ImageIcon(getClass().getResource("min-icon34H.png"));
	    //JButton button = new JButton(icon);
	    miniButton 		= new JButton(icon);
	    miniButton.setHorizontalAlignment(SwingConstants.RIGHT);
	    //miniButton.setIcon(new ImageIcon("media/min-icon34.png"));
	    miniButton.setRolloverIcon(icon2);
	    miniButton.setBorder(null);
	    miniButton.setBorderPainted(false);
	    miniButton.setContentAreaFilled(false);
	    miniButton.setRolloverEnabled(true);
	    GridBagConstraints tc2 = new GridBagConstraints();
	    tc2.gridheight = 2;
	    tc2.anchor = GridBagConstraints.SOUTHWEST;
	    tc2.insets = new Insets(0, 0, 5, 5);
	    tc2.gridx = 7;
	    tc2.gridy = 0;
	    GridBagConstraints gbc_miniButton = new GridBagConstraints();
	    gbc_miniButton.anchor = GridBagConstraints.EAST;
	    gbc_miniButton.gridx = 5;
	    gbc_miniButton.insets = new Insets(0, 0, 0, 5);
	    gbc_miniButton.gridheight = 2;
	    gbc_miniButton.gridy = 0;
	    titlePanel.add(miniButton, gbc_miniButton);
	    
	    Icon icon3 = new ImageIcon(getClass().getResource("max-icon34.png"));
	    Icon icon4 = new ImageIcon(getClass().getResource("max-icon34H.png"));
	    maxButton = new JButton(icon3);
	    maxButton.setHorizontalAlignment(SwingConstants.RIGHT);
	    //maxButton.setIcon(new ImageIcon("media/max-icon34.PNG"));
	    maxButton.setRolloverIcon(icon4);
	    maxButton.setBorder(null);
	    maxButton.setBorderPainted(false);
	    maxButton.setContentAreaFilled(false);
	    maxButton.setRolloverEnabled(true);
	    GridBagConstraints tc3 = new GridBagConstraints();
	    tc3.anchor = GridBagConstraints.SOUTHWEST;
	    tc3.insets = new Insets(0, 0, 5, 5);
	    tc3.gridx = 8;
	    tc3.gridy = 0;
	    GridBagConstraints gbc_maxButton = new GridBagConstraints();
	    gbc_maxButton.anchor = GridBagConstraints.EAST;
	    gbc_maxButton.gridx = 6;
	    gbc_maxButton.insets = new Insets(0, 0, 0, 5);
	    gbc_maxButton.gridheight = 2;
	    gbc_maxButton.gridy = 0;
	    titlePanel.add(maxButton, gbc_maxButton);
	    
	    
	    Icon icon5 = new ImageIcon(getClass().getResource("exit-icon34.png"));
	    Icon icon6 = new ImageIcon(getClass().getResource("exit-icon34H.png"));
	    exitButton 		= new JButton(icon5);
	    exitButton.setHorizontalAlignment(SwingConstants.RIGHT);
	   // exitButton.setIcon(new ImageIcon("media/exit-icon34.png"));
	    exitButton.setRolloverIcon(icon6);
	    exitButton.setBorder(null);
	    exitButton.setBorderPainted(false);
	    exitButton.setContentAreaFilled(false);
	    exitButton.setRolloverEnabled(true);
	    GridBagConstraints tc4 = new GridBagConstraints();
	    tc4.anchor = GridBagConstraints.EAST;
	    tc4.insets = new Insets(0, 0, 5, 0);
	    tc4.gridx = 5;
	    tc4.gridy = 0;
	    GridBagConstraints gbc_exitButton = new GridBagConstraints();
	    gbc_exitButton.anchor = GridBagConstraints.EAST;
	    gbc_exitButton.gridx = 7;
	    gbc_exitButton.gridheight = 2;
	    gbc_exitButton.gridy = 0;
	    titlePanel.add(exitButton, gbc_exitButton);
	    
	    exitButton.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) {
	    		System.exit(0);
	    	}
	    });
		*/
		//--------------------------
		// MOUSE LISTENERS
		//--------------------------
		
		mosaicButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {//Add methods for mouse events to trigger loading graphics
				drawingType = 0;
				sketch.init();
				sketch.start();
				sketchPanel.add(sketch);
			}
		});
		
		pieButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {//Add methods for mouse events to trigger loading graphics
				drawingType = 1;
				//sketch.clear();
				sketch.init();
				sketch.start();
				sketchPanel.add(sketch);
			}
		});
		
		barButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {//Add methods for mouse events to trigger loading graphics
				drawingType = 2;
				sketch.init();
				sketch.start();
				sketchPanel.add(sketch);
			}
		});
		
		lineButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {//Add methods for mouse events to trigger loading graphics
				drawingType = 3;
				sketch.init();
				sketch.start();
				sketchPanel.add(sketch);
			}
		});
		
		mosaic2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {//Add methods for mouse events to trigger loading graphics
				drawingType = 4;
				sketch.init();
				sketch.start();
				sketchPanel.add(sketch);
			}
		});
			
		//Adding other things to Frame
		
		/*GridBagConstraints c = new GridBagConstraints();
	    c.gridx 	= 0;
	    c.gridy 	= 0;
	    c.weightx 	= 1.0;
	    c.fill  	= GridBagConstraints.BOTH;
	    mainPanel.add(titlePanel, c);
	    GridBagConstraints c1 = new GridBagConstraints();
	    c1.gridx 	= 0;
	    c1.gridy 	= 1;
	    c1.weightx 	= 1.0;
	    c1.fill  	= GridBagConstraints.BOTH;
	    mainPanel.add(sketchPanel, c1);
	    GridBagConstraints c2 = new GridBagConstraints();
	    c2.anchor = GridBagConstraints.SOUTH;
	    c2.gridx 	= 0;
	    c2.gridy 	= 2;
	    c2.weightx	= 1.0;
	    c2.weighty  = 2.0;
	    c2.fill  	= GridBagConstraints.HORIZONTAL;
	    */mainPanel.setLayout(new BorderLayout(0, 0));
	    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	    mainPanel.add(titlePanel, BorderLayout.NORTH);
	    GridBagConstraints tc1 = new GridBagConstraints();
	    tc1.anchor = GridBagConstraints.NORTHWEST;
	    tc1.insets = new Insets(0, 0, 0, 5);
	    tc1.gridx = 6;
	    tc1.gridy = 1;
	    mainPanel.add(sketchPanel, BorderLayout.CENTER);
		//Adding Sketch to the Frame
		
		//this.add(sketchPanel);
		/*sketch.init();
		sketch.frame = this;*/
		
		this.setContentPane(mainPanel);
		pack();
		
	}



	public static void main(String[] args)
	{
		new MyGUI().setVisible(true);		
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource()==lineButton)
		{
			//sketch.init();
			//sketchPanel.add(sketch);
		}
	}

	static public ArrayList<PagePost> getPage()
	{
		return page;
	}
	
	static public int getDrawingType()
	{
		return drawingType;
	}
	static public String getPath()
	{
		return path;
	}
	
}