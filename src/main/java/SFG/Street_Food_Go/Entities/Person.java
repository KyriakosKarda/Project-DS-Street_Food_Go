package SFG.Street_Food_Go.Entities;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String emailAddress;

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private String passwordHash;

    @Column
    private Instant createdAt;

    @OneToMany(mappedBy = "owner")
    private List<Restaurant> restaurants;


    @OneToMany(mappedBy = "person_order")
    private List<OrderRequest> orders;



    // Constructors, getters, setters.
    public Person() {

        this.createdAt = Instant.now();
    }

    public Person(Long id, PersonType personType, String name, String surname, String emailAddress, String phoneNumber, String address, String passwordHash) {
        this.id = id;
        this.personType = personType;
        this.name = name;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.passwordHash = passwordHash;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<Restaurant> getRestaurants() {
         return restaurants;
    }
                                                                  
    public void setRestaurants(List<Restaurant> restaurants) {
         this.restaurants = restaurants;                          
    }
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personType=" + personType +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}