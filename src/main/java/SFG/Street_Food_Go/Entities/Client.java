package SFG.Street_Food_Go.Entities;
import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String clientSecret;

    @Column(name = "roles_csv")
    private String rolesCsv;

    public Client() {}

    public Client(String clientId, String clientSecret, String rolesCsv) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.rolesCsv = rolesCsv;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getRolesCsv() { return rolesCsv; }
    public void setRolesCsv(String rolesCsv) { this.rolesCsv = rolesCsv; }
}
