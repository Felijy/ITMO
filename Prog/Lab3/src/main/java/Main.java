public class Main {
    public static void main(String[] args) {
        Everyone everyone = new Everyone("Все");
        Human znaika = new Human("Знайка");
        Human toropizhka = new Human("Торопыжка");
        Human ponchik = new Human("Пончик");
        Human korotishi = new Human("Коротыши");
        Human sahar = new Human("Сахарин Сахариныч Сиропчик");
        Thing balloon = new Thing("воздушный шар");
        Thing basket = new Thing("корзина");
        Thing warmAir = new Thing("теплный воздух");
        Thing boiler = new Thing("котел");
        Thing pipe = new Thing("резиновая трубка");

        balloon.doing(Moves.FILL, warmAir);
        znaika.asking(Moves.REMOVE, boiler);
        znaika.doing(Moves.TIE, pipe);
        pipe.doing(Moves.TIE, null);
        znaika.asking(Moves.SIT, basket);
        toropizhka.climbing(basket);
        ponchik.climbing(basket);
        ponchik.fallingFrom(basket, false);
        ponchik.fallingTo(korotishi, false);
        ponchik.addStatus("толстенький");
        ponchik.printStatus();
        ponchik.addThing(new Thing("карман Пончика"));
        ponchik.getThing().addThing(new Thing("сахарок"));
        ponchik.getThing().printThing();
        ponchik.getThing().addThing(new Thing("печеньице"));
        ponchik.getThing().printThing();
        ponchik.addThing(new Thing("галоши"));
        ponchik.printThing();
        ponchik.addThing(new Thing("зонтик"));
        ponchik.printThing();
        everyone.helping(Moves.SIT, ponchik);
        everyone.doing(Moves.SIT, basket);
        sahar.addStatus("суетится");
        sahar.printStatus();
    }
}