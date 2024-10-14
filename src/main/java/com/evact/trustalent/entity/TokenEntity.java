package com.evact.trustalent.entity;

import com.evact.trustalent.common.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_M_TOKEN")
@Entity()
public class TokenEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String token;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private TokenType tokenType = TokenType.BEARER;

	private boolean revoked;

	private boolean expired;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
}
