package com.m2i.poec.jpa;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

@Entity
@Table(name="article")
public class Article {

	// on definit l'entité persistente = clé primaire
	@Id  
	// old way to use Generated 
	// Timestamp est crée dans la DB a chaque creation
	// attention plus couteux
	//CreationTimestamp
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	@Column(name="id")
	private Integer Id;
	private String title;
	private String content;
	@Column(name="created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	@Column(name="author_id")
	private Integer authorId;
	
	public Integer getId() {
		return Id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
}

