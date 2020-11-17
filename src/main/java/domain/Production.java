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
public class Production {
    private ProductionCompany productionCompany;
    private Movie movie;
    
    public Production() {
        
    }

    public Production(ProductionCompany productionCompany, Movie movie) {
        this.productionCompany = productionCompany;
        this.movie = movie;
    }

    public ProductionCompany getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.productionCompany);
        hash = 29 * hash + Objects.hashCode(this.movie);
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
        final Production other = (Production) obj;
        if (!Objects.equals(this.productionCompany, other.productionCompany)) {
            return false;
        }
        if (!Objects.equals(this.movie, other.movie)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Production{" + "productionCompany=" + productionCompany + ", movie=" + movie + '}';
    }

    
}
