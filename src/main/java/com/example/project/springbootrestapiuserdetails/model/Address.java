package com.example.project.springbootrestapiuserdetails.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ADDRESS")
@Getter
@Setter
@ToString
public class Address implements Serializable {

    @Id
    @Column(name="id")
    /*@SequenceGenerator(initialValue=1, name="address_seq", sequenceName="address_sequence")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="address_seq")*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "street", nullable = false)
    @JsonProperty("street")
    private String street;

    @Column(name = "city", nullable = false)
    @JsonProperty("city")
    private String city;

    @Column(name = "state", nullable = false)
    @JsonProperty("state")
    private String state;

    @Column(name = "postcode", nullable = false)
    @JsonProperty("postcode")
    private String postcode;

    /*@OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;*/
}
