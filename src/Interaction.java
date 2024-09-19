import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class Interaction implements MouseListener, MouseMotionListener, KeyListener {
    private int width, height;
    private Map map;
    private Des des;
    private Set<String> visitedPoints;

    public Interaction(int width, int height, Map map, Des des) {
        this.width = width;
        this.height = height;
        this.map = map;
        this.des = des;
        this.visitedPoints = new HashSet<>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        togglePixel(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        visitedPoints.clear();  // Clear visited points when a new drag starts
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Could implement functionality if needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        togglePixel(e);  // Toggle pixel during dragging
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Implement if needed
    }

    private void togglePixel(MouseEvent e) {
        int cellSize = des.getCellSize();
        int xoffset = des.getXoffset();
        int yoffset = des.getYoffset();

        // Calculer les coordonnées x et y en tenant compte des décalages
        int x = (e.getX() - xoffset) / cellSize;
        int y = (e.getY() - yoffset) / cellSize;

        // Assurez-vous que les coordonnées sont correctement calculées
        // Pour les coordonnées négatives, le calcul précédent est suffisant

        // Créer une clé unique pour le point visité
        String pointKey = x + "," + y;

        // Ajouter le pixel uniquement s'il n'a pas encore été visité
        if (!visitedPoints.contains(pointKey)) {
            map.addPixel(x, y);
            visitedPoints.add(pointKey);
            des.repaint();
        }
    }




    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed here
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int dep = 30;
        int cellSize = des.getCellSize();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z: // Déplacer vers le bas
                des.setYoffset(des.getYoffset() + dep);
                break;
            case KeyEvent.VK_Q: // Déplacer vers la droite
                des.setXoffset(des.getXoffset() + dep);
                break;
            case KeyEvent.VK_S: // Déplacer vers le haut
                des.setYoffset(des.getYoffset() - dep);
                break;
            case KeyEvent.VK_D: // Déplacer vers la gauche
                des.setXoffset(des.getXoffset() - dep);
                break;
            case KeyEvent.VK_UP: // Zoom avant
                zoom(1);
                break;
            case KeyEvent.VK_DOWN: // Zoom arrière
                if (des.getCellSize() > 1) {
                    zoom(-1);
                }
                break;
            case KeyEvent.VK_ENTER:
                map.nextGeneration();
                break;
            case KeyEvent.VK_SPACE:
                if (des.getStatus() == 1) {
                    des.stopSimulation();
                } else {
                    des.startSimulation();
                }
                break;
        }
        des.repaint();
    }

    private void zoom(int delta) {
        int cellSize = des.getCellSize();
        int zoomFactor = delta; // Zoom avant pour delta > 0, zoom arrière pour delta < 0

        // Calculer la taille de la cellule avant le zoom
        int oldCellSize = cellSize;

        // Calculer la nouvelle taille de la cellule
        int newCellSize = cellSize + zoomFactor;
        des.setCellSize(newCellSize);

        // Calculer la différence de taille de cellule
        int cellSizeDifference = newCellSize - oldCellSize;

        // Calculer les décalages pour centrer le zoom autour du milieu de l'affichage
        int centerX = des.getWidth() / 2;
        int centerY = des.getHeight() / 2;

        // Ajuster les décalages en fonction de la différence de taille de cellule
        des.setXoffset(des.getXoffset() - cellSizeDifference * (centerX - des.getXoffset()) / oldCellSize);
        des.setYoffset(des.getYoffset() - cellSizeDifference * (centerY - des.getYoffset()) / oldCellSize);
    }



    @Override
    public void keyReleased(KeyEvent e) {
        // No action needed here
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
