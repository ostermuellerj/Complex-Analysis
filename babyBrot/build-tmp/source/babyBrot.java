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

public class babyBrot extends PApplet {

double xmin = -2;
double ymin = -2;
int w = 2;
int h = 2;
double wh = h*w;
int maxIterations = 300;

//extra
double downX, downY, startX, startY, startWH;
boolean shift=false;
float zoomSpeed = 1.01f;


public void setup () 
{
  
  colorMode(HSB, 255); //hue/sat/bright
  loadPixels();
}

public void draw ()
{
  
  double xmax = xmin + wh;
  double ymax = ymin + wh;
  
  double dx = (xmax-xmin)/ width;
  double dy = (ymax-ymin)/ height;
     
  double y = ymin;
  for (int j = 0; j < height; j++) 
  {
    double x = xmin;
    for (int i = 0; i < width; i++) 
    {
      double a = x;
      double b = y;
      int n = 0;
      while (n < maxIterations) 
      { 
        double aa = a * a; 
        double bb = b * b; 
        b = 2.0f * a * b + y; 
        a = aa - bb + x; 
        if (aa + bb > 4.0f) break;
        n++;
      }
      
      //Color
      //pixels[i+j*width] = (n==maxIterations) ? color(0) : color(n*16 % 255, 255, 255);
      
      if (n == maxIterations) 
      {
        pixels[i+j*width] = color(0);
      } 
      else 
      {
        float norm = map(n, 0, maxIterations, 0, 1);
        pixels[i+j*width] = color(map(sqrt(norm), 0, 1, 0, 255));
      }
      
      x += dx;
    }
    y += dy;
  }
  //println(ymax + " " + ymin);
  updatePixels();
}

//extra
public void mousePressed() 
{
  downX=mouseX;
  downY=mouseY;
  startX=xmin;
  startY=ymin;
  startWH=wh;
}

public void mouseDragged() 
{
  double deltaX=(mouseX-downX)/width;
  double deltaY=(mouseY-downY)/height;

  xmin = startX-deltaX*wh;
  ymin = startY-deltaY*wh; 
   
}

public void keyPressed() 
{
  if (key== 'X' || key == 'x') 
    {
      wh*=zoomSpeed;
      xmin*=zoomSpeed;
      ymin*=zoomSpeed;
    }
  if (key== 'C' || key == 'c')
    {
      wh/=zoomSpeed;
      xmin/=zoomSpeed;
      ymin/=zoomSpeed;
    }
  if (key== 'R' || key == 'r')
    {
      wh=4;
      xmin=-2;
      ymin=-2;
    }
}
  public void settings() {  size(600,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "babyBrot" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
