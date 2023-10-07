import pokemons.*;
import ru.ifmo.se.pokemon.*;


public class Lab2 {
    public static void main(String[] args) {
        Battle b = new Battle();
        Deoxys p1 = new Deoxys("R#1", 10);
        Jirachi p2 = new Jirachi("R#2", 10);
        Spearow p3 = new Spearow("R#3", 10);
        Squirtle p4 = new Squirtle("B#1", 10);
        Teddiursa p5 = new Teddiursa("B#2", 10);
        Turtwig p6 = new Turtwig("B#3", 10);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}
