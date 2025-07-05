/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.agencia.objectdb.servicios;

import com.agencia.objectdb.modelos.Estudiante;
import javax.persistence.*;
import java.util.List;

/**
 *
 * @author lordd
 */
public class EstudianteService {
    private EntityManagerFactory emf;
    
    public EstudianteService() {
        emf = Persistence.createEntityManagerFactory("Estudiantes");
    }
    
    public void guardaEstudiante(Estudiante estudiante) {
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(estudiante);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar estudiante: " + e.getMessage());
        }finally{
            em.close();
        }
        
    }
    
    public List<Estudiante> obtenerTodoLosEstudiantes(){
        EntityManager em = emf.createEntityManager();
        
        try{
            TypedQuery<Estudiante> query = em.createQuery(
                    "SELECT e From Estudiante e", Estudiante.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }
    public Estudiante buscarPorId(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Estudiante.class, id);
        }finally{
            em.close();
        }
    }
    
    public void actualizarEstudiante(Estudiante estudiante) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(estudiante);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar estudiante: " + e.getMessage());
        }finally{
            em.close();
        }
    }
    
    public void eliminarEstudiante(Long id) {
        EntityManager em = emf.createEntityManager();
        
        try {
            Estudiante estudiante = em.find(Estudiante.class, id);
            if(estudiante != null){
                em.getTransaction().begin();
                em.remove(estudiante);
                em.getTransaction().commit();
            }
        }catch(Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Error al eliminar estudiante: " + e.getMessage());
        }finally{
            em.close();
        }
    }
    
    public void cerrar(){
        if(emf != null && emf.isOpen()){
            emf.close();
        }
    }


}                                                                                               
