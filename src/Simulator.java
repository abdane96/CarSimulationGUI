import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Simulator extends Application {
	ArrayList<Car> cars;
	TrafficLight[] trafficLights;
	TrafficLight northTrafficLight, westTrafficLight, eastTrafficLight, southTrafficLight;
	int northCount, southCount, eastCount, westCount;
	long startTime = System.nanoTime() / 1000000000;
	long waitStartTime;
	long greenTimeSN, greenTimeWE;
	boolean carStopped, SNRedTrue, WERedTrue;
	double timeButtonClicked;
	Boolean cycle;
	Pane root;
	Canvas canvas;
	Button start;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Simulator");

		root = new Pane();
		cars = new ArrayList<Car>();
		canvas = new Canvas(800, 800);
		trafficLights = new TrafficLight[4];
		start = new Button("Start");

		start.setLayoutX(800);
		start.setLayoutY(200);

		SNRedTrue = false;
		WERedTrue = true;
		cycle = false;

		/*
		 * Tooltip mousePositionToolTip = new Tooltip(""); root.setOnMouseMoved(new
		 * EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent event) { String msg = "(x: " +
		 * event.getX() + ", y: " + event.getY() + ")\n(sceneX: " + event.getSceneX() +
		 * ", sceneY: " + event.getSceneY() + ")\n(screenX: " + event.getScreenX() +
		 * ", screenY: " + event.getScreenY() + ")"; mousePositionToolTip.setText(msg);
		 * 
		 * Node node = (Node) event.getSource(); mousePositionToolTip.show(node,
		 * event.getScreenX() + 50, event.getScreenY()); }
		 * 
		 * });
		 */

		// TrafficLights
		trafficLights[0] = new TrafficLight(185, 135, 100, 200, true, "N");
		trafficLights[1] = new TrafficLight(135, 565, 200, 100, true, "W");
		trafficLights[2] = new TrafficLight(565, 185, 200, 100, false, "E");
		trafficLights[3] = new TrafficLight(565, 565, 100, 200, false, "S");

		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawShapes(gc);
		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				startTime = System.nanoTime() / 1000000000;
				Timeline tl = new Timeline(new KeyFrame(Duration.millis(1500), ae -> {
					Car car = new Car();
					cars.add(car);
					root.getChildren().add(car.drawCar());
					String side = car.getSide();
					if (side == "N") {
						northCount++;
					} else if (side == "S") {
						southCount++;
					} else if (side == "W") {
						westCount++;
					} else if (side == "E") {
						eastCount++;
					}
				}));
				tl.setCycleCount(200000000);
				tl.play();
			}
		});
		update();
		root.getChildren().add(canvas);

		Scene scene = new Scene(root, 900, 800);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void drawShapes(GraphicsContext gc) {

		// Draw Intersection
		gc.setLineWidth(10.0);

		gc.strokeLine(250, 0, 250, 250);
		gc.strokeLine(0, 250, 250, 250);

		gc.strokeLine(550, 0, 550, 250);
		gc.strokeLine(550, 250, 800, 250);

		gc.strokeLine(250, 550, 250, 800);
		gc.strokeLine(250, 550, 0, 550);

		gc.strokeLine(550, 550, 550, 800);
		gc.strokeLine(550, 550, 800, 550);

		Line line1 = new Line(400, 0, 400, 250);
		line1.getStrokeDashArray().addAll(25d, 10d);

		Line line2 = new Line(400, 550, 400, 800);
		line2.getStrokeDashArray().addAll(25d, 10d);

		Line line3 = new Line(0, 400, 250, 400);
		line3.getStrokeDashArray().addAll(25d, 10d);

		Line line4 = new Line(550, 400, 800, 400);
		line4.getStrokeDashArray().addAll(25d, 10d);

		root.getChildren().addAll(line1, line2, line3, line4, start);

		// Draw Cars
		for (int i = 0; i < cars.size(); i++) {
			root.getChildren().add(cars.get(i).drawCar());
		}

		// Draw Traffic light
		for (int i = 0; i < trafficLights.length; i++) {
			root.getChildren().add(trafficLights[i].drawTL());
			root.getChildren().add(trafficLights[i].drawRedCircle());
			root.getChildren().add(trafficLights[i].drawGreenCircle());
		}

	}

	private void update() {

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub

				for (int i = 0; i < cars.size(); i++) {
					String side = cars.get(i).getSide();
					if (side == "N") {
						checkCollision(side, cars.get(i));
						if (cars.get(i).getYPosition() > 224 && cars.get(i).getYPosition() < 229
								&& trafficLights[0].getColor() == Color.RED) {
							cars.get(i).stopCar();
						} else {
							cars.get(i).incrementYPosition();
							cars.get(i).drawCar();
							checkRemoveCar(cars.get(i));
						}
					} else if (side == "S") {
						checkCollision(side, cars.get(i));
						if (cars.get(i).getYPosition() > 550 && cars.get(i).getYPosition() < 555
								&& trafficLights[3].getColor() != Color.RED) {
							cars.get(i).stopCar();
						} else {
							cars.get(i).decrementYPosition();
							cars.get(i).drawCar();
							checkRemoveCar(cars.get(i));
						}
					} else if (side == "W") {
						checkCollision(side, cars.get(i));
						if (cars.get(i).getXPosition() > 224 && cars.get(i).getXPosition() < 229
								&& trafficLights[1].getColor() == Color.RED) {
							cars.get(i).stopCar();
						} else {
							cars.get(i).incrementXPosition();
							cars.get(i).drawCar();
							checkRemoveCar(cars.get(i));
						}
					} else {
						checkCollision(side, cars.get(i));
						if (cars.get(i).getXPosition() > 550 && cars.get(i).getXPosition() < 555
								&& trafficLights[2].getColor() != Color.RED) {
							cars.get(i).stopCar();
						} else {
							cars.get(i).decrementXPosition();
							cars.get(i).drawCar();
							checkRemoveCar(cars.get(i));
						}
					}
					// System.out.println("Y Position is: "+cars.get(i).getYPosition());
					// System.out.println("X Position is: "+cars.get(i).getXPosition());
				}

				Logic();

			}
		};
		timer.start();
	}

	public void Logic() {
		// int middle = checkCarsInMiddle();
		// System.out.println(seconds);
		// boolean wait = true;
		// System.out.println(seconds);
		long seconds = System.nanoTime() / 1000000000 - startTime;
		long yellowTime = 3;
		if (seconds > yellowTime) { // After 7 seconds, the first cycle starts
			
			if (!cycle) {
				
				greenTimeSN = 2 * (southCount + northCount);
				greenTimeWE = 2 * (westCount + eastCount);
//				if (greenTimeSN < 10) {
//					if (greenTimeSN == 0) {
//						SNRedTrue = true;
//						WERedTrue = false;
//					} else {
//						greenTimeSN = 10;
//					}
//				}
//				if (greenTimeSN > 60) {
//					greenTimeSN = 60;
//				}
//				if (greenTimeWE < 10) {
//					if (greenTimeWE == 0) {
//						WERedTrue = true;
//						SNRedTrue = false;
//					} else {
//						greenTimeWE = 10;
//					}
//				}
//				if (greenTimeWE > 60) {
//					greenTimeWE = 60;
//				}
				cycle = true;
			} else {
				// SN first then WE // FIX THE CASE WHEN greenTimeSN IS ZERO
				if (!SNRedTrue) {
					greenTimeWE = 2*(westCount+eastCount);
					// while SNRedTrue is false (SN is green), keep into account of the WE count
					SNGreen(); // Set SN to green
					if (seconds - yellowTime >= greenTimeSN) { // Set SN Red after the time for green (2*(southCount+northCount))
						SNRedTrue = true;
						WERedTrue = false;
						waitStartTime = System.nanoTime() / 1000000000;
						SNRed();
					}
					System.out.println("SN is " + greenTimeSN);
					System.out.println("SN is " + (seconds - yellowTime));

				} else if (!WERedTrue) {
					// greenTimeSN = 2*(southCount+northCount);
					long waitEndTime = System.nanoTime() / 1000000000;
					if (waitEndTime - waitStartTime >= 3) {
						int greenTimeSN2 = 2*(southCount+northCount);
						//greenTimeSN is taken into account below, if it keeps getting updated, we will never reach >= greenTimeWE
						WEGreen();
						if (seconds - yellowTime - greenTimeSN - 3 >= greenTimeWE) {
							WERed();
							if (seconds - yellowTime - greenTimeSN - 3 - greenTimeWE >= 0) {
								WERedTrue = true;
								SNRedTrue = false;
								cycle = false;
								startTime = System.nanoTime() / 1000000000;
								greenTimeSN = greenTimeSN2;
								//System.out.println(greenTimeSN);
							}
						}
					}
					System.out.println("WE is " + greenTimeWE);
					System.out.println("WE is " + (seconds - yellowTime-greenTimeSN-3));
					//System.out.println("GreenTime is " + greenTimeWE);
					//System.out.println("Time left to go red is " + (seconds - 7 - greenTimeSN - 3));
					//System.out.println("time left to go to SN " + (seconds - 7 - greenTimeSN - 3 - greenTimeWE));

				}
			}

		}
	}
	

	public int checkCarsInMiddle() {
		int count = 0;
		for (Car midCar : cars) {
			// System.out.println(midCar.getXPosition());
			if (midCar.getXPosition() > 270 && midCar.getXPosition() < 550 && midCar.getYPosition() > 270
					&& midCar.getYPosition() < 550) {
				count++;
				// return true;
			}
		}
		// System.out.println(count);
		return count;
	}

	public void checkCollision(String side, Car car) {
		for (Car testCar : cars) {
			switch (side) {
			case "N":
				if (testCar != car && testCar.getSide() == side && testCar.getLane() == car.getLane()) {
					if (car.getYPosition() == testCar.getYPosition() - testCar.getHeight() - 10) {
						car.decrementYPosition();
					}
				}
				break;
			case "S":
				if (testCar != car && testCar.getSide() == side && testCar.getLane() == car.getLane()) {
					if (car.getYPosition() == testCar.getYPosition() + testCar.getHeight() + 10) {
						car.incrementYPosition();
					}
				}
				break;
			case "W":
				if (testCar != car && testCar.getSide() == side && testCar.getLane() == car.getLane()) {
					if (car.getXPosition() == testCar.getXPosition() - testCar.getWidth() - 10) {
						car.decrementXPosition();
					}
				}
				break;
			case "E":
				if (testCar != car && testCar.getSide() == side && testCar.getLane() == car.getLane()) {
					if (car.getXPosition() == testCar.getXPosition() + testCar.getHeight() + 10) {
						car.incrementXPosition();
					}
				}
				break;
			}
		}
	}

	public void checkRemoveCar(Car car) {
		String side = car.getSide();
		if (side == "N" && car.getYPosition() > 800) {
			car.deleteCar();
			cars.remove(car);
			northCount--;
		} else if (side == "S" && car.getYPosition() < 0) {
			car.deleteCar();
			cars.remove(car);
			southCount--;
		} else if (side == "W" && car.getXPosition() > 800) {
			car.deleteCar();
			cars.remove(car);
			westCount--;
		} else if (side == "E" && car.getXPosition() < 0) {
			car.deleteCar();
			cars.remove(car);
			eastCount--;
		}
	}

	public void SNRed() {
		trafficLights[0].setRed();
		trafficLights[3].setRed();
	}

	public void WERed() {
		trafficLights[1].setRed();
		trafficLights[2].setRed();
	}

	public void SNGreen() {
		trafficLights[0].setGreen();
		trafficLights[3].setGreen();
	}

	public void WEGreen() {
		trafficLights[1].setGreen();
		trafficLights[2].setGreen();
	}

}
