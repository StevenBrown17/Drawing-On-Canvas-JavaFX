package application;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;


/**
 * This program captures points given by a user, and puts them on a canvas.
 * The distance between the next point and the first point is calculated and displayed.
 * @author Steve
 *
 */
public class MainController implements Initializable{
	
	@FXML TextField txtX, txtY;
	@FXML Button btnAdd, btnClear;
	@FXML Canvas canvas;
	GraphicsContext gc;
	
	ArrayList<Point> pointList = new ArrayList<Point>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		gc = canvas.getGraphicsContext2D();
	}//end initialize
	
	/**
	 * The original canvas is 500x500. We are displaying to the user that the canvas is 100x100.
	 * We need to convert the point to scale on a 500x500 canvas, instead of the 100x100 the user is expecting.
	 * @param d
	 * @return
	 */
	public double convertDouble(Double d) {	
		return d*5;
	}//end convertDouble
	
	/**
	 * Ads a point to canvas. Checks to see if the user input is valid.
	 * The distance is calculated from the first point, to the next added point.
	 */
	public void addPoint() {
		
		double x;
		double y;
		
		try {		
			x = Double.parseDouble(txtX.getText());
			y = Double.parseDouble(txtY.getText());
			
			if(x > 100 || y > 100) {
				throw new Exception();
			}
		
			Point p = new Point(convertDouble(x),convertDouble(y));//convert the users coordinates to fit the 500x500 canvas
			gc.fillOval(p.getX(), p.getY(), 5, 5);
			gc.strokeText("("+p.getX()/5+","+p.getY()/5+")", p.getX()-20, p.getY()-10, 50);
			
			pointList.add(p);
			
			if(pointList.size() != 1) {
				double x1 = pointList.get(0).getX();
				double y1 = pointList.get(0).getY();
				double x2 = p.getX();
				double y2 = p.getY();
				double distance = p.distance(pointList.get(0));
				DecimalFormat df=new DecimalFormat("0.00");
				String angleFormated = df.format(distance);
				
				gc.strokeLine(x1, y1, x2, y2);
				gc.strokeText(angleFormated+"", (x1+x2)/2+10, (y1+y2)/2);
	
			}		
		}catch(Exception e) {
			System.out.println("invalid entry");
			txtX.clear();
			txtY.clear();
		}//end try/catch
	}//end addPoint

	/**
	 * Clears canvas, textfield, and arraylist
	 */
	public void clearCanvas() {
		 gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		 pointList.clear();
		 txtX.clear();
		 txtY.clear();
	}//end clearCanvas
}//end class