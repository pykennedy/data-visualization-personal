import processing.core.*;
import processing.event.MouseEvent;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
//import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.User;


public class ProccesingSketch extends PApplet {
	
	private static int 			width				= 1200;
	private static int			height				= 600;
	int 		   centerX 							= 0;
	int 		   centerY 							= 0;
	int 		   offsetX 							= 0;
	int 		   offsetY 							= 0;
	float 		   zoom 							= (float) 1.0;
	int		       photoColor						= color(255,255,255);
	int		       textColor						= color(0,128,0);
	int		       audioColor						= color(123,90,205);
	int		       videoColor						= color(196,255,0);
	int		       answerColor						= color(255,0,0);
	int		       quoteColor						= color(0,255,255);
	int		       chatColor						= color(36,31,182);
	int		       linkColor						= color(238,130,238);
	
	
	public void setup() {
		size(width,height);
		//setResizable(true);
		/*
		 * " The combination that works is to call frame.setResizable(true) in setup() and then to use frame.setSize(w,h) in draw().  "
		 */
		background(0);
		centerX = 0;
		centerY = 0;
		cursor(HAND);
		smooth();
	} 

	public void draw() {
		clear();
		setSize(width, height);
		background(0,0,0);
		if (mousePressed == true) {
			centerX = mouseX-offsetX;
			centerY = mouseY-offsetY;
		}
		translate(centerX,centerY);
		scale(zoom);
		
		if(MyGUI.getDrawingType() == 0)
		{
			drawClockFace();
		}
		else if(MyGUI.getDrawingType() == 1)
		{
			drawPieGraph();
		}
		else if(MyGUI.getDrawingType() == 2)
		{
			drawBarGraph();
		}
		else if(MyGUI.getDrawingType() == 3)
		{
			drawLineGraph();
		}
		else if(MyGUI.getDrawingType() == 4)
		{
			drawMosaic();
		}
	}
	
	public void mousePressed(){
		loop();
		offsetX = mouseX-centerX;
		offsetY = mouseY-centerY;
		//loop();
	}
	
	public void mouseReleased() {
		  //noLoop();  // Releasing the mouse stops looping draw()
		noLoop();
	}
	
	public void keyPressed() {
		// zoom
		loop();
		if (keyCode == UP) centerY -= 5;
		if (keyCode == DOWN) centerY += 5;
		if (keyCode == LEFT) centerX -= 5;
		if (keyCode == RIGHT) centerX += 5;
	}
	
	public void mouseWheel(MouseEvent event)
	{
		float e = event.getCount();
		int i = 0;
		e = e/20;
		zoom -= e;
		//System.out.println(zoom);
		redraw();
	}
	
	
	private void elseif(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased() {
		// zoom
		noLoop();
	}
	
	public void drawClockFace()
	{
		//-----------------------
		//ARRAY CREATION
		//-----------------------
		
		//MyGUI mG = new MyGUI();
		ArrayList<PagePost> page = new ArrayList<PagePost>();
		page = MyGUI.getPage();
		//page = mG.getPage();
		
															// array list containing individual posts
		//page = RawToArr.txtToArr(page,thePath);					// page gets return from txtToArr(a filled up array list)
		//-----------------------
		//VARIABLES
		//-----------------------
		int epoch2007		= 1167652800;
		int finishTime		= page.get(0).getTimestamp();
		int startTime 		= page.get(page.size() - 2).getTimestamp();
		int currentTime		= 0;
		int opac		 	= 180;
		float ir 		 	= random(25) + 250;
		float or 		 	= 300;
		float thetaDate  	= 0;
		PVector cp 			= new PVector(width/2,height/2);
		strokeWeight(1);
		strokeCap(SQUARE);
		
		//----------------------
		//DRAWING THE KEY
		//----------------------
		int keyY = 200;
		int keyX = 70;
		int keyX2 = keyX + 5;
		stroke(255,255,255);
		noFill();
		rect(keyX,keyY,75, 170);

		fill(photoColor);
		text("Photo", keyX2, keyY +20);
		
		fill(textColor);
		text("Text", keyX2, keyY + 40);
		
		fill(audioColor);
		text("Audio", keyX2, keyY + 60);
		
		fill(videoColor);
		text("Video", keyX2, keyY + 80);
		
		fill(answerColor);
		text("Answer", keyX2, keyY + 100);
		
		fill(quoteColor);
		text("Quote", keyX2, keyY + 120);
		
		fill(chatColor);
		text("Chat", keyX2, keyY + 140);
		
		fill(linkColor);
		text("Link", keyX2, keyY + 160);
		
		fill(photoColor);
		
		
		//-----------------------
		//ARRAY ITERATION
		//-----------------------
		
		
		for(int i=0; i < page.size() - 1;i++)
		{
			if(page.get(i).getInclude() == true && page.get(i).getType()!=null)
			{	
			
				String type = page.get(i).getType();
				
				switch(type)
				{
					case "photo" :
						stroke(photoColor, opac);
						or = 300; 
						ir = 290;
						break;
					case "text" :
						stroke(textColor,opac);
						or = 280; 
						ir = 270;
						break;
					case "audio" :
						stroke(audioColor,opac);
						or = 260;
						ir = 250;
						break;
					case "video" :
						stroke(videoColor,opac);
						or = 240; 
						ir = 230;
						break;
					case "answer" :
						stroke(answerColor,opac);
						or = 220; 
						ir = 210;
						break;
					case "quote" :
						stroke(quoteColor,opac);
						or = 200; 
						ir = 190;
						break;
					case "chat" :
						stroke(chatColor,opac);
						or = 180; 
						ir = 170;
						break;
					case "link" :
						stroke(linkColor,opac);
						or = 160; 
						ir = 150;
						break;
				}
				
				currentTime = page.get(i).getTimestamp();
				
				thetaDate = map(currentTime, startTime, finishTime, 0, 2 * PI);
				
				renderCirc(cp, or, ir, thetaDate);
			}
			
		}
		
		stroke(255,255,255,opac);
		fill(255,255,255);
		thetaDate = map(startTime, startTime, finishTime, 0, 2 * PI);
		renderStringInCirc(cp, 350, 340, thetaDate, "Start Time");
		int year = 2007;
		
		for(int i = epoch2007; i < finishTime; i += 31556926)
		{
			thetaDate = map(i, startTime, finishTime, 0, 2 * PI);
			if(i > startTime)
			{
				renderStringInCirc(cp, 350, 340, thetaDate, "" + year);
			}
			year++;
			System.out.println(thetaDate);
		}
		
		System.out.println("done");
	}
	
	public void drawLineGraph()
	{
		
		//-----------------------
		//ARRAY CREATION
		//-----------------------
		
		ArrayList<PagePost> page = new ArrayList<PagePost>();
		page = MyGUI.getPage();
		
		//-----------------------
		//VARIABLES
		//-----------------------
		int opac		 	= 180;
		PVector startPoint 	= new PVector(0,height);
		PVector endPoint 	= new PVector(0, height);
		int x 				= 0;
		int y				= 0;
		int noteCap			= 0;
		int heightTenth 	= height/10;
		int divide10		= (page.size()  -1)/10;
		int noteTenth		= 0;
		int k 				= 0;
		strokeWeight(1);
		strokeCap(SQUARE);
		
		//----------------------
		//DRAWING THE KEY
		//----------------------
		int keyY = 200;
		int keyX = -350;
		int keyX2 = keyX + 5;
		stroke(255,255,255);
		noFill();
		rect(keyX,keyY,75, 170);

		fill(photoColor);
		text("Photo", keyX2, keyY +20);
		
		fill(textColor);
		text("Text", keyX2, keyY + 40);
		
		fill(audioColor);
		text("Audio", keyX2, keyY + 60);
		
		fill(videoColor);
		text("Video", keyX2, keyY + 80);
		
		fill(answerColor);
		text("Answer", keyX2, keyY + 100);
		
		fill(quoteColor);
		text("Quote", keyX2, keyY + 120);
		
		fill(chatColor);
		text("Chat", keyX2, keyY + 140);
		
		fill(linkColor);
		text("Link", keyX2, keyY + 160);
		
		fill(photoColor);
		
		//-----------------------
		//ARRAY ITERATION
		//-----------------------
		for(int i=0; i < page.size() -1; i++)
		{
			int notes = page.get(i).getNotes();
			if(notes > noteCap)
			{
				noteCap = notes;
			}
		}
		
		noteTenth = noteCap/10;
		
		//Divides the horizontal graph by 10 pieces labeling the post #
		for(int i = 0; i <= page.size() - 1; i += divide10)
		{
			text("" + i + "", i, height + 20);
		}
		
		//Divides the vertical graph by 10 pieces labeling the note count
		for(int i = height; i >= 0; i -= heightTenth)
		{
			String num = NumberFormat.getNumberInstance(Locale.US).format(k);
			text("" + num + "", -100, i);
			k += noteTenth;
		}
		
		for(int i=0; i < page.size() - 1;i++)
		{
			if(page.get(i).getInclude() == true && page.get(i).getType()!=null)
			{	
			
				String type = page.get(i).getType();
				int notes = page.get(i).getNotes();
				
				switch(type)
				{
				case "photo" :
					stroke(photoColor,opac);
					break;
				case "text" :
					stroke(textColor,opac);
					break;
				case "audio" :
					stroke(audioColor,opac);
					break;
				case "video" :
					stroke(videoColor,opac);
					break;
				case "answer" :
					stroke(answerColor,opac);
					break;
				case "quote" :
					stroke(quoteColor,opac);
					break;
				case "chat" :
					stroke(quoteColor,opac);
					break;
				case "link" :
					stroke(linkColor,opac);
					break;
					
				}
				
				//x = (int) map(i, 0, page.size() - 1, 0 , width);
				x = i;
				y = (int) map(notes, 0, noteCap,height, 0);
				endPoint.x = x;
				endPoint.y = y;
				line(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
				startPoint.x = x;
				startPoint.y = y;
				
			}	
		}
		System.out.println("done");
	}
	
	public void drawBarGraph()
	{
		//-----------------------
		//ARRAY CREATION
		//-----------------------
		
		//MyGUI mG = new MyGUI();
		ArrayList<PagePost> page = new ArrayList<PagePost>();
		page = MyGUI.getPage();
		//page = mG.getPage();
		
															// array list containing individual posts
		//page = RawToArr.txtToArr(page,thePath);					// page gets return from txtToArr(a filled up array list)
		//-----------------------
		//VARIABLES
		//-----------------------
		
		//int finishTime		= page.get(0).getTimestamp();
		//int startTime 		= page.get(page.size() - 2).getTimestamp();
		//int currentTime		= 0;
		int opac		 	= 180;
		//float ir 		 	= random(25) + 250;
		//float or 		 	= 300;
		int photo			= 0;
		int text			= 0;
		int audio			= 0;
		int video			= 0;
		int answer			= 0;
		int quote			= 0;
		int chat			= 0;
		int link			= 0;
		noStroke();
		//float thetaDate  	= 0;
		//PVector cp 			= new PVector(width/2,height/2);
		//strokeWeight(1);
		//strokeCap(SQUARE);
		
		//----------------------
		//DRAWING THE KEY
		//----------------------
		textSize(12);
		int keyY = 200;
		int keyX = -300;
		int keyX2 = keyX + 5;
		stroke(255,255,255);
		noFill();
		rect(keyX,keyY,75, 170);

		fill(photoColor);
		text("Photo", keyX2, keyY +20);
		
		fill(textColor);
		text("Text", keyX2, keyY + 40);
		
		fill(audioColor);
		text("Audio", keyX2, keyY + 60);
		
		fill(videoColor);
		text("Video", keyX2, keyY + 80);
		
		fill(answerColor);
		text("Answer", keyX2, keyY + 100);
		
		fill(quoteColor);
		text("Quote", keyX2, keyY + 120);
		
		fill(chatColor);
		text("Chat", keyX2, keyY + 140);
		
		fill(linkColor);
		text("Link", keyX2, keyY + 160);
		
		fill(photoColor);
		
		//-----------------------
		//ARRAY ITERATION
		//-----------------------
		
	
		
		for(int i=0; i < page.size() - 1;i++)
		{
			if(page.get(i).getInclude() == true && page.get(i).getType()!=null)
			{	
				
				String type = page.get(i).getType();
				
				switch(type)
				{
					case "photo" :
						stroke(photoColor,opac);
						photo++;
						break;
					case "text" :
						stroke(textColor,opac);
						text++;
						break;
					case "audio" :
						stroke(audioColor,opac);
						audio++;
						break;
					case "video" :
						stroke(videoColor,opac);
						video++;
						break;
					case "answer" :
						stroke(answerColor,opac);
						answer++;
						break;
					case "quote" :
						stroke(quoteColor,opac);
						quote++;
						break;
					case "chat" :
						stroke(chatColor,opac);
						chat++;
						break;
					case "link" :
						stroke(linkColor,opac);
						link++;
						break;
					
				}
				
				//currentTime = page.get(i).getTimestamp();
				//thetaDate = map(currentTime, startTime, finishTime, 0, 2 * PI);
				
				//renderCirc(cp, or, ir, thetaDate);
			}
			
		}
		
		int[] heights = { photo, text, audio, video, answer, quote, chat, link};
		//for(int i=0; i < angles.length;i++)
		//{
		//	System.out.println(angles[i]);
		//}
		//pieChart(600, 550, angles, page.size()-1);
		int maxHeight = 0;
		for(int i = 0; i < heights.length; i++)
		{
			if(maxHeight < heights[i])
			{
				maxHeight = heights[i];
			}
		}
		
		int size = 75;
		int size2 = 0;
		
		drawBar(size2, size, photo, "photo", maxHeight);
		size2 = size2 + size;
		
		drawBar(size2, size, text, "text", maxHeight);
		size2 = size2 + size;
		
		drawBar(size2, size, audio, "audio", maxHeight);
		size2 = size2 + size;
		
		drawBar(size2, size, video, "video", maxHeight);
		size2 = size2 + size;
		
		drawBar(size2, size, answer, "answer", maxHeight);
		size2 = size2 + size;
		
		drawBar(size2, size, quote, "quote", maxHeight);
		size2 = size2 + size;
		
		drawBar(size2, size, chat, "chat", maxHeight);
		size2 = size2 + size;
		
		drawBar(size2, size, link, "link", maxHeight);
		size2 = size2 + size;
		
		System.out.println("done");
	}
	
	void drawBar(float leftCorner,float barWidth, float barHeight, String color, int maxHeight)
	{
		int opac = 255;
		int x = 0;
		switch(color)
		{
			case "photo" :
				fill(photoColor,opac);
				break;
			case "text" :
				fill(textColor,opac);
				break;
			case "audio" :
				fill(audioColor,opac);
				break;
			case "video" :
				fill(videoColor,opac);
				break;
			case "answer" :
				fill(answerColor,opac);
				break;
			case "quote" :
				fill(quoteColor,opac);
				break;
			case "chat" :
				fill(chatColor,opac);
				break;
			case "link" :
				fill(linkColor,opac);
				break;
			
		}
		System.out.println(barHeight);
		System.out.println(maxHeight);
		///float downScale = barHeight/ 3;
		float downScaleBarHeight = map(barHeight, 0, maxHeight, 0, width -200);
		//float leftCorner2 = leftCorner - leftCorner/2;
		rect(110, leftCorner, downScaleBarHeight, barWidth);
		fill(255,255,255,opac);
		textSize(26);
		text("" + (int)barHeight + "", downScaleBarHeight + 25 + 100,(float) (leftCorner + barWidth/1.6));
		text("" + color + "s", -100, (float) (leftCorner + barWidth/1.6)); 
	}
	/*
	 * renderCirc() is a utility function for drawing a circle with given points in a circle 
	 */
	
	public void drawPieGraph()
	{
		//-----------------------
		//ARRAY CREATION
		//-----------------------
		
		//MyGUI mG = new MyGUI();
		ArrayList<PagePost> page = new ArrayList<PagePost>();
		page = MyGUI.getPage();
		//page = mG.getPage();
		
															// array list containing individual posts
		//page = RawToArr.txtToArr(page,thePath);					// page gets return from txtToArr(a filled up array list)
		//-----------------------
		//VARIABLES
		//-----------------------
		
		//int finishTime		= page.get(0).getTimestamp();
		//int startTime 		= page.get(page.size() - 2).getTimestamp();
		//int currentTime		= 0;
		int opac		 	= 180;
		//float ir 		 	= random(25) + 250;
		//float or 		 	= 300;
		int photo			= 0;
		int text			= 0;
		int audio			= 0;
		int video			= 0;
		int answer			= 0;
		int quote			= 0;
		int chat			= 0;
		int link			= 0;
		noStroke();
		//float thetaDate  	= 0;
		//PVector cp 			= new PVector(width/2,height/2);
		//strokeWeight(1);
		//strokeCap(SQUARE);
		
		//----------------------
		//DRAWING THE KEY
		//----------------------
		int keyY = 200;
		int keyX = 70;
		int keyX2 = keyX + 5;
		stroke(255,255,255);
		noFill();
		rect(keyX,keyY,75, 170);

		fill(photoColor);
		text("Photo", keyX2, keyY +20);
		
		fill(textColor);
		text("Text", keyX2, keyY + 40);
		
		fill(audioColor);
		text("Audio", keyX2, keyY + 60);
		
		fill(videoColor);
		text("Video", keyX2, keyY + 80);
		
		fill(answerColor);
		text("Answer", keyX2, keyY + 100);
		
		fill(quoteColor);
		text("Quote", keyX2, keyY + 120);
		
		fill(chatColor);
		text("Chat", keyX2, keyY + 140);
		
		fill(linkColor);
		text("Link", keyX2, keyY + 160);
		
		fill(photoColor);
		
		//-----------------------
		//ARRAY ITERATION
		//-----------------------
		
		int count = 0; //test code
		
		for(int i=0;i<page.size()-1;i++)
			System.out.println(page.get(i).getInclude()+String.valueOf(i));
		
		for(int i=0; i < page.size() - 1;i++)
		{
			if(page.get(i).getInclude() == true && page.get(i).getType()!=null)
			{	
				
				String type = page.get(i).getType();
				
				if(type == null)
				{
					System.out.println(type);
					System.out.println(count);
				}
				else
				{
					count++;
				}
				switch(type)
				{
					case "photo" :
						stroke(photoColor,opac);
						photo++;
						break;
					case "text" :
						stroke(textColor,opac);
						text++;
						break;
					case "audio" :
						stroke(audioColor,opac);
						audio++;
						break;
					case "video" :
						stroke(videoColor,opac);
						video++;
						break;
					case "answer" :
						stroke(answerColor,opac);
						answer++;
						break;
					case "quote" :
						stroke(quoteColor,opac);
						quote++;
						break;
					case "chat" :
						stroke(chatColor,opac);
						chat++;
						break;
					case "link" :
						stroke(linkColor,opac);
						link++;
						break;
					
				}
				
				//currentTime = page.get(i).getTimestamp();
				//thetaDate = map(currentTime, startTime, finishTime, 0, 2 * PI);
				
				//renderCirc(cp, or, ir, thetaDate);
			}
			
		}
		
		int[] angles = { photo, text, audio, video, answer, quote, chat, link};
		for(int i=0; i < angles.length;i++)
		{
			System.out.println(angles[i]);
		}
		pieChart(600, 550, angles, page.size()-1);
		
		System.out.println("done");
	}
	
	void renderCirc(PVector cp, float or, float ir, float thetaDate)
	{
		
		float x = (sin(thetaDate) * or) + cp.x;	//sets x value of the outer radius
		float y = (cos(thetaDate) * or) + cp.y;	//sets y calue of the outer radius
		float xx = (sin(thetaDate) * ir) + cp.x; //sets x value of the inner radius
		float yy = (cos(thetaDate) * ir) + cp.y; //sets the y value of the inner radius
		
		line(xx,yy,x,y);	//draws the line from the outer radius point to the inner radius point
	}
	
	void renderStringInCirc(PVector cp, float or, float ir, float thetaDate, String s)
	{
		
		float x = (sin(thetaDate) * or) + cp.x;	//sets x value of the outer radius
		float y = (cos(thetaDate) * or) + cp.y;	//sets y value of the outer radius
		float xx = (sin(thetaDate) * ir) + cp.x; //sets x value of the inner radius
		float yy = (cos(thetaDate) * ir) + cp.y; //sets the y value of the inner radius
		
		//line(xx,yy,x,y);	//draws the line from the outer radius point to the inner radius point
		pushMatrix();
		translate(x,y);
		//rotate(thetaDate - HALF_PI);
		text(s, 0,0);
		popMatrix();
	}
	
	void pieChart(float outerDiameter, float innerDiameter, int data[],int size)
	{
		int opac = 255;
		float lastAngle = 0;
		for (int i = 0; i < data.length; i++) 
		{
			float angle = map(data[i], 0, size, 0, 2 * PI );
			System.out.println(angle);
			//float gray = map(i, 0, data.length, 0, 255);
			//fill(gray);
			switch(i)
			{
				case 0 :
					fill(photoColor,opac);
					break;
				case 1 :
					fill(textColor,opac);
					break;
				case 2 :
					fill(audioColor,opac);
					break;
				case 3 :
					fill(videoColor,opac);
					break;
				case 4 :
					fill(answerColor,opac);
					break;
				case 5 :
					fill(quoteColor,opac);
					break;
				case 6 :
					fill(chatColor,opac);
					break;
				case 7 :
					fill(linkColor,opac);
					break;
				
			}
			arc(width/2, height/2, outerDiameter, outerDiameter, lastAngle, lastAngle + angle);
			lastAngle += angle;
		}
		//fill(0,0,0);
		//ellipse(width/2, height/2, innerDiameter, innerDiameter);
	}
	
	public void drawMosaic()
	{
		//-----------------------
		//ARRAY CREATION
		//-----------------------
		
		ArrayList<PagePost> page = new ArrayList<PagePost>();
		page = MyGUI.getPage();
		
		//-----------------------
		//VARIABLES
		//-----------------------
		int opac = 180;
		int count3 = 0;
		int count4 = 0;
		int size = 20;
		fill(0,0,0,0);
		strokeWeight(1);
		noStroke();
		
		//----------------------
		//DRAWING THE KEY
		//----------------------
		int keyY = 200;
		int keyX = -200;
		int keyX2 = keyX + 5;
		stroke(255,255,255);
		noFill();
		rect(keyX,keyY,75, 170);

		fill(photoColor);
		text("Photo", keyX2, keyY +20);
		
		fill(textColor);
		text("Text", keyX2, keyY + 40);
		
		fill(audioColor);
		text("Audio", keyX2, keyY + 60);
		
		fill(videoColor);
		text("Video", keyX2, keyY + 80);
		
		fill(answerColor);
		text("Answer", keyX2, keyY + 100);
		
		fill(quoteColor);
		text("Quote", keyX2, keyY + 120);
		
		fill(chatColor);
		text("Chat", keyX2, keyY + 140);
		
		fill(linkColor);
		text("Link", keyX2, keyY + 160);
		
		fill(photoColor);
		
		//-----------------------
		//ARRAY ITERATION
		//-----------------------
		noStroke();
		
		for(int i=0; i < page.size() - 1;i++)
		{
			if(page.get(i).getInclude() == true && page.get(i).getType()!=null)
			{	
			
				String type = page.get(i).getType();
				
				//rotate(PI/360);
				switch(type)
				{
					case "photo" :
						//stroke(255,255,255,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(count3,count4,size,size);
						//white 255,255,255
						fill(photoColor,opac);
						break;
					case "text" :
						//stroke(0,128,0,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(count3,count4,size,size);
						//green 0 128 0
						fill(textColor,opac);
						break;
					case "audio" :
						//stroke(123,90,205,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(count3,count4,size,size);
						// 123,90,205
						fill(audioColor,opac);
						break;
					case "video" :
						//stroke(196,255,0,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(count3,count4,size,size);
						//black 0,0,0 yellow
						fill(videoColor,opac);
						break;
					case "answer" :
						//stroke(255,0,0,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(count3,count4,size,size);
						// red
						fill(answerColor,opac);
						break;
					case "quote" :
						//stroke(220,70,70,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						//pink i think
						ellipse(count3,count4,size,size);
						fill(quoteColor,opac);
						break;
					case "chat" :
						//stroke(36,31,182,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(count3,count4,size,size);
						//70,30,180 blue
						fill(chatColor,opac);
						break;
					case "link" :
						//stroke(36,31,182,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(count3,count4,size,size);
						//70,30,180 blue
						fill(linkColor,opac);
						break;
				}
				
				if(page.size() > 20000)
				{
					count3 = count3 + 5;
				}
				else
				{
					count3 = count3 + 20;
				}
				
				if (count3 >= width)
				{
					count3 = 0;
					count4 = count4 + 5;
				}
			}
		}
		System.out.println("done");
	}
	
	// LEGACY CODE
	
	public void drawFromBR()
	{
		int count3 = 200;
		int count4 = 200;
		int opac = 180;
		int size = 10;
		fill(0,0,0,0);
		
		//stroke(255, 180);
		
		/*
		//int count = 120;
	    for (int x = 50; x <= width-50; x +=20) {
	      for (int y = 50; y <= height-50; y+=20) {
	        float s = map(0, 120, 0, 0, TWO_PI*2);
	        arc(x, y, 14, 14, s, s + PI);
	        //count--;
	      }
	    }
	  	*/
		String line = null;
		String line2 = null;
	    try {
	    	
	    	int i = 0;
	    	int j = 0;
	    	
	    	//File file = new File(Scraper.filePath + Scraper.tumblrName + ".txt");
	    	File file = new File("C:/Users/alexm_000/Documents/data-visualization/DNA/src/files/" + Scraper.tumblrName + ".txt");
	    	BufferedReader br = new BufferedReader(new FileReader(file));	
	    	
	
	    	
	    	
	    	translate(width/2, height/2);
	    	
			while ((line = br.readLine()) != null) 
			{	
				
				if(i == 0){
					
						rotate(PI/360);
					//int count = 120;
					//float s = map(count, 120, 0, 0, TWO_PI*2);
					switch(line)
					{
					case "photo" :
						//stroke(255,255,255,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(count3,count4,size,size);
						//white 255,255,255
						fill(255,255,255,opac);
						break;
					case "text" :
						//stroke(0,128,0,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(170,170,size,size);
						//green 0 128 0
						fill(0,128,0,opac);
						break;
					case "audio" :
						//stroke(123,90,205,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(140,140,size,size);
						// 123,90,205
						fill(123,90,205,opac);
						break;
					case "video" :
						//stroke(196,255,0,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(110,110,size,size);
						//black 0,0,0 yellow
						fill(196,255,0,opac);
						break;
					case "answer" :
						//stroke(255,0,0,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(80,80,size,size);
						// red
						fill(255,0,0,opac);
						break;
					case "quote" :
						//stroke(220,70,70,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						//pink i think
						ellipse(50,0,size,size);
						fill(220,70,70,opac);
						break;
					case "chat" :
						//stroke(36,31,182,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(20,20,size,size);
						//70,30,180 blue
						fill(36,31,182,opac);
						break;
					case "link" :
						//stroke(36,31,182,opac);
						//arc(count3,count4, 20, 20, s, s + PI);
						ellipse(20,20,size,size);
						//70,30,180 blue
						fill(0,0,132,opac);
						break;
					
					}
					
					
					
					//count--;
					/*
					10
					10
					10
					interesting 
					*/
					/*
					count3 = count3 + 20;
					if (count3 >= width)
					{
						count3 = 10;
						count4 = count4 + 5;
					}
					System.out.println(line);
					*/
				
				}
				i++;
				if(i == 8)
				{
					i = 0;
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //save("candy" + random(2000) + ".jpg");
	}
}