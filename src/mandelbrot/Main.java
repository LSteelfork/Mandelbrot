package mandelbrot;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

	static String colorCombo = "red & blueish"; //TODO: implement nicer way to select colors
	static int maxIt = 20;
	static BufferedImage img;
	
	
	static float zoom = 1F; 
	static int x_adj = 0; // lets you zoom into a *specific* area of the mandelbrot
	static int y_adj = 0;

	static boolean ctrlDown = false;

	public static void main(String[] args) throws IOException, InterruptedException {

		KeyListener k = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_CONTROL)
					System.out.println("*ctrl pressed*");
				ctrlDown = true;
			}

			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_CONTROL)
					System.out.println("*ctrl released*");
				ctrlDown = false;

			}
		};

		MouseListener a = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("*click*");
				if (ctrlDown == false) {
					zoom = zoom * 1.05F; 
					System.out.println("zoom in");
				}
				else {
					zoom = zoom / 1.05F;
					System.out.println("zoom out");
				}

				x_adj += 300 - e.getX(); // 300 because image is 600*600
				y_adj += 300 - e.getY();
			}
		};


		Frame wind = new Window();
		wind.addMouseListener(a);
		wind.addKeyListener(k);
		
		while (true){
			img = makeImage(600, 600, zoom, x_adj, y_adj, maxIt);
			Thread.sleep(100);
			Graphics g = wind.getGraphics();
			g.drawImage(img, 0, 0, null);
			
			wind.paint(g);
		}

	}
	


	private static BufferedImage makeImage(int width, int heigth, float zoom, int x_adj, int y_adj, int maxIterations){

		BufferedImage img = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
			int rgb, rgb2;
			
		switch (colorCombo) {
			case "blueish & red":
				rgb = 0xFF00FFFF;
				rgb2 = 0x00AA0000;
				break;
				
			case "green & orange":
				rgb = 0xFF00AA00;
				rgb2 = 0x00330005;
				break;
			default:
				rgb = 0xFFAA0000;
				rgb2 = 0x00001122;
				break;
			}
		
		for (double x = 0; x < width; x+=1){
			for (double y = 0; y < heigth; y+=1){

				double iterations = rekursionsRegel(((x-width/2-x_adj)/(width*0.5))/zoom, ((y-heigth/2*1.5-y_adj)/(heigth*0.5))/zoom, maxIterations);

				if (iterations == maxIterations) img.setRGB((int)x,(int)y, rgb);
				else img.setRGB((int)x,(int)y, 0xFF000000+rgb2*(int)iterations);

			}

		}
		return img;
	}



	private static double  rekursionsRegel(double i, double r, int maxIterations) // if this returns maxIterations for a pixel, it's part of the fractal
	{
		double rad = 4;

		double i_z = 0;
		double r_z = 0;

		double iter = 0;

		while (iter < maxIterations && Math.sqrt(i_z*i_z + r_z*r_z) < rad) 
		{

			double tmpRz = r_z * r_z - i_z * i_z + r;

			i_z = 2*r_z*i_z+i;

			r_z = tmpRz;

			iter++; 
		}
		return iter;
	}

}
