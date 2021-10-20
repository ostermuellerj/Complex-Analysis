//Adapted from Daniel Shiffman https://processing.org/examples/mandelbrot.html

size(640, 360);
noLoop();
background(255);

float w = 4.5;
float h = (w * height) / width;

float xmin = -w/2;
float ymin = -h/2;

loadPixels();

int maxiterations = 1000;

float xmax = xmin + w;
float ymax = ymin + h;

float dx = (xmax - xmin) / (width);
float dy = (ymax - ymin) / (height);

float y = ymin;
for (int j = 0; j < height; j++) 
{
  float x = xmin;
  for (int i = 0; i < width; i++) 
  {

    float a = x;
    float b = y;
    int n = 0;
    while (n < maxiterations) 
    {
      float aa = a * a;
      float bb = b * b;
      float twoab = 2.0 * a * b;
      a = aa - bb + x; //+ -0.8;
      b = twoab + y; //+ 0.165;
      if (dist(aa, bb, 0, 0) > 4.0) 
      {
        break;  // Bail
      }
      n++;
    }

    if (n == maxiterations) 
    {
      pixels[i+j*width] = color(0);
    } else 
    {
      float norm = map(n, 0, maxiterations, 0, 1);
      pixels[i+j*width] = color(map(sqrt(norm), 0, 1, 0, 255));
    }
    x += dx;
  }
  y += dy;
}
updatePixels();
