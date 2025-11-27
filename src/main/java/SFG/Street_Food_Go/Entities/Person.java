package SFG.Street_Food_Go.Entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * Dhmiourgia entities. Basika xaraktiristika ka8e an8rwpou pou apo8hkeuetai sth bash.
 */
@Entity
@Table(name = "people", uniqueConstraints = {
        @UniqueConstraint(name = "uk_person_email", columnNames = "email_address"),
        @UniqueConstraint(name = "uk_person_phone_number", columnNames = "phone_number")
}, indexes = {
        @Index(name = "idx_person_phone_number", columnList = "phone_number")
})
public class Person {
    // -----------------------------------------------------------------

    // TODO des ti allo mporei na xreiastei na baloume ws xaraktiristika.

    // -----------------------------------------------------------------

    // TODO des an 8a prepei na kanoume allo entity gia owner kai allo gia customer.

    // -----------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - Kalutero gia postgreSQL database.
    private long id; // auto-generated.

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    @Size(max = 20)
    private PersonType type;

    @Column(name = "first_name", length = 50)
    @Size(max = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(max = 50)
    private String lastName;

    @Column(name = "email_address", length = 255) // Einai null alla otan dw8ei einai unique.
    @Size(max = 255)
    private String emailAddress; // Exei proste8ei sta indexes gia grhgoro search.

    @Column(name = "phone_number", unique = true, length = 20) // Einai null alla otan dw8ei einai unique.
    @Size(max = 20)
    private String phoneNumber; // Exei proste8ei sta indexes gia grhgoro search.

    @Column(name = "address", length = 100)
    @Size(max = 100)
    private String address;

    @Column(name = "password", length = 255)
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String passwordHash;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    // Constructors, getters, setters.
    public Person() {

    }

    public Person(Long id, PersonType type,
                  String firstName, String lastName, String emailAddress,
                  String phoneNumber, String address, String passwordHash, Instant createdAt) {
        this.id = id;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setAddress(String address) { this.address = address; }

    public String getAddress() { return address; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", type=" + type +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}