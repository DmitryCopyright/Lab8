package common.data;

import java.io.Serializable;

/**
 * Location.
 */
public class Location implements Serializable {
    private Double x;
    private long y;
    private Double z;
    private String name;

    public Location(Double x, long y, Double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }



    /**
     * @return x-coordinate.
     */
    public Double getX() {
        return x;
    } //Это из-за того, что оно стало static, как и все остальное

    /**
     * @return y-coordinate.
     */
    public long getY() {
        return y;
    }

    public Double getZ() {
        return z;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "X: " + x + " Y: " + y + " Z: " + z + " Location: " + name;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + x.hashCode() +  (int) y + z.hashCode() ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Person) {
            Location locationObj = (Location) obj;
            return name.equals(locationObj.getName()) && (x == locationObj.getX()) && (y == locationObj.getY()) && (z == locationObj.getZ());
        }
        return false;
    }
}

