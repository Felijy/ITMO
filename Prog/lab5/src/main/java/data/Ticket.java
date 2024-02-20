package data;

import exceptions.IncorrectIDException;
import handler.IdHandler;

import java.util.Objects;

/**
 * Основной класс, хранящий в себе всю основную информацию о билетах
 */
public class Ticket {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float price; //Значение поля должно быть больше 0
    private Integer discount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100
    private String comment; //Поле может быть null
    private TicketType type; //Поле может быть null
    private Venue venue; //Поле может быть null

    /**
     * Вспомогательный конструктор для создания наполненного класса
     */
    public Ticket(String name, Coordinates coordinates,
                  float price, Integer discount,
                  String comment, TicketType type, Venue venue) {
        this.setNewId();
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setCreationDate(java.time.LocalDate.now());
        this.setPrice(price);
        this.setDiscount(discount);
        this.setComment(comment);
        this.setType(type);
        this.setVenue(venue);
    }

    /**
     * Пустой конструктор для добавления полей в несколько этапов
     */
    public Ticket() {

    }

    /**
     * Валидирует все значения класса Ticket на корректность
     * @return true если данные верны, false в обратном случае
     */
    public boolean validateValues() {
        if ((getName() == null) || (getName() == "")) return false;
        if ((getCoordinates() == null) || (!getCoordinates().validateValues())) return false;
        if (getPrice() <= 0) return false;
        if ((getDiscount() == null) || (getDiscount() <= 0) || (getDiscount() > 100)) return false;
        if (getVenue() != null) {
            if (!getVenue().validateValues()) return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Float.compare(getPrice(), ticket.getPrice()) == 0 && Objects.equals(getId(), ticket.getId()) && Objects.equals(getName(), ticket.getName()) && Objects.equals(getCoordinates(), ticket.getCoordinates()) && Objects.equals(getCreationDate(), ticket.getCreationDate()) && Objects.equals(getDiscount(), ticket.getDiscount()) && Objects.equals(getComment(), ticket.getComment()) && getType() == ticket.getType() && Objects.equals(getVenue(), ticket.getVenue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCoordinates(), getCreationDate(), getPrice(), getDiscount(), getComment(), getType(), getVenue());
    }

    @Override
    public String toString() {
        return " " +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", coordinates=" + getCoordinates() +
                ", creationDate=" + getCreationDate() +
                ", price=" + getPrice() +
                ", discount=" + getDiscount() +
                ", comment='" + getComment() + '\'' +
                ", type=" + getType() +
                ", venue=" + getVenue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws IncorrectIDException {
        if (!IdHandler.validateId(id)) throw new IncorrectIDException();
        else {
            this.id = id;
            IdHandler.addId(id);
        }
    }

    public void setNewId() {
        this.id = IdHandler.generateId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public java.time.LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.time.LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
