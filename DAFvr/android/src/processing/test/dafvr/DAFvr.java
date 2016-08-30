package processing.test.dafvr;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.cardboard.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class DAFvr extends PApplet {


PShape grid, floor;
DAF[] dafjes = new DAF[100];

public void setup() {
  

  floor = createShape(RECT, -10000, -10000, 20000, 20000);
  floor.setFill(color(0));
  floor.rotateX(PI/2);
  floor.translate(0, 0, 1001);
  grid = createShape();
  grid.beginShape(LINES);
  grid.stroke(0, 255, 0);
  for (int x = -10000; x < 10000; x += 400) {
    grid.vertex(x, -1000, 10000);
    grid.vertex(x, -1000, -10000);
  }
  for (int z = -10000; z < 10000; z += 400) {
    grid.vertex(10000, -1000, z);
    grid.vertex(-10000, -1000, z);
  }  
  grid.endShape();

  colorMode(HSB);
  for (int i=0; i!=dafjes.length; ++i) {
    dafjes[i] = new DAF();
  }
}

public void draw() {
  background(255);
  pushMatrix();
  translate(width/2-1000, height/2, -1000);
  shape(floor);
  shape(grid);
  popMatrix();
  for (int i=0; i!=dafjes.length; ++i) {
    dafjes[i].move();
    dafjes[i].show();
  }
}

class DAF {
  PShape daf;
  float r, xa, ya, za, ad;
  DAF() {
    daf = loadShape("DAF.obj"); 
    daf.setStroke(color(0));
    daf.rotateX(random(TWO_PI));
    daf.rotateY(random(TWO_PI));
    daf.rotateZ(random(TWO_PI));
    daf.setFill(color(random(255), 255, 255));

    r=random(3000);
    xa=random(TWO_PI);
    ya=random(TWO_PI);
    za=random(TWO_PI);
    ad=random(500, 1000);
  }

  public void show() {
    translate(cos(xa)*r, sin(ya)*r, cos(za)*r);
    shape(daf);
  }

  public void move() {
    xa=(xa+TWO_PI/ad)%TWO_PI;
    ya=(ya+TWO_PI/ad)%TWO_PI;
    za=(za+TWO_PI/ad)%TWO_PI;
  }
}
  public void settings() {  fullScreen(PCardboard.STEREO); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "DAFvr" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
