/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ioana.Radoi
 */
@Entity
@Table(name = "user_area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserArea.findAll", query = "SELECT u FROM UserArea u"),
    @NamedQuery(name = "UserArea.findById", query = "SELECT u FROM UserArea u WHERE u.id = :id"),
    @NamedQuery(name = "UserArea.findByIdUser", query = "SELECT u FROM UserArea u WHERE u.idUser = :idUser"),
    @NamedQuery(name = "UserArea.findByIdZona", query = "SELECT u FROM UserArea u WHERE u.idZona = :idZona"),
    @NamedQuery(name = "UserArea.findByDurata", query = "SELECT u FROM UserArea u WHERE u.durata = :durata"),
    @NamedQuery(name = "UserArea.findByTimestampp", query = "SELECT u FROM UserArea u WHERE u.timestampp = :timestampp")})
public class UserArea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "id_zona")
    private Integer idZona;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "durata")
    private Float durata;
    @Column(name = "timestampp")
    private Double timestampp;

    public UserArea() {
    }

    public UserArea(Integer id, Integer idUser, Integer idZona, Float durata, Double timestampp) {
        this.id = id;
        this.idUser = idUser;
        this.idZona = idZona;
        this.durata = durata;
        this.timestampp = timestampp;
    }

    
    
    public UserArea(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public Float getDurata() {
        return durata;
    }

    public void setDurata(Float durata) {
        this.durata = durata;
    }

    public Double getTimestampp() {
        return timestampp;
    }

    public void setTimestampp(Double timestampp) {
        this.timestampp = timestampp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserArea)) {
            return false;
        }
        UserArea other = (UserArea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.UserArea[ id=" + id + " ]";
    }
    
}
