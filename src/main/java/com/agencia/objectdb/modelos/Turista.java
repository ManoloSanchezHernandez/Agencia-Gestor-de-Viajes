package com.agencia.objectdb.modelos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Turista implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigoTurista;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, length = 15)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    // Constructor vacío requerido por JPA
    public Turista() {
    }

    // Constructor con parámetros
    public Turista(String codigoTurista, String nombre, String apellidos, String telefono, Sucursal sucursal) {
        this.codigoTurista = codigoTurista;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.sucursal = sucursal;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoTurista() {
        return codigoTurista;
    }

    public void setCodigoTurista(String codigoTurista) {
        this.codigoTurista = codigoTurista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    // Métodos funcionales (sin lógica aún, para implementar más adelante)
    public void reservarVuelo() {
        // TODO: implementar lógica de reserva de vuelo
    }

    public void reservarHotel() {
        // TODO: implementar lógica de reserva de hotel
    }

    public void consultarSucursal() {
        // TODO: mostrar datos de la sucursal
    }

    public void registrarse() {
        // TODO: lógica de registro del turista
    }

    @Override
    public String toString() {
        return "Turista: " + nombre + " " + apellidos + " | Tel: " + telefono;
    }
}
