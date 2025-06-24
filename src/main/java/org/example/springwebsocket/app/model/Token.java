package org.example.springwebsocket.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="token_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="access_token")
	private String accessToken;
	@Column(name="is_logged_out")
	private boolean loggedOut;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
