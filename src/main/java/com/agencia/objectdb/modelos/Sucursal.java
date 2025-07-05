package com.agencia.objectdb.modelos;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Juan Manuel Sanchez Hernandez
 */
@Entity
public class Sucursal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigoSucursal;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false, length = 10)
    private String telefono;

    @OneToMany(mappedBy = "sucursal")
    private List<Turista> turistas = new ArrayList<>();

    //Constructor vacio JPA
    public Sucursal() {
    }

    // Constructor con parametros
    public Sucursal(String codigoSucursal, String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.codigoSucursal = codigoSucursal;

    }
    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Sucursal: " + nombre + " | Código: " + codigoSucursal + " | Tel: " + telefono;
    }

}
