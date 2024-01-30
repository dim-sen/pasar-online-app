package com.online.pasaronlineapp.domain.dao;

import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PEMBELIS")
public class PembeliDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "pembeli", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<LocationDao> locationDaos;

    @OneToMany(mappedBy = "pembeli", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CartDao> cartDaos;
}