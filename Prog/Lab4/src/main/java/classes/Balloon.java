package classes;

public class Balloon extends Thing{
    private Basket currentBucket;

    public Balloon(String name) {
        super(name);
    }

    public class Basket {
        private String name;
        Basket(String name) {
            this.name = name;
        }
    }

    public void setBasket(String name) {
        currentBucket = new Basket(name);
    }

    public Basket getBasket() {
        return currentBucket;
    }

    public String getBasketName() {
        return currentBucket.name;
    }

    public void tieBasket() {
        System.out.println(currentBucket.name + " привязана");
    }

    public void flyBasket() {
        System.out.println(currentBucket.name + " приподнялась");
    }
}
