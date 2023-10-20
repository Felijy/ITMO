import pokemons.*;
import ru.ifmo.se.pokemon.*;


public class Lab2 {
    public static void main(String[] args) {
        Battle b = new Battle();
        Throh r1 = new Throh("ниндзя", 5);
        Slowpoke g1 = new Slowpoke("слоупок", 5);
        Slowking r2 = new Slowking("ребенок слоупока", 5);
        Bounsweet g2 = new Bounsweet("трава", 5);
        Steenee r3 = new Steenee("трава X", 5);
        Tsareena g3 = new Tsareena("трава XX", 5);
        b.addFoe(r1);
        b.addFoe(r2);
        b.addFoe(r3);
        b.addAlly(g1);
        b.addAlly(g2);
        b.addAlly(g3);
        b.go();
    }
}
