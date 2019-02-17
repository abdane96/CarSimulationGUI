import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class Car {
	int size;
	int xposition;
	int yposition;
	int position;
	TrafficLight TrfcLight;
	String side;
	Rectangle car;
	int lane;
	int speed;
	int collision;
	Image img;// = new Image("001.jpg");
	
	public Car() {
		car = new Rectangle();
		//size = ThreadLocalRandom.current().nextInt(80, 90 + 1);
		size = 85;
		position = ThreadLocalRandom.current().nextInt(0, 3 + 1);
		//speed = ThreadLocalRandom.current().nextInt(1, 4);
		speed = 2;
		//position = 2;
		switch(position) {
		
			case 0: side = "N";
					break;
			case 1: side = "S";
					break;
			case 2: side = "W";
					break;
			case 3: side = "E";
					break;
			default: side = "N";
					break;
		}
		if(side =="N") {
			lane = ThreadLocalRandom.current().nextInt(0,3);
			switch(lane) {
				case 0: xposition = 270;
						break;
				case 1: xposition = 310;
						break;
				case 2: xposition = 350;
						break;
				default: xposition = 310;
			}
			//xposition = ThreadLocalRandom.current().nextInt(320-size, 400 -size + 1);
			yposition = 0;
			collision = yposition;
			img = new Image("NorthCar.png");
		}else if(side =="S"){
			lane = ThreadLocalRandom.current().nextInt(0,3);
			switch(lane) {
				case 0: xposition = 410;
						break;
				case 1: xposition = 450;
						break;
				case 2: xposition = 490;
						break;
				default: xposition = 450;
			}
			//xposition = ThreadLocalRandom.current().nextInt(420-size, 500 -size + 1);
			yposition = 800-size/2;
			collision = yposition+15;
			img = new Image("SouthCar.png");
		}else if(side == "W") {
			lane = ThreadLocalRandom.current().nextInt(0,3);
			switch(lane) {
				case 0: yposition = 410;
						break;
				case 1: yposition = 450;
						break;
				case 2: yposition = 490;
						break;
				default: yposition = 450;
			}
			xposition = 0;
			collision = xposition-15;
			img = new Image("EastCar.png");
			//yposition = ThreadLocalRandom.current().nextInt(420-size, 500 -size + 1);
		}else {
			lane = ThreadLocalRandom.current().nextInt(0,3);
			switch(lane) {
				case 0: yposition = 270;
						break;
				case 1: yposition = 310;
						break;
				case 2: yposition = 350;
						break;
				default: yposition = 310;
			}
			xposition = 800-size/2;
			collision = xposition+15;
			img = new Image("WestCar.png");
			//yposition = ThreadLocalRandom.current().nextInt(320-size, 400 -size + 1);
		}
	}
	
	
	public Rectangle drawCar() {
		car.setX(xposition);
		car.setY(yposition);
		car.setWidth(size/2);
		car.setHeight(size/2);
		car.setFill(new ImagePattern(img));
		return car;
	}
	
	public void stopCar() {
		car.setX(xposition);
		car.setY(yposition);
	}
	
	public int getLane() {
		return lane;
	}
	
	public int getXPosition() {
		return xposition;
	}
	
	public int getYPosition() {
		return yposition;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getSide() {
		return side;
	}
	
	public int getHeight() {
		return (int) car.getHeight();
	}
	
	public int getWidth() {
		return (int) car.getWidth();
	}
	
	public void deleteCar() {
		car.setWidth(0);
		car.setHeight(0);
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
	public int getCollision() {
		return collision;
	}
	
	public void incrementXPosition() {
		xposition+=speed;
	}
	
	public void incrementYPosition() {
		yposition+=speed;
	}
	
	public void decrementXPosition() {
		xposition-=speed;
	}
	
	public void decrementYPosition() {
		yposition-=speed;
	}
}
