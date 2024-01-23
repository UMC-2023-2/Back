package com.pickpick.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Users {

	@Id
	@GeneratedValue
	@Column(name = "idx")
	private Long idx;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false, unique = true)
	private String userId;

	@Column(nullable = false)
	private String password;

	private String phoneNum;

	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private PublicStatus publicStatus;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private ShareStatus shareStatus;

	private LocalDate createdAt;

}
