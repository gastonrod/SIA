package engine;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public final class Plotter extends ApplicationFrame {

    private DefaultCategoryDataset dataset;

    public Plotter() {
        super("Genetic Algorithm");
        dataset = new DefaultCategoryDataset();
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Fitness",
                "Generation","Fitness",
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize( new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    public void addBest(int generation, double fitness) {
        addValue("Best", generation, fitness);
    }

    public void addAverage(int generation, double fitness) {
        addValue("Average", generation, fitness);
    }

    public void addWorst(int generation, double fitness) {
        addValue( "Worst", generation, fitness);
    }

    private void addValue(String key, int generation, double fitness) {
        dataset.addValue(fitness, key, Integer.valueOf(generation));
    }

    public void resetDataset() {
        dataset.clear();
    }

    public void plot() {
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }

}
