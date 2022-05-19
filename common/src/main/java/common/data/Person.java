package common.data;

import java.io.Serializable;

/**
 * Person.
 */
public class Person implements Serializable {
    private String name;
    private long weight;
    private Color hairColor;
    private Country nationality;
    private Location location;

    public Person(String name, long weight, Color hairColor, Country nationality, Location location) {
        this.name = name;
        this.weight = weight;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }



    /**
     * @return Name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Person weight.
     */
    public long getWeight() {
        return weight;
    }

    public Color getHairColor() {
        return hairColor;
    }
    public Country getNationality() {
        return nationality;
    }
    public Location getLocation() {
        return location;
    }
    @Override
    public String toString() {
        return "Имя админа - " + name + " Вес - " + weight + " Цвет волос - " + hairColor + " Национальность - " + nationality + " Местоположение - " + location;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + (int) weight + hairColor.hashCode() + nationality.hashCode() + location.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Person) {
            Person personObj = (Person) obj;
            return name.equals(personObj.getName()) && (weight == personObj.getWeight()) && hairColor.equals(personObj.getHairColor()) && nationality.equals(personObj.getNationality()) && location.equals(personObj.getLocation());
        }
        return false;
    }


}
