package com.example.batch_sample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserInfo {

    @Id
    private Long id;

    private String name;
}
