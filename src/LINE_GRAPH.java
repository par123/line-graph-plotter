
import java.awt.Color;
import java.util.Arrays;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author partho
 */
public class LINE_GRAPH extends JFrame{
    public LINE_GRAPH(String graphTitle,String lineName,String xLabel,String yLabel,double x[],double y[]){
        super(graphTitle);
        XYDataset dataset=createDataset(x,y,lineName);
        JFreeChart lineChart=ChartFactory.createXYLineChart(graphTitle,xLabel,yLabel,dataset,PlotOrientation.VERTICAL,true,true,false);
        ChartPanel chartPanel=new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800,600));
        setContentPane(chartPanel);
        
        
                /* >>>>> added later */
                XYPlot plot = lineChart.getXYPlot();
  plot.setDataset(0, dataset);
  plot.setDataset(1, dataset);
  XYLineAndShapeRenderer renderer0 = new XYLineAndShapeRenderer(); 
  XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(); 
  plot.setRenderer(0, renderer0); 
  plot.setRenderer(1, renderer1); 
  //plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.YELLOW); 
  plot.getRendererForDataset(plot.getDataset(1)).setSeriesPaint(0, Color.YELLOW);
  
  /* added later <<<<<<< */
        
        /*
        XYItemRenderer renderer=lineChart.getXYPlot().getRenderer();
        renderer.setSeriesPaint(0,Color.YELLOW);
        renderer.setSeriesPaint(1,Color.YELLOW);
        renderer.setSeriesPaint(2,Color.BLUE);
        */
    }

    private XYDataset createDataset(double[] x, double[] y,String lineName) {
        XYSeriesCollection dataset=new XYSeriesCollection();
                       
        XYSeries graphDataSeries=new XYSeries(lineName);
        int l=x.length;
        for(int i=0;i<l;i++){
            graphDataSeries.add(x[i],y[i]);
        }
        
        Arrays.sort(x);
        Arrays.sort(y);
        
        XYSeries XaxisLineSeries=new XYSeries("X axis");
        XaxisLineSeries.add(modulus(x[x.length-1])+1,0);
        XaxisLineSeries.add(-modulus(x[x.length-1])-1,0);
        
        XYSeries YaxisLineSeries=new XYSeries("Y axis");
        YaxisLineSeries.add(0,modulus(y[y.length-1])+1);
        YaxisLineSeries.add(0,-modulus(y[y.length-1])-1);
        
        dataset.addSeries(XaxisLineSeries);
        dataset.addSeries(YaxisLineSeries);
        dataset.addSeries(graphDataSeries);        
        return dataset;
    }
    
    private double modulus(double n){
        return (n==0)? 0: (n>0)? n:-n;
    }
}
