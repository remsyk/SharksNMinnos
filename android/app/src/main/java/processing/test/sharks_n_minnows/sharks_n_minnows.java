package processing.test.sharks_n_minnows;

import android.content.Context;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;


public class sharks_n_minnows extends PApplet {

	private Context context;
	Vehicle[] vehicles;
	RippleGenerator ripples;
	int numFish;
	Button button;
	float circle_grow = 0;
	float random_num = 200;
	float inneer_circle_size = 500;
	boolean circleOver = false;

	sharks_n_minnows(Context context){
		this.context = context;
	}


	public void setup() {
		ripples = new RippleGenerator(width + 250, height / 2);
		numFish = 10;
		vehicles = new Vehicle[numFish];

		for (int i = 0; i < numFish; i++) {
			vehicles[i] = new Vehicle(random(100, width), random(100, height));
		}
	}

	public void draw() {
		frameRate(30);
		background(0xff200A89);

		button = new Button(); //Start button being called.

		for (Vehicle v : vehicles) {
			v.run();
		}
		ripples.run();

	}

	class Button {
		GameHandler gameHandler = new GameHandler(context);

		Button() {
			update(mouseX, mouseY);

			circle_grow += 5;
			fill(0xff200A89, 50);
			ellipse(width / 2, height / 2, circle_grow + 450, circle_grow + 450);
			if (circle_grow == 450) {
				circle_grow = 0;
			}

			pushStyle();
			fill(0xff200A89);
			strokeWeight(5);
			ellipse(width / 2, height / 2, inneer_circle_size, inneer_circle_size);
			popStyle();

			if (circleOver && mousePressed) {
				random_num = (int) random(100);
				inneer_circle_size = 570;
				gameHandler.buttonPressed();
			} else {
				inneer_circle_size = 500;
			}

			if (random_num == 200) {
				pushStyle();
				fill(0xffFFFFFF);
				textSize(70);
				text("Start", (width / 2) - 50, (height / 2) + 10);
				popStyle();
			} else {


				if (random_num % 10 == 0) {
					pushStyle();
					fill(0xffFFFFFF);
					textSize(70);
					text("Shark", (width / 2) - 50, (height / 2) + 10);
					popStyle();
					//status = true;
					//gps_on = true;
				} else {
					pushStyle();
					fill(0xffFFFFFF);
					textSize(70);
					text("Minnow", (width / 2) - 50, (height / 2) + 10);
					popStyle();
					//gps_on = true;
					//status = false;
				}
			}
		}

		public void update(int x, int y) {
			if (overCircle(width / 2, height / 2, 500)) {
				circleOver = true;
			} else {
				circleOver = false;
			}
		}

		public boolean overCircle(int x, int y, int diameter) {
			float disX = x - mouseX;
			float disY = y - mouseY;
			if (sqrt(sq(disX) + sq(disY)) < diameter / 2) {
				return true;
			} else {
				return false;
			}
		}
	}

	class Fish {
		int numSegs;
		float angle, size, amplitude, period, y, y2, xWdth;
		PVector[] segPositions;
		Vector2D vec;
		PVector dxdy;

		Fish() {
			xWdth = 2;
			size = 8;
			numSegs = 20;
			vec = new Vector2D();
			segPositions = new PVector[numSegs];
			for (int i = 0; i < numSegs; i++) {
				segPositions[i] = new PVector(0, 0);
			}
		}


		public void display(PVector pos, float speed) {
			moveAndDisplaySegs(0, pos, speed);
			for (int i = 0; i < numSegs - 1; i++) {
				moveAndDisplaySegs(i + 1, segPositions[i], speed);
			}
		}

		// this approach to vector projection from John Resig's
		//    'Snake' on the Processing.js 'Learning' page
		public void moveAndDisplaySegs(int i, PVector pos, float speed) {
			dxdy = PVector.sub(pos, segPositions[i]);
			angle = dxdy.heading2D();
			segPositions[i] = PVector.sub(pos, vec.radDist(angle, size));

			pushMatrix();
			translate(segPositions[i].x, segPositions[i].y);
			rotate(angle);
			period = map(speed, .5f, 7, 80, 5);

			fill(255);
			stroke(255);
			strokeWeight(1);
			rectMode(CENTER);
			ellipseMode(CENTER);

			switch (i) {
				case 0:
					arc(-4, 0, 45, 20, radians(-70), radians(70));
					fill(0xff200A89);
					ellipse(6, -5, 7, 5);
					ellipse(6, 5, 7, 5);
					break;
				case 1:
					line(0, 0, size, 0);
					rect(0, 0, xWdth, 22);
					break;
				case 2:
					line(0, 0, size, 0);
					rect(0, 0, xWdth, 25);
					break;
				case 3:
					line(0, 0, size, 0);
					rect(0, 0, xWdth, 27);
					break;
				case 4:
					line(0, 0, size, 0);
					rect(0, 0, xWdth, 27);
					break;
				case 5:
					line(0, 0, size, 0);
					rect(0, 0, xWdth, 26);
					break;
				case 6:
					line(0, 0, size, 0);
					rect(0, 0, xWdth, 24);
					break;
				case 7:
					line(0, 0, size, 0);
					rect(0, 0, xWdth, 22);
					break;
				case 8:
					line(0, 0, size, 0);
					rect(0, 0, xWdth, 20);
					break;
				case 9:
					line(0, 0, size, 0);
					rect(0, 0, xWdth, 18);
					break;
				// increase the amplitude,
				// and increment the oscillation cycle position,
				// as we move along the tail
				case 10:
					amplitude = .5f;
					//            or  sin(someIncrementingNumber)
					y = amplitude * sin(TWO_PI * (frameCount / period));
					line(0, y, size, 0);
					rect(0, y, xWdth, 16);
					break;
				case 11:
					amplitude = 1;
					y2 = amplitude * sin(TWO_PI * (0.01f + (frameCount / period)));
					line(0, y2, size, y);
					rect(0, y2, xWdth, 14);
					break;
				case 12:
					amplitude = 2;
					y = amplitude * sin(TWO_PI * (0.02f + (frameCount / period)));
					line(0, y, size, y2);
					rect(0, y, xWdth, 12);
					break;
				case 13:
					amplitude = 3;
					y2 = amplitude * sin(TWO_PI * (0.03f + (frameCount / period)));
					line(0, y2, size, y);
					rect(0, y2, xWdth, 10);
					break;
				case 14:
					amplitude = 4;
					y = amplitude * sin(TWO_PI * (0.04f + (frameCount / period)));
					line(0, y, size, y2);
					rect(0, y, xWdth, 8);
					break;
				case 15:
					amplitude = 5;
					y2 = amplitude * sin(TWO_PI * (0.05f + (frameCount / period)));
					line(0, y2, size, y);
					rect(0, y2, xWdth, 6);
					break;
				case 16:
					amplitude = 6;
					y = amplitude * sin(TWO_PI * (0.06f + (frameCount / period)));
					line(0, y, size, y2);
					rect(0, y, xWdth, 4);
					break;
				case 17:
					amplitude = 7;
					y2 = amplitude * sin(TWO_PI * (0.07f + (frameCount / period)));
					line(0, y2, size, y);
					rect(0, y2, xWdth, 3);
					break;
				case 18:
					amplitude = 8;
					y = amplitude * sin(TWO_PI * (0.08f + (frameCount / period)));
					line(0, y, size, y2);
					rect(0, y, xWdth, 2);
					break;
				case 19:
					stroke(255);
					strokeWeight(1);
					amplitude = 14;
					y2 = amplitude * sin(TWO_PI * (0.09f + (frameCount / period)));
					line(-12, y2, 8, y);
					break;
			}
			popMatrix();
		}
	}

	class RippleGenerator {
		ArrayList<Particle> particles;
		PVector pos;

		RippleGenerator(float x, float y) {
			pos = new PVector(x, y);
			particles = new ArrayList<Particle>();
		}

		public void run() {
			// limit particle generation frequency
			if (frameCount % 100 == 0) {
				particles.add(new Particle(pos));
			}
			for (int i = particles.size() - 1; i >= 0; i--) {
				Particle p = particles.get(i);
				p.run();
				if (p.isDead()) {
					particles.remove(i);
				}
			}
		}
	}

	class Particle {
		PVector pos;
		PVector vel;
		PVector accel;
		float lifespan;
		int c;
		float diam;

		Particle(PVector l) {
			accel = new PVector();
			vel = new PVector();

			pos = l.get();
			lifespan = 255.0f;
			c = color(0xffE1F6F7);
			diam = 500;
		}

		public void run() {
			move();
			display();
		}

		public void move() {
			vel.add(accel);
			pos.add(vel);
			accel.mult(0);
			lifespan -= .2f;
			diam += 1;
		}

		public void display() {
			noFill();
			stroke(c, lifespan);
			strokeWeight(1);
			ellipse(pos.x, pos.y, diam, diam);
		}

		public boolean isDead() {
			if (lifespan < 0.0f) {
				return true;
			} else {
				return false;
			}
		}
	}

	static class Vector2D {
		public static PVector degDist(float angDeg, float distance) {
			float theta = radians(angDeg);
			float dx = distance * cos(theta);
			float dy = distance * sin(theta);
			PVector v = new PVector(dx, dy);
			return v;
		}

		public static PVector radDist(float angRad, float distance) {
			float dx = distance * cos(angRad);
			float dy = distance * sin(angRad);
			PVector v = new PVector(dx, dy);
			return v;
		}
	}

	class Vehicle {
		Vector2D vec;
		Fish fish;
		PVector vel, pos;
		int randNum;
		float speed;

		Vehicle(float x, float y) {
			vec = new Vector2D();
			fish = new Fish();
			pos = new PVector(x, y);
			vel = vec.degDist(random(-20, 20), random(.5f, 7));
		}


		public void dirChangeRandom() {
			randNum = PApplet.parseInt(random(50, 200));
			if (frameCount % randNum == 0) {
				vel.y = random(-3, 3);
				vel.normalize();
				if (speed < 3.5f) vel.mult(random(.3f, 7));
				else vel.mult(random(1.5f, 7));
			}
		}

		public void update() {
			speed = vel.mag();
			pos.add(vel);
		}

		public void display() {
			fish.display(pos, speed);
		}

		public void boundsCheck() {
			if (pos.x > width + 220) {
				pos.x = -250;
				if (vel.y == 0) vel.y = .1f; // stop fish swimming backwards
			}
			if (pos.y > height + 200) {
				vel.y = -1;
			}
			if (pos.y < -200) {
				vel.y = 1;
			}
		}

		public void run() {
			update();
			boundsCheck();
			dirChangeRandom();
			display();
		}
	}

	public void settings() {
		size(1000, 2000);
	}

}
