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

public class colorizedMandelbrot extends PApplet {

// Interactive Mandelbrot Set
// Inspired by Daniel Shiffman's Processing example.
// by Rick Companje - www.companje.nl - 6 december 2009

double xmin = -2.5f;
double ymin = -2;
double wh = 4;
double downX, downY, startX, startY, startWH;
int maxiterations = 25;
boolean shift=false;

public void setup() {
  
  colorMode(HSB, 255);
  loadPixels();
}

public void mousePressed() {
  downX=mouseX;
  downY=mouseY;
  startX=xmin;
  startY=ymin;
  startWH=wh;
}

public void keyPressed() {
  if (keyCode==SHIFT) shift=true;
}

public void keyReleased() {
  if (keyCode==SHIFT) shift=false;
}

public void mouseDragged() {
  double deltaX=(mouseX-downX)/width;
  double deltaY=(mouseY-downY)/height;

  if (!shift) {
    xmin = startX-deltaX*wh;
    ymin = startY-deltaY*wh;
  } 
  else {
    if (wh>10) wh=10;
    if (deltaX>1) deltaX=1;
    wh = startWH-deltaX*wh;
    xmin = startX+deltaX*wh/2;
    ymin = startY+deltaX*wh/2;
  }
}

public void draw() {
  double xmax = xmin + wh;
  double ymax = ymin + wh;

  // Calculate amount we increment x,y for each pixel
  double dx = (xmax-xmin) / width;
  double dy = (ymax-ymin) / height;

  double y = ymin;
  for (int j = 0; j < height; j++) {
    double x = xmin;
    for (int i = 0; i < width; i++) {
      double a = x;
      double b = y;
      int n = 0;
      while (n < maxiterations) { 
        double aa = a * a; 
        double bb = b * b; 
        b = 2.0f * a * b + y; 
        a = aa - bb + x; 
        if (aa + bb > 16.0f) break;
        n++;
      }

      pixels[i+j*width] = (n==maxiterations) ? color(0) : color(n*16 % 255, 255, 255);

      x += dx;
    }
    y += dy;
  }
  updatePixels();
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "colorizedMandelbrot" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
