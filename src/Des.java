import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Des extends JPanel {
    private Map map;
    private Interaction interaction;
    private int status = 0;
    private Timer timer;
    private int cellSize = 10;  // Size of each cell
    private int padding = 1;    // Padding between cells
    private int xoffset = 0, yoffset = 0;

    public Des(Map map, JFrame frame) {
        this.map = map;
        interaction = new Interaction(getWidth(), getHeight(), map, this);
        addMouseListener(interaction);
        addMouseMotionListener(interaction);
        frame.addKeyListener(interaction);
        setBackground(new Color(50, 50, 50));
        // Initialize the timer with a 100ms delay between each generation
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status != 0) {
                    map.nextGeneration();  // Move to the next generation
                    repaint();  // Refresh the display
                }
            }
        });
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getPadding() {
        return padding;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        if (status != 0) {
            timer.start();  // Start the simulation
        } else {
            timer.stop();   // Stop the simulation
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the width and height of the panel
        int width = getWidth();
        int height = getHeight();

        // Update the interaction object's width and height
        interaction.setWidth(width);
        interaction.setHeight(height);

        for (Cell cell : map.getMap()){
            g.setColor(Color.WHITE);
            g.fillRect(xoffset + cell.getX()*cellSize, yoffset + cell.getY()*cellSize, cellSize, cellSize);
        }
    }

    public int getXoffset() {
        return xoffset;
    }

    public void setXoffset(int xoffset) {
        this.xoffset = xoffset;
    }

    public int getYoffset() {
        return yoffset;
    }

    public void setYoffset(int yoffset) {
        this.yoffset = yoffset;
    }

    // This method can be used to start the simulation via an external call
    public void startSimulation() {
        setStatus(1);  // Set status to 1 (running)
    }

    // This method can be used to stop the simulation
    public void stopSimulation() {
        setStatus(0);  // Set status to 0 (stopped)
    }
}
