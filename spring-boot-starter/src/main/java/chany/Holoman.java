package chany;

public class Holoman {

    String name;
    int Howlong;

    @Override
    public String toString() {
        return "Holoman{" +
                "name='" + name + '\'' +
                ", Howlong=" + Howlong +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHowlong() {
        return Howlong;
    }

    public void setHowlong(int howlong) {
        Howlong = howlong;
    }
}
