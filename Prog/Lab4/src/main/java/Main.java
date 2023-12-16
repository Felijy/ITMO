import classes.Balloon;
import classes.Everyone;
import classes.Human;
import classes.Thing;
import exceptions.FallOffTheEndException;
import exceptions.NullThingException;
import interfaces.IFall;
import other.Moves;

public class Main {
    public static void main(String[] args) {
        // анонимный класс
        IFall ponchikIFall = new IFall() {
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
        Balloon balloon = new Balloon("воздушный шар");
        balloon.setBasket("корзина");
        Thing warmAir = new Thing("теплный воздух");
        Thing boiler = new Thing("котел");
        Thing pipe = new Thing("резиновая трубка");

        try {
            topik.doingWithoutThing(Moves.SHOUT);
            balloon.status.addStatus("не улетел");
            balloon.status.printStatus();
            balloon.tieBasket();
            balloon.flyBasket();
            everyone.addStatus("верят");
            everyone.printStatus();
            balloon.doing(Moves.FILL, warmAir);
            znaika.asking(Moves.REMOVE, boiler);
            znaika.doing(Moves.TIE, pipe);
        } catch (NullThingException e) {
            System.out.println("Exception! " + e);
        }
        pipe.doingWithoutThing(Moves.TIE);
        znaika.asking(Moves.SIT, balloon);
        try {
            toropizhka.climbing(balloon);
            ponchik.climbing(balloon);
            ponchikIFall.fallingFrom(balloon, false);
            ponchikIFall.fallingTo(korotishi, false);
        } catch (NullThingException e) {
            System.out.println("Exception! " + e);
        }
        ponchik.addStatus("толстенький");
        ponchik.printStatus();
        ponchik.addThing(new Thing("сахарок"));
        ponchik.printThing();
        ponchik.addThing(new Thing("печеньице"));
        ponchik.printThing();
        ponchik.addThing(new Thing("галоши"));
        ponchik.printThing();
        ponchik.addThing(new Thing("зонтик"));
        ponchik.printThing();
        everyone.helping(Moves.SIT, ponchik);
        everyone.doing(Moves.SIT, balloon);
        sahar.addStatus("суетится");
        sahar.printStatus();
        sahar.helping(Moves.SIT, everyone);
        everyone.addStatus("залезли");
        everyone.printStatus();
        sahar.addStatus("остался внизу");
        sahar.printStatus();
    }
}