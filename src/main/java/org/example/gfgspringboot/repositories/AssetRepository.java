package org.example.gfgspringboot.repositories;

import org.example.gfgspringboot.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query(name = "Select u from assets u where id=?1", nativeQuery = true)
    public Asset findAssetById(Long id);


}
