
package com.raven.chart;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;


public class GranttChartCustom extends JPanel{
    public GranttChartCustom(){
        setLayout(new BorderLayout());
        
    }
   public void setDataset(IntervalCategoryDataset dataset, String title, String x_label, String y_label) {
        removeAll();
        JFreeChart chart = ChartFactory.createGanttChart(title, y_label, x_label, dataset);
        ChartPanel panel = new ChartPanel(chart);
        add(panel);
        repaint();
        revalidate();
    }
}
