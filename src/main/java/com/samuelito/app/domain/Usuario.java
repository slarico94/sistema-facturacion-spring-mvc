package com.samuelito.app.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer idUsuario;
	
	@Column(name = "username", length = 45, unique = true)
	private String username;
	
	@Column(name = "password", length = 60)
	private String password;
	
	@Column(name = "enabled")
	private Boolean enabled = true;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Role> roles = new ArrayList<>();
}
