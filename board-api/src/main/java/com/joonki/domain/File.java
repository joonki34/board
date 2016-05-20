package com.joonki.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "file")
@Getter
@Setter
public class File implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4096108404419342414L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String path;
}
