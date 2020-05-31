
package com.example.models;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.nosql.annotations.Field;


@Entity
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    @Field(name="_id")
    private String id;
    
    @NotNull
    @Column(name = "create_at", updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar createdAt;
    
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Calendar updatedAt;
    private String name;
    private String marca;
    private String FeComp;
    
    @ManyToOne(cascade = ALL)
    private Competitor competitor;

    public Producto() {
    }

    public Producto(String nName, String pPrice, String dDescription) {
        name = nName;
        marca = pPrice;
        FeComp = dDescription;
    }

    @PreUpdate
    private void updateTimestamp() {
        this.updatedAt = Calendar.getInstance();
    }

    @PrePersist
    private void creationTimestamp() {
        this.createdAt = this.updatedAt = Calendar.getInstance();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFeComp() {
        return FeComp;
    }

    public void setFeComp(String FeComp) {
        this.FeComp = FeComp;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }
    
    
}
