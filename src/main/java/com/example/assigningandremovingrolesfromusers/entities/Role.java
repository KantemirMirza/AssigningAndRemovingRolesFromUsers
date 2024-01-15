package com.example.assigningandremovingrolesfromusers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    private String roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public void removeAllUsersFromRole(){
        if(this.getUsers() != null){
            List<User> userInRole = this.getUsers().stream().toList();
            userInRole.forEach(this :: removeUserFromRole);
        }
    }

    public void removeUserFromRole(User user) {
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

    public void assignUserToRole(User user) {
        user.getRoles().add(this);
        this.getUsers().add(user);
    }
}
