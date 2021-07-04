package fr.manu.courses.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Veuillez indiquer un nom")
    @NotBlank(message = "Veuillez indiquer un nom")
    @Size(min = 2, max = 50, message = "Veuillez indiquer un Nom entre 2 et 50 caractères")
    private String name;

    @Email(message = "Le format de votre adresse email n'est pas bon")
    @NotNull(message = "Veuillez indiquer une adresse email")
    @NotBlank(message = "Veuillez indiquer une adresse email")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Veuillez indiquer un mot de passe")
    @NotBlank(message = "Veuillez indiquer un mot de passe")
    @Size(min = 8, message = "Veuillez indiquer un mot de passe avec au moins 8 caractères")
    private String password;

    @ManyToMany
    private List<ListItem> listItems;

    @ManyToMany
    private Set<Role> roles;

    private boolean active;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", listItems=" + listItems +
                '}';
    }
}
