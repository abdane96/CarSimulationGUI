import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TrafficLight {
	
	int x, y, width, height;
	Rectangle trafficLight;
	Circle red, green;
	Color color;
	boolean solution;
	String side;
	
	public TrafficLight() {
		trafficLight = new Rectangle();	
		red = new Circle();
		green = new Circle();
	}
	
	public TrafficLight(int x, int y, int width, int height, boolean solution, String side) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solution = solution;
		this.side = side;
		
		trafficLight = new Rectangle();	
		red = new Circle();
		green = new Circle();
	}
	
	public Rectangle drawTL() {
		trafficLight.setX(x);
		trafficLight.setY(y);
		trafficLight.setWidth(width/2);
		trafficLight.setHeight(height/2);
		trafficLight.setFill(Color.TRANSPARENT);
		trafficLight.setStroke(Color.BLACK);
		trafficLight.setStrokeWidth(5);
		
		return trafficLight;
	}
	
	public Circle drawRedCircle() {
		if(width < height) {
			red.setCenterX(trafficLight.getX()+(trafficLight.getHeight()/4));
			red.setCenterY(trafficLight.getY()+(trafficLight.getHeight()/4));
			red.setRadius(trafficLight.getHeight()/7);
			if(solution) {
				red.setFill(Color.TRANSPARENT);
				red.setStroke(Color.BLACK);
				red.setStrokeWidth(2);
				side = "N";
				color = Color.TRANSPARENT;
			}else {			
				red.setFill(Color.RED);
				red.setStroke(Color.BLACK);
				red.setStrokeWidth(2);
				side ="S";
				color = Color.RED;
			}
		}else{
			red.setCenterX(trafficLight.getX()+(trafficLight.getHeight()/2));
			red.setCenterY(trafficLight.getY()+(trafficLight.getHeight()/2));
			red.setRadius(trafficLight.getWidth()/7);
			if(solution) {
				red.setFill(Color.TRANSPARENT);
				red.setStroke(Color.BLACK);
				red.setStrokeWidth(2);
				side = "W";
				color = Color.TRANSPARENT;
			}else {			
				red.setFill(Color.RED);
				red.setStroke(Color.BLACK);
				red.setStrokeWidth(2);
				side = "E";
				color = Color.RED;
			}
		}
		return red;
	}
	
	public Circle drawGreenCircle() {
		if(width < height) {
			green.setCenterX(red.getCenterX());
			green.setCenterY(red.getCenterY()+50);
			green.setRadius(trafficLight.getHeight()/7);
			if(solution) {
				green.setFill(Color.RED);
				green.setStroke(Color.BLACK);
				green.setStrokeWidth(2);
				side = "N";
				color = Color.RED;
			}else {			
				green.setFill(Color.TRANSPARENT);
				green.setStroke(Color.BLACK);
				green.setStrokeWidth(2);
				side = "S";
				color = Color.TRANSPARENT;
			}		
		}else {
			green.setCenterX(red.getCenterX()+50);
			green.setCenterY(red.getCenterY());
			green.setRadius(trafficLight.getWidth()/7);
			if(solution) {
				green.setFill(Color.RED);
				green.setStroke(Color.BLACK);
				green.setStrokeWidth(2);
				side = "W";
				color = Color.RED;
			}else {			
				green.setFill(Color.TRANSPARENT);
				green.setStroke(Color.BLACK);
				green.setStrokeWidth(2);
				side = "E";
				color = Color.TRANSPARENT;
			}
		}
		return green;		
	}
	
	public String getSide() {
		return side;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setGreen() {
		if(solution) {
			red.setFill(Color.GREEN);
			green.setFill(Color.TRANSPARENT);
			color = Color.GREEN;
		}else {
			green.setFill(Color.GREEN);
			red.setFill(Color.TRANSPARENT);
			color = Color.RED;
		}
		
	}
	
	public void setRed() {
		if(solution) {
			green.setFill(Color.RED);
			red.setFill(Color.TRANSPARENT);
			color = Color.RED;
		}else {
			red.setFill(Color.RED);
			green.setFill(Color.TRANSPARENT);
			color = Color.GREEN;
		}
	}
}
