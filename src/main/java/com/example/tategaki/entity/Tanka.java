package com.example.tategaki.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tanka")
@NoArgsConstructor
@Getter
@Setter
public class Tanka {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tankaId;

	@Column(name = "content")
	private String content;

}
