package com.example.gonzalezdariomyikea.models;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "customer", schema = "myikea", catalog = "")
public class CustomerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "customer_id")
    private short customerId;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "telefono")
    private String telefono;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "fecha_de_nacimiento")
    private Date fechaDeNacimiento;

    public short getCustomerId() {
        return customerId;
    }

    public void setCustomerId(short customerId) {
        this.customerId = customerId;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (customerId != that.customerId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (telefono != null ? !telefono.equals(that.telefono) : that.telefono != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (fechaDeNacimiento != null ? !fechaDeNacimiento.equals(that.fechaDeNacimiento) : that.fechaDeNacimiento != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) customerId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fechaDeNacimiento != null ? fechaDeNacimiento.hashCode() : 0);
        return result;
    }
}
