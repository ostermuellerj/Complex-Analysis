//"Spiral Plotter" by John Ostermueller
//3.2.17

float e=2.718;
float zoom=1;
float lineDensity=.5;
float zoomSpeed=1;
float xBound=1;
float yBound=1;

float circleCenter=0;

float colorGradient= 255;

int axisDummy = 1;

PFont f1;
PFont f2;

void setup ()
{
  size(1400, 900);
  background(colorGradient, colorGradient, colorGradient);
  f1 = createFont("Arial", 12, true);
  f2 = createFont("Arial Bold", 12, true);
}

void draw ()
{
  zoom*=(1.01*zoomSpeed);
  if(zoom>1000000)
  zoom=1;
  
  background(colorGradient, colorGradient, colorGradient);
  
  stroke(0);
  strokeWeight(0);
  noFill();
  for(float i=1;i<100;i+=.1)
  {
    ellipse(width/2,height/2, pow((i/8),50)*zoom, pow((i/8),50)*zoom);
  }

  if (axisDummy==1)
    drawAxis();

  for (float i=-100; i<100; i+=lineDensity)
  {
    fill(0);
    stroke(0);
    
    //generates point at u/v given an x/y
    ellipse((width/2)+getU(i, i), (height/2)+getV(i, i), 2, 2); 
    
    //draws line between each point
    strokeWeight(0);
    line((width/2)+getU(i, i), (height/2)+getV(i, i), (width/2)+getU(i+lineDensity, i+lineDensity), (height/2)+getV(i+lineDensity, i+lineDensity));
  }

  if (mousePressed == true)
    //left click zooms in, right click zooms out
    if (mouseButton == LEFT) 
    {
      //generating HIGHER u/v values zooms OUT
      zoom*=(1.01*zoomSpeed); 
      //colorGradient*=1.01;
    } 
    else if (mouseButton == RIGHT) 
    {
      //generating LOWER u/v values zooms IN
      zoom*=(0.99*(1/zoomSpeed)); 
      //colorGradient*=.01;
    }

  //change density of line using x and c keys
  if (keyPressed) 
    if (key == 'x' || key == 'X') 
    {
      lineDensity*=1.05;
    } else if (key == 'c' || key == 'C')
      lineDensity*=.95;
    else if (key == 'r' || key == 'R')
    {
      zoom=100;
      lineDensity=1;
      zoomSpeed=1;
      colorGradient=255;
    }
    else if (keyCode == UP) 
    {
      zoom*=(1.01*zoomSpeed);
    }
    else if (keyCode == DOWN) 
    {
      zoom*=(.99*(1/zoomSpeed));
    }
    else if (keyCode == RIGHT) 
    {
      zoomSpeed*=1.01;
    }
    else if (keyCode == LEFT) 
    {
      if(zoomSpeed>1)
      zoomSpeed*=.99;
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

float getU (float x, float y)
{
  return (pow(e, x)*(cos(y)))*zoom;
}

float getV (float x, float y)
{
  return pow(e, x)*(sin(y))*zoom;
}

void drawAxis()
{
  strokeWeight(0);
  stroke(0);
  line((width/2), 0, (width/2), height);
  line(0, (height/2), width, (height/2));
  
  line(50,(height/2)+15,50,(height/2)-15);
  line(width-50,(height/2)+15,width-50,(height/2)-15);
}

void keyPressed() {
  if (key == 'z' || key == 'Z')
  {
    axisDummy*=-1;
  }
}
