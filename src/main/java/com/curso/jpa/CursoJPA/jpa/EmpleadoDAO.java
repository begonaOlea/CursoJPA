package com.curso.jpa.CursoJPA.jpa;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.*;

import com.curso.jpa.CursoJPA.domain.Empleado;
import com.curso.jpa.CursoJPA.domain.Trabajo;

public class EmpleadoDAO {
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("OracleHRPU");
	
	/**
	 * 
	 * @param empleado
	 */
	public void crearEmpleado(Empleado empleado) throws EmpleadoException{
		
		EntityManager em = factory.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		
		try {
				em.persist(empleado); 
		}catch(EntityExistsException e) {
		
			throw new EmpleadoException("No se pudo crear. El empleado con id " + empleado.getId() + " ya existe");
		}

		tx.commit();
		
	}//fin crearEmpleado
	
	/**
	 * MÃ©todo que devuelve una instancia del empleado
	 * por su id . Si no lo encuentra devuelve null
	 * 
	 * @param id
	 * @return  empleado (null si no existe)
	 */
	public Empleado getEmpleado(Long id) {
		EntityManager em = factory.createEntityManager();
		Empleado e = em.find(Empleado.class, id); 
		
		System.out.println("..................................");
		
		return e;
	}
	
	//borrar	
	public void borrarEmpleado(Long id) throws EmpleadoException {
		EntityManager em = factory.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Empleado eBorrar = em.find(Empleado.class, id);
		if( eBorrar == null) {
			throw new EmpleadoException("No se pudo borrar. no existe el empleado");
		}
		
		
		em.remove(eBorrar);	
		
		tx.commit();
		
	}
	
	
	//get Trabajo con sus empleados
	public Trabajo getTrabajo(String codigo) {
		
		EntityManager em = factory.createEntityManager();
		Trabajo t = em.find(Trabajo.class, codigo);
		return t;
	
	}
	
	
	
	public void modificarEmpleado(Empleado eModif) {
		EntityManager em = factory.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Empleado eBaseDatos = em.merge(eModif);
		
		tx.commit();
	}
	
	
	/**
	 * lista todos los empleaados
	 * @param args
	 */
	 public List<Empleado> getAllEmpleados(){
		 // createQuery de JPA
		 EntityManager em = factory.createEntityManager();
		// Query query = em.createQuery("SELECT e FROM Empleado e");
		
		Query query = em.createNamedQuery("Empleado.findAll");
		 
		// Query query = em.createNativeQuery("SELECT * FROM EMPLOYEES");
		// List lista = query.getResultList(); // list de Object
		 
		List<Empleado> lista = query.getResultList();
		
		 return lista;
	 }
	 
	 public List<Empleado> ejemploConCriteria(String nombre, String apellidos) {
		
		 EntityManager em = factory.createEntityManager();
		 
		 CriteriaBuilder cb = em.getCriteriaBuilder();
         
         
         CriteriaQuery<Empleado> consulta = cb.createQuery(Empleado.class);
          
         Root<Empleado> personas = consulta.from(Empleado.class); //FROM Empleado e
         Predicate pNombre = null, pApellido = null; // where p1  p2
     
          
         if(nombre != null) {
              
              pNombre= cb.equal(personas.get("nombre"),nombre);  //e.nombre = "Luis"
              
         }
          
         if(apellidos!=null) {
              
              pApellido= cb.equal(personas.get("apellidos"),apellidos);   //e.apelidos = ''
         }
          
         
         
          
         if(nombre!= null && apellidos != null ) {
             Predicate nombreAndApellidos=cb.and(pNombre,pApellido);
          
        	 consulta.select(personas).where(nombreAndApellidos);
         } else if (nombre != null) 
        	 consulta.select(personas).where(pNombre);
         else if (apellidos  !=null )
        	 consulta.select(personas).where(pApellido);
         //else no where
         
        List<Empleado> lista =  em.createQuery(consulta).getResultList();
        for(Empleado p: lista) {
       	 
            System.out.println(p.getNombre() + " - " + p.getApellidos());

        }
        return lista;
         
	 }
	 
	 /**
	  * metodo que devuelve los empleados de un trabajo dado
	  * @param args
	  */
	 public List<Empleado> getEmpleadoPorTrabajo(String idTra){
		 
		 EntityManager em = factory.createEntityManager();
		 Query q  = em.createNamedQuery("Empleado.findByTrabajoId");
		 q.setParameter("idTrabajo", idTra);
		 List<Empleado> lista = q.getResultList();
			
		 return lista;
		 
	 }
	 
	 
	 /**
	  * Metodo que devuelve la liaasta de los empeados de un departamento o 
	  * de todos los departamentos (idDepartamentos llega null) si no se indica 
	  * cuyo salario es mayor que el indicado
	  * 
	  * @param idDepartamento  - opcional null
	  * @param salarioMayorAEste    no opcional
	  * @return
	  */
	 public List<Empleado> buscarPorCriterios(int idDepartamento, double salarioMayorAEste ){
		 return null;
	 }
	 
	 /**
	  * Metodo que devuelte lista de empleados buscados por nombre o/y apellidos
	  * Podemos pasar solo nombre, solo apellido o ninguno
	  * SI no paso ni nombre ni apellido devuelve todos los empleados sin filtro
	  * @param nombre  nombre del empleado , null sin no filta
	  * @param apellidos
	  * @return lllll
	  */
	 public List<Empleado> buscarEmpleadoPorFiltro(String nombre, String apellidos) {
		 EntityManager em = factory.createEntityManager();
	
	            String sql = "select e from Empleado e ";
	            if (nombre != null || apellidos != null) {
	                sql += " where ";
	            }
	            if (nombre != null) {
	 
	                sql +=  " e.nombre ='" + nombre + "'";
	            }
	            if (apellidos != null) {
	 
	                if(nombre!=null) {
	                    sql += " and  e.apellidos='" + apellidos + "'";
	                }else{
	                     
	                    sql += "  e.apellidos='" + apellidos + "'";
	                }
	                 
	            }
	 
	            Query consulta = em.createQuery(sql);
	 
	            List<Empleado> lista = consulta.getResultList();
	            for(Empleado p: lista) {
	 
	                System.out.println(p.getNombre() + " - " + p.getApellidos());
	 
	            }
	            return lista;
	       
	 }
	
	
	public static void main(String[] args) {
	
		EmpleadoDAO dao = new EmpleadoDAO();
		
		Empleado e = new Empleado();
		e.setNombre("Luis");
		e.setApellidos("Ramos");
		//e.setComision(n);
		e.setEmail("lramos@");
		e.setFechaContratacion(new java.util.Date());
		e.setIdDepartamento(100); //101  fk
		//e.setIdManager(null);
		
		//Trabajo t = new Trabajo();
		//t.setId("AD_PRES");
		
		//e.setTrabajo(t);
		e.setIdTrabajo("AD_PRES");
		e.setSalario(23000.0);
		
//		
//		try {
//			
//		//	dao.crearEmpleado(e);
//
//			
//		} catch (EmpleadoException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		//100 int
		//100l o 100L  long
		//1d   double
		//1.0 double
		//1.0f  float
		
		
		//buscar empleado
		Empleado e2 = dao.getEmpleado(100L);
//		
		System.out.println("Nombre "  + e2.getNombre());
//		
		System.out.println("Trabajo " + e2.getTrabajo().getTituloTrabajo());
		
//		Trabajo t = dao.getTrabajo("AD_PRES");
//		
//		System.out.println("trabajo " + t.getTituloTrabajo());
//		
//		for(Empleado e : t.getColeccionEmpleados()) {
//			System.out.println(e.getNombre());
//		}
//		
//		
//		EntityManager em = factory.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//		System.out.println("....... ante find");
//		Empleado e = em.find(Empleado.class,100l);
//		System.out.println("....... despues find");
//		
//		Trabajo t = dao.getTrabajo("AD_VP");
//		
//		System.out.println("....... despues find trabajo");
//		tx.commit();
//	//	em.close();
//		em.clear();
//		
//		List<Empleado> lista = t.getColeccionEmpleados();
//		for(Empleado e2 : lista)
//			System.out.println(e2.getNombre());
		
		System.out.println(".......... CONSULTA");
		List<Empleado> l =  dao.getEmpleadoPorTrabajo("AD_PRES");// dao.getAllEmpleados();
		for(Empleado ee: l) {
			System.out.println(ee.getNombre() + ". tarea " + ee.getTrabajo().getTituloTrabajo());
		}
		
		
		System.out.println("-----------");
		EntityManager em = factory.createEntityManager();
		
		Query q = em.createQuery("SELECT new com.curso.jpa.CursoJPA.domain.Empleado(e.id, e.nombre, e.apellidos) FROM Empleado e");
		Query q2 = em.createQuery("SELECT e.id,e.nombre, e.apellidos FROM Empleado e");
		
		List le1 = q2.getResultList();
		
		List<Empleado> le2 = q.getResultList();
		
	
		//dao.buscarEmpleadoPorFiltro("Luis", null);
		
		dao.ejemploConCriteria(null, null);
		
	
	}
	
	
	
	
}