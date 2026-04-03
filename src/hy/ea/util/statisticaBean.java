package hy.ea.util;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.ui.TextAnchor;


public class statisticaBean {
	
	 public static void resetPiePlot(PiePlot plot){
	    	String unitSytle = "{0}={1}({2})";
	    	
	    	plot.setNoDataMessage("无对应的数据，请重新查询。");
	    	plot.setNoDataMessagePaint(Color.red);
	    	plot.setLabelFont(new Font("宋体",Font.BOLD,20));
	    	//指定轮廓的厚度
	    	plot.setOutlineStroke(new BasicStroke(0));
	    	
	    	plot.setStartAngle(90);
	    	
	    	plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
	    	unitSytle,NumberFormat.getNumberInstance(),
	    	new DecimalFormat("0.00%")));
	    	
	    	plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
	    			unitSytle,NumberFormat.getNumberInstance(),
	    	    	new DecimalFormat("0.00%")));
	    }
	
	 public static void resetPiePlotByzz(JFreeChart chart){
		    chart.getTitle().setFont(new Font("宋体", Font.BOLD,15));
		    chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 15));   
		    CategoryPlot plot = chart.getCategoryPlot();    //获得图表区域对象
		    CategoryAxis domainAxis = plot.getDomainAxis();
		    domainAxis.setVisible(true);
		    plot.setDomainAxis(domainAxis);
		   
		    NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();   
		    numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//设置y轴显示整数数据  
	        domainAxis.setTickLabelFont(new Font("宋体",Font.PLAIN,12));
	       /*------设置X轴的标题文字------------*/
	        domainAxis.setLabelFont(new Font("宋体",Font.PLAIN,15));    
	        ValueAxis rAxis = plot.getRangeAxis();
		   
	       /*------设置Y轴坐标上的文字-----------*/
	        rAxis.setTickLabelFont(new Font("宋体",Font.PLAIN,15));
	       /*------设置Y轴的标题文字------------*/
	        rAxis.setLabelFont(new Font("黑体",Font.PLAIN,15)); 
	        
	        rAxis.setLowerMargin(1.5);
	        plot.setRangeAxis(rAxis);
	        BarRenderer3D renderer = new BarRenderer3D();
	        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	        renderer.setBaseItemLabelsVisible(true);
	        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
	        renderer.setItemLabelAnchorOffset(10D);
	        plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
	        plot.setRenderer(renderer);   

	        
	        
	    }
	 
	 
	 
	
}
