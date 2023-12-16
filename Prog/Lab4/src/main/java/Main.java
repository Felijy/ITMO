import classes.Everyone;
import classes.Human;
import classes.Thing;
import exceptions.FallOffTheEndException;
import exceptions.NullThingException;
import interfaces.Do;
import interfaces.Fall;
import other.Moves;

public class Main {
    public static void main(String[] args) {
        // анонимный класс
        Fall ponchikFall = new Fall() {
            @Override
            public void fallingFrom(Thing smth, boolean complete) {
                if (complete) {
                    if (Math.random() < 0.01) {
                        throw new FallOffTheEndException("Пончик упал слишком высоко :(");
                    }
                    System.out.println("Пончик вновь сорвался с " + smth.getName());
                } else {
                    System.out.println("Пончик чуть не сорвался с " + smth.getName());
                }
            }

            @Override
            public void fallingTo(Human smb, boolean complete) {
                if (complete) {
                    System.out.println("Пончик сорвался на " + smb.getName());
                } else {
                    System.out.println("Пончик чуть не сорвался на " + smb.getName());
                }
            }
        };

        Everyone everyone = new Everyone("Все");
        Human znaika = new Human("Знайка");
        Human toropizhka = new Human("Торопыжка");
        Human ponchik = new Human("Пончик");
        Human korotishi = new Human("Коротыши");
        Human sahar = new Human("Сахарин Сахариныч Сиропчик");
        Human topik = new Human("Топик");
        Thing balloon = new Thing("воздушный шар");
        Thing basket = new Thing("корзина");
        Thing warmAir = new Thing("теплный воздух");
        Thing boiler = new Thing("котел");
        Thing pipe = new Thing("резиновая трубка");
        Thing nutBush = new Thing("ореховый куст");

        try {
            topik.doingWithoutThing(Moves.SHOUT);
            balloon.status.addStatus("не улетел");
            balloon.status.printStatus();
            basket.doing(Moves.TIE, nutBush);
            basket.status.addStatus("немного приподнялась");
            basket.status.printStatus();
            everyone.status.addStatus("верят");
            everyone.status.printStatus();
            balloon.doing(Moves.FILL, warmAir);
            znaika.asking(Moves.REMOVE, boiler);
            znaika.doing(Moves.TIE, pipe);
        } catch (NullThingException e) {
            System.out.println("Exception! " + e);
        }
        pipe.doingWithoutThing(Moves.TIE);
        znaika.asking(Moves.SIT, basket);
        try {
            toropizhka.climbing(basket);
            ponchik.climbing(basket);
            ponchikFall.fallingFrom(basket, false);
            ponchikFall.fallingTo(korotishi, false);
        } catch (NullThingException e) {
            System.out.println("Exception! " + e);
        }
        ponchik.status.addStatus("толстенький");
        ponchik.status.printStatus();
        Human.CurrentThing ponckikPocket = new Human.CurrentThing("Карман пончика");
        ponckikPocket.addThing(new Thing("сахарок"));
        ponckikPocket.printThing();
        ponckikPocket.addThing(new Thing("печеньице"));
        ponckikPocket.printThing();
        ponckikPocket.addThing(new Thing("галоши"));
        ponckikPocket.printThing();
        ponchik.content.addThing(new Thing("зонтик"));
        ponchik.content.printThing();
        everyone.helping(Moves.SIT, ponchik);
        everyone.doing(Moves.SIT, basket);
        sahar.status.addStatus("суетится");
        sahar.status.printStatus();
        sahar.helping(Moves.SIT, everyone);
        everyone.status.addStatus("залезли");
        everyone.status.printStatus();
        sahar.status.addStatus("остался внизу");
        sahar.status.printStatus();
    }
}