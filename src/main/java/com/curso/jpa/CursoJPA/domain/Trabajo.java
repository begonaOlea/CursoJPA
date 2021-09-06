package com.curso.jpa.CursoJPA.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="JOBS")
public class Trabajo implements Serializable {
	
	@Id
	@Column(name="JOB_ID")
	private String id;
	
	@Column(name="JOB_TITLE", nullable = false)
	private String tituloTrabajo;
	@Column(name="MIN_SALARY")
	private Double salarioMin;
	@Column(name="MAX_SALARY")
	private Double salarioMax;
	
	@OneToMany(mappedBy = "trabajo",fetch = FetchType.LAZY)
	private List<Empleado> coleccionEmpleados;
	

	public Trabajo() {
		super();
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getTituloTrabajo() {
		return tituloTrabajo;
	}

	public void setTituloTrabajo(String tituloTrabajo) {
		this.tituloTrabajo = tituloTrabajo;
	}

	public Double getSalarioMin() {
		return salarioMin;
	}

	public void setSalarioMin(Double salarioMin) {
		this.salarioMin = salarioMin;
	}

	public Double getSalarioMax() {
		return salarioMax;
	}

	public void setSalarioMax(Double salarioMax) {
		this.salarioMax = salarioMax;
	}
	
	public List<Empleado> getColeccionEmpleados() {
		return coleccionEmpleados;
	}
	
	public void setColeccionEmpleados(List<Empleado> coleccionEmpleados) {
		this.coleccionEmpleados = coleccionEmpleados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trabajo other = (Trabajo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
