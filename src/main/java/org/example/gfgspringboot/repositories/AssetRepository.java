package org.example.gfgspringboot.repositories;

import org.example.gfgspringboot.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
