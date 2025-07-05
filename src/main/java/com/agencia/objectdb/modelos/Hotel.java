package com.agencia.objectdb.modelos;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Juan Manuel Sanchez Hernandez
 */
@Entity
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigoHotel;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false, length = 10)
    private String telefono;

    @Column(nullable = false)
    private Number cuartosDisponibles;

    @OneToMany(mappedBy = "sucursal")
    private List<Cuarto> cuartos = new ArrayList<>();

    //Constructor vacio JPA
    public Hotel() {
    }

    // Constructor con parametros
    public Hotel(String codigoHotel, String codigoSucursal, String nombre, String direccion, String telefono, String ciudad, Number cuartosDisponibles) {
        this.codigoHotel = codigoHotel;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.cuartosDisponibles = cuartosDisponibles;

    }
    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoHotel() {
        return codigoHotel;
    }

    public void setCodigoHotel(String codigoHotel) {
        this.codigoHotel = codigoHotel;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Number getCuartosDisponibles() {
        return cuartosDisponibles;
    }

    public void setCuartosDisponibles(Number cuartosDisponibles) {
        this.cuartosDisponibles = cuartosDisponibles;
    }

    public List getCuarto() {
        return cuartos;
    }

    public void setCuarto(List cuarto) {
        this.cuartos = cuarto;
    }
    
    
    
    @Override
    public String toString() {
        return "Nombre del Hotel: " + nombre + " | Tel: " + telefono + "Direccion:" + direccion + "Cuartos disponibles:" + cuartosDisponibles ;
    }

}
