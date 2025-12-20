package SFG.Street_Food_Go.Services.models;

import SFG.Street_Food_Go.Entities.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class PersonDetails implements UserDetails {
    private String username;
    private String password;
    private Long personId;
    private GrantedAuthority grantedAuthority;

    public PersonDetails(Person person) {
        this.username = person.getName();
        this.password = person.getPasswordHash();
        this.personId = person.getId();
        this.grantedAuthority = new SimpleGrantedAuthority("ROLE_" + person.getPersonType());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public GrantedAuthority getGrantedAuthority() {
        return grantedAuthority;
    }

    public void setGrantedAuthority(GrantedAuthority grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    @Override
    public String toString() {
        return "PersonDetails{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", personId=" + personId +
                ", grantedAuthority=" + grantedAuthority +
                '}';
    }
}
