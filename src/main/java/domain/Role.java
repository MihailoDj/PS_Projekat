/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Objects;

/**
 *
 * @author Mihailo
 */
public class Role {
    private String roleName;
    private Movie movie;
    private Actor actor;
    
    public Role () {
        
    }

    public Role(String roleName, Movie movie, Actor actor) {
        this.roleName = roleName;
        this.movie = movie;
        this.actor = actor;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.roleName);
        hash = 41 * hash + Objects.hashCode(this.movie);
        hash = 41 * hash + Objects.hashCode(this.actor);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if (!Objects.equals(this.roleName, other.roleName)) {
            return false;
        }
        if (!Objects.equals(this.movie, other.movie)) {
            return false;
        }
        if (!Objects.equals(this.actor, other.actor)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Role{" + "roleName=" + roleName + ", movie=" + movie + ", actor=" + actor + '}';
    }
    
    
}
