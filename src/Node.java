public class Node {
    private int id;
    private int longitude;
    private int latitude;

    public Node(int id, int longitude, int latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }
}
