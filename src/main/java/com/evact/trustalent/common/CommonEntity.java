package com.evact.trustalent.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class CommonEntity {

	@JsonIgnore
	@Column(name = "CREATED_BY", nullable = false)
	private String createdBy;

	@JsonIgnore
	@Column(name = "CREATED_DT", nullable = false)
	private Date createdDt;

	@JsonIgnore
	@Column(name = "CHANGED_BY")
	private String changedBy;

	@JsonIgnore
	@Column(name = "CHANGED_DT")
	private Date changedDt;

}
