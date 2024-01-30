package com.online.pasaronlineapp.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ADMINS")
public class AdminDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @ToString.Exclude
    private RoleDao role;

    @OneToMany(mappedBy = "adminDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<BarangDao> barangDaos;

    @OneToMany(mappedBy = "adminDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<PackageDao> packageDaos;

    @OneToMany(mappedBy = "adminDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<CategoryDao> categoryDaos;

    @OneToMany(mappedBy = "adminDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<WarehouseDao> warehouseDaos;

    @OneToMany(mappedBy = "adminDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<BatchDao> batchDaos;

    @OneToMany(mappedBy = "adminDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<OrderDao> orderDaos;
}
