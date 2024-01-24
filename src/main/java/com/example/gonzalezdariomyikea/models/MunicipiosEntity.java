package com.example.gonzalezdariomyikea.models;

import jakarta.persistence.*;

@Entity
@Table(name = "municipios", schema = "myikea", catalog = "")
public class MunicipiosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_municipio")
    private short idMunicipio;
    @Basic
    @Column(name = "id_provincia")
    private short idProvincia;
    @Basic
    @Column(name = "cod_municipio")
    private int codMunicipio;
    @Basic
    @Column(name = "DC")
    private int dc;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_provincia", referencedColumnName = "id_provincia", insertable = false, updatable = false)
    private ProvinciasEntity provincia;
    public ProvinciasEntity getProvincia() {
        return provincia;
    }



    public short getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(short idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public short getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(short idProvincia) {
        this.idProvincia = idProvincia;
    }

    public int getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(int codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public int getDc() {
        return dc;
    }

    public void setDc(int dc) {
        this.dc = dc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MunicipiosEntity that = (MunicipiosEntity) o;

        if (idMunicipio != that.idMunicipio) return false;
        if (idProvincia != that.idProvincia) return false;
        if (codMunicipio != that.codMunicipio) return false;
        if (dc != that.dc) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) idMunicipio;
        result = 31 * result + (int) idProvincia;
        result = 31 * result + codMunicipio;
        result = 31 * result + dc;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
