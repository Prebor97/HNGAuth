package com.HNGInternship.HNGAuth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Organization {
@Id
private String orgId;
@Column(nullable = false)
private String name;
private String description;
@ManyToMany(mappedBy = "organizations")
@JsonBackReference
private Set<HNGUser> users = new HashSet<>();

@PrePersist
    protected void onCreate(){
    if (this.orgId == null){
        this.orgId = UUID.randomUUID().toString();
    }
}
}
