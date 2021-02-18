package com.example.project.springbootrestapiuserdetails.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "USERS")
@JsonPropertyOrder({ "firstName", "lastName", "empId", "gender", "address"})
@Proxy(lazy = false)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name="id")
    private Long id;

    @Column(name = "firstname", nullable = false)
    @NotBlank(message = "First name is required")
    @JsonProperty("firstName")
    private String firstName;

    @Column(name = "lastname", nullable = false)
    @NotBlank(message = "Last name is required")
    @JsonProperty("lastName")
    private String lastName;

    @Column(name = "empId", nullable = false)
    //@NotBlank(message = "Employee Id is required")
    @JsonProperty("empId")
    private int empId;

    @Column(name = "gender", nullable = false)
    @NotBlank(message = "Gender is required")
    @JsonProperty("gender")
    private String gender;

    //@JsonManagedReference
    @OneToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    @JoinColumn(name = "addressid", nullable = false, referencedColumnName = "id")
    @JsonProperty("address")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Address address;
}
