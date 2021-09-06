package com.curso.jpa.CursoJPA.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.curso.jpa.CursoJPA.domain.Empleado;

public class PruebasQuerysJPA {

 
	public static void main(String[] args) {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("MiUnidad");
			
			EntityManager em = factory.createEntityManager();
			
			
			String consulta = "SELECT e FROM Empleado e";
			
			//lista todos los empleados
			Query query = em.createQuery(consulta); //JPA QUERY
			
			List<Empleado> lista  = query.getResultList(); // lista empleados
			for(Empleado e : lista) {
				System.out.println(e.getApellidos());
			}
			
			
			

	}

}
