package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@IdClass(GroupFuncPK.class)
@Entity
@Table(name="Groupfunc")
@AllArgsConstructor
@NoArgsConstructor
public class GroupFunc implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="GROUP_ID")
	private long groupid;
	
	@Id
	@Column(name="FUNC_ID")
	private long funcid;
	
	@Column(name="AUTH_TYPE")
	private String authtype;
	
	@Column(name="CDATE")
	private Timestamp cdate;
}

