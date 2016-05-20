package com.joonki.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5642651987860135654L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	@NotNull
	@JsonProperty("user")
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date createdDate;
	
	private String content;
	
	@Column(name="\"group\"")
	private int group;
	@JsonProperty("parent_num")
	private int parentNum;
	private int depth;
	private int sort;
	
	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;
	
	@PrePersist
	protected void onCreate() {
		createdDate = new Date();
	}
}
