package BigDataSecondAssignment.SpamDetector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class BarChart_AWT extends ApplicationFrame
{
   public BarChart_AWT( String applicationTitle  )
   {
      super( applicationTitle );        
      
   }
   public  void setChartSetting(CategoryDataset dataset,String chartTitle,String xAxis,String yAxis ){
	   JFreeChart barChart = ChartFactory.createBarChart(
		         chartTitle,           
		         xAxis,            
		         yAxis,            
		         dataset,          
		         PlotOrientation.VERTICAL,           
		         true, true, false);
		         
		      ChartPanel chartPanel = new ChartPanel( barChart );        
		      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
		      setContentPane( chartPanel ); 
   }
   public static CategoryDataset createDatasetError(Double[] errors )
   {
      final String h1 = "HyperPlane 1";        
      final String h2 = "HyperPlane 2";        
      final String h4 = "HyperPlane 4";
      final String h8 = "HyperPlane 8";        
      final String h16 = "HyperPlane 16";        
      final String h32 = "HyperPlane 32";
       
           
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  
      dataset.addValue( errors[0], h1 , "error" );
      dataset.addValue( errors[1], h2 , "error" );
      dataset.addValue( errors[2], h4 , "error" );
      dataset.addValue( errors[3], h8 , "error" );
      dataset.addValue( errors[4], h16 , "error" );
      dataset.addValue( errors[5], h32 , "error" );
      
              

      return dataset; 
   }
   public static CategoryDataset createDatasetEmailLabels(int[] labels )
   {
      final String label1 = "Correct hams";        
      final String label2 = "Correct  Spams";        
      final String label3 = "Hams labeled as Spam";
      final String label4 = "Spams labeld as Ham ";        
      
      
           
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  
      dataset.addValue( labels[0], label1 , "Email-labels" );
      dataset.addValue( labels[1], label2 , "Email-labels" );
      dataset.addValue( labels[2], label3 , "Email-labels" );
      dataset.addValue( labels[3], label4 , "Email-labels" );
     
      
              

      return dataset; 
   }
  
  }