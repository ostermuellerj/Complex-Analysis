double xmin = -2;
double ymin = -2;
int w = 2;
int h = 2;
double wh = h*w;
int maxIterations = 300;

//extra
double downX, downY, startX, startY, startWH;
boolean shift=false;
float zoomSpeed = 1.01;


void setup () 
{
  size(600,600);
  colorMode(HSB, 255); //hue/sat/bright
  loadPixels();
}

void draw ()
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
        b = 2.0 * a * b + y; 
        a = aa - bb + x; 
        if (aa + bb > 4.0) break;
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
void mousePressed() 
{
  downX=mouseX;
  downY=mouseY;
  startX=xmin;
  startY=ymin;
  startWH=wh;
}

void mouseDragged() 
{
  double deltaX=(mouseX-downX)/width;
  double deltaY=(mouseY-downY)/height;

  xmin = startX-deltaX*wh;
  ymin = startY-deltaY*wh; 
   
}

void keyPressed() 
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