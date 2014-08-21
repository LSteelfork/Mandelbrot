package mandelbrot;

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Window extends Frame
{
	
    private void fileSaver(String format) {
    	JFileChooser fileChooser1 = new JFileChooser();
    	fileChooser1.setDialogTitle("Specify a file to save");   
    	 
    	int userSelection = fileChooser1.showSaveDialog(getParent());
    	 
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    	    File fileToSave = fileChooser1.getSelectedFile();
    	    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
    	    try {
				ImageIO.write(Main.img, format, fileToSave);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
	
  public Window () 
  {
    setTitle("M4nd3lbr07 | click to zoom in, ctrl + click to zoom out"); 
    setSize(600,600);                            
    addWindowListener(new WindowListener());        
    
    MenuBar menuBar;
    Menu menu,submenu;
    MenuItem m1,m2,m3,m4,m5,m6;
    
    menuBar=new MenuBar();
   
    menu=new Menu("Menu");
   
    submenu=new Menu("Save Current view as ...");
   
    m1=new MenuItem("About");
    m1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	JOptionPane.showMessageDialog(getParent(), "The image shows a Mandelbrot-fractal. This app is the byproduct of a summer-camp for nerds.");
        }
      }
    );
    
    
    m2=new MenuItem("Set colors");
    m2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	Object[] possibilities = {"red & blueish", "blueish & red", "green & orange"};
        	Main.colorCombo = (String)JOptionPane.showInputDialog(getParent(), "choose a nice color-combo:",  "Set colors", JOptionPane.PLAIN_MESSAGE,  null, possibilities, "red & blueish");
        	System.out.println("ColorCombo set to " + Main.colorCombo);
        }
      }
    );

    m3=new MenuItem("Number of Iterations"); 
    m3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	int iterations = (Integer.parseInt((String)JOptionPane.showInputDialog(getParent(), "set the number of iterations:",  "Set iterations", JOptionPane.PLAIN_MESSAGE,  null, null, "20")));
        	if (!(iterations > 0 && iterations < 40)) iterations = 20;  // protect app from crashing/dumb user
        	Main.maxIt=iterations;
        	System.out.println("number of iterations set to " + iterations);
        }
      }
    );
    
    m4=new MenuItem("png");
    m4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	fileSaver("png");
        }
      }
    );
    m5=new MenuItem("bmp");
    m5.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	fileSaver("bmp");
        }
      }
    );
    
    m6=new MenuItem("Reset view");
    m6.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	Main.zoom = 1F;
        	Main.x_adj = 0;
        	Main.y_adj = 0;
        	Main.colorCombo = "red & blueish";
        }
      }
    );
    
    menu.add(m1);
    menu.add(m2);
    menu.add(m3);
    menu.add(m6);
   
    submenu.add(m4);
    submenu.add(m5);
   
    menu.add(submenu);
   
    menuBar.add(menu);
    setMenuBar(menuBar);
    
    
    setVisible(true);                           
  }
  

private class WindowListener extends WindowAdapter
  {
    public void windowClosing(WindowEvent e)
    {
      e.getWindow().dispose();                  
      System.exit(0);                           
    }           
  }
}