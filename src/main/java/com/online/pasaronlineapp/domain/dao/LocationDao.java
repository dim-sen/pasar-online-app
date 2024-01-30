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
@Table(name = "LOCATIONS")
public class LocationDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "sub_district", nullable = false)
    private String subDistrict;

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "latitude", nullable = false)
    private String latitude;

    @Column(name = "longitude", nullable = false)
    private String longitude;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "pembeli_id")
    @ToString.Exclude
    private PembeliDao pembeli;

    @OneToMany(mappedBy = "location", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<OrderDao> orderDaos;
}
