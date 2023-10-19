package com.online.pasaronlineapp.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PACKAGES")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PackageDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_name", nullable = false)
    private String packageName;

    @Column(name = "package_price", nullable = false)
    private Integer packagePrice;

    @Column(name = "package_weight", nullable = false)
    private Integer packageWeight;

    @Column(name = "package_description")
    private String packageDescription;

    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "package_image", columnDefinition = "BYTEA")
    private byte[] packageImage;

    @OneToMany(mappedBy = "packageDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<PackageItemDao> packageItemDaos;
}
