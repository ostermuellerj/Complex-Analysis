import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class spiralPlotter_v1_1 extends PApplet {

// "Spiral Plotter"
// John Ostermueller
//
// Created 3-2-17
// V1.1: 3-6-17
//
// Complex Analysis

float e=2.718281828459045235360287471352662497757247093699959574966967627724076630353f;
float zoom=1;
float lineDensity=.5f;
float zoomSpeed=1;
float xBound=1;
float yBound=1;

float circleCenter=0;

float colorGradient= 255;

int axisDummy = 1;

PFont f1;
PFont f2;


//setup method`
public void setup ()
{
  
  background(colorGradient, colorGradient, colorGradient);
  f1 = createFont("Arial", 12, true);
  f2 = createFont("Arial Bold", 12, true);
}

//main method
public void draw ()
{
  zoom*=(1.01f*zoomSpeed);
  if(zoom>1000000)
  zoom=1;
  
  background(colorGradient, colorGradient, colorGradient);
  
  stroke(0);
  strokeWeight(0);
  noFill();
  for(float i=1;i<100;i+=.1f)
  {
    ellipse(width/2,height/2, pow((i/8),50)*zoom, pow((i/8),50)*zoom);
  }

  if (axisDummy==1)
    drawAxis();

  for (float i=-100; i<100; i+=lineDensity)
  {
    fill(0);
    stroke(0);
    ellipse((width/2)+getU(i, i), (height/2)+getV(i, i), 2, 2); //generates point at u/v given an x/y
    strokeWeight(0);
    line((width/2)+getU(i, i), (height/2)+getV(i, i), (width/2)+getU(i+lineDensity, i+lineDensity), (height/2)+getV(i+lineDensity, i+lineDensity)); //line between each point
  }

  if (mousePressed == true)
    if (mouseButton == LEFT) //left click zooms in, right clikc zooms out
    {
      zoom*=(1.01f*zoomSpeed); //generating HIGHER u/v values zooms OUT
      //colorGradient*=1.01;
    } else if (mouseButton == RIGHT) 
    {
      zoom*=(0.99f*(1/zoomSpeed)); //generating LOWER u/v values zooms IN
      //colorGradient*=.01;
    }

  if (keyPressed) //change density of line using x and c keys
    if (key == 'x' || key == 'X') 
    {
      lineDensity*=1.05f;
    } else if (key == 'c' || key == 'C')
      lineDensity*=.95f;
    else if (key == 'r' || key == 'R')
    {
      zoom=100;
      lineDensity=1;
      zoomSpeed=1;
      colorGradient=255;
    }
    else if (keyCode == UP) 
    {
      zoom*=(1.01f*zoomSpeed);
    }
    else if (keyCode == DOWN) 
    {
      zoom*=(.99f*(1/zoomSpeed));
    }
    else if (keyCode == RIGHT) 
    {
      zoomSpeed*=1.01f;
    }
    else if (keyCode == LEFT) 
    {
      if(zoomSpeed>1)
      zoomSpeed*=.99f;
    }

  textAlign(LEFT);
  textFont(f2);
  text("SPIRAL PLOTTER V1.1", 40, 35);    
  textFont(f1);
  {
    text("Use x and c keys to increment line density,\nleft/right click or up/down arrow keys to zoom\nin/out. Left and Right arrow keys adjust zoom \nspeed. Press z to toggle axis. Press r to reset.", 40, 50);     
    text("zoom: " + zoom, width-(width/4)-50, 50); 
    text("lineDensity: " + lineDensity, width-(width/4)-50, 65);
    text("zoomSpeed: " + zoomSpeed, width-(width/4)-50, 80);
    text("window: [" + xBound*(1/zoom) + ", " + -1*xBound*(1/zoom) + "]", width-(width/4)-50, 95);
    
    textAlign(CENTER);
    text(-1*xBound*(1/zoom) + "",50,(height/2)-20);
    text(xBound*(1/zoom) + "",width-50,(height/2)-20);
  }
}


//methods
public float getU (float x, float y)
{
  return (pow(e, x)*(cos(y)))*zoom;
}

public float getV (float x, float y)
{
  return pow(e, x)*(sin(y))*zoom;
}

public void drawAxis()
{
  strokeWeight(0);
  stroke(0);
  line((width/2), 0, (width/2), height);
  line(0, (height/2), width, (height/2));
  
  line(50,(height/2)+15,50,(height/2)-15);
  line(width-50,(height/2)+15,width-50,(height/2)-15);
}

public void keyPressed() {
  if (key == 'z' || key == 'Z')
  {
    axisDummy*=-1;
  }
}
  public void settings() {  size(1400, 900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "spiralPlotter_v1_1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
