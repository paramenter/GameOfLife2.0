public class Main {
    public static void main(String[] args) {
        Map map = new Map();
        map.addPixel(0, 0);
        map.addPixel(1, 0);
        map.addPixel(2, 0);
        new Fen(map);
    }
}