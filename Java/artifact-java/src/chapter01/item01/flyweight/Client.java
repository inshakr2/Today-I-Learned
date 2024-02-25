package chapter01.item01.flyweight;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Client {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        FontFactory fontFactory = new FontFactory();

        Character character1 = new Character('h', "white", fontFactory.getFont("nanum:12"));
        Character character2 = new Character('e', "white", fontFactory.getFont("nanum:12"));
        Character character3 = new Character('l', "blue", fontFactory.getFont("nanum:12"));


        Class<?> aClass = Class.forName("foo.bar.class");
        Constructor<?> constructor = aClass.getConstructor();
        Object o = constructor.newInstance();

    }
}
