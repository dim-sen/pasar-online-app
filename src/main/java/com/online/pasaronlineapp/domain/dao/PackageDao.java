package com.online.pasaronlineapp.domain.dao;

import com.online.pasaronlineapp.domain.comman.BaseDao;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PACKAGES")
@SQLDelete(sql = "UPDATE PACKAGES SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class PackageDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_name", nullable = false)
    private String packageName;

    @Column(name = "package_image", nullable = false)
    private String packageImage;

    @OneToMany(mappedBy = "packages", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<PackageItemDao> packageItemDaos;
}
