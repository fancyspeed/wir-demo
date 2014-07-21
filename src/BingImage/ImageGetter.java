package BingImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageGetter {
	public Image getImage(String filename) {
		Image image = null;
		  
		//获取指定URL上的图像
		image=Toolkit.getDefaultToolkit().getImage(filename);
		return image;
	}
	
	public Image downloadImage(String surl) {
		Image image = null;
		URL url;
		  
		try
		{
		    //指定要获取的资源的URL
			url=new URL(surl);
			//获取指定URL上的图像
			image=Toolkit.getDefaultToolkit().getImage(url);
		}
		catch(MalformedURLException e)
		{  
		}
		return image;
	}
	
	public double[] getRGBFeature(Image img, int bins) {
		int d = bins*bins*bins;
		int step = 256/bins;
		double[] feat = new double[d];
		int h, w;
		
		img = new ImageIcon(img).getImage(); 
		h = img.getWidth(null);
		w = img.getHeight(null);
		
		BufferedImage bi = new BufferedImage(h, w, BufferedImage.TYPE_INT_RGB); 
        Graphics2D g = bi.createGraphics(); 
        g.drawImage(img, 0, 0, null); 
        
        int[] rgb = new int[3];
        int[] idx = new int[3];
        int pixel, ind;
        for(int i=0; i<d; i++) {
        	feat[i] = 0;
        }
        for(int x=0; x<h; x++) {
        	for(int y=0; y<w; y++) {
        		 
        	     pixel = bi.getRGB(x, y);
        	     rgb = getSplitRGB(pixel);
        	     idx[0] = (int)(rgb[0]/step);
        	     idx[1] = (int)(rgb[1]/step);
        	     idx[2] = (int)(rgb[2]/step);
        	     ind = (idx[0]*bins + idx[1])*bins + idx[2];
        	     feat[ind] ++;
        	}
        }
        for(int i=0; i<d; i++) {
        	feat[i] /= h*w;
//        	System.out.println(feat[i]);
        }
	
		return feat;
	}
	
	public int[] getSplitRGB(int pixel) {
        int[] rgbs = new int[3];
        rgbs[0] = (pixel & 0xff0000) >> 16;
        rgbs[1] = (pixel & 0xff00) >> 8;
        rgbs[2] = (pixel & 0xff);
        return rgbs;
    }
	
	public BufferedImage readImageFromFile(String filename) {
		BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return bi;
	}
	
	 public void writeImageToFile(String filename, BufferedImage img) {
	    try {
	        ImageIO.write(img, "jpg", new File(filename));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	    
	 }
	 
	 public static void main(String args[]) {
		 ImageGetter ig = new ImageGetter();
		 String surl = "http://www.kklinux.com/uploads/090313/2_204213_1.jpg";
		 Image img = ig.downloadImage(surl);
		 ig.getRGBFeature(img, 8);
	 }
}
