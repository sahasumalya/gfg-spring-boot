package org.example.gfgspringboot.services;

import org.example.gfgspringboot.models.Asset;
import org.example.gfgspringboot.models.AssetMetaData;
import org.example.gfgspringboot.models.User;
import org.example.gfgspringboot.models.UserRole;
import org.example.gfgspringboot.repositories.AssetRepository;
import org.example.gfgspringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    @Autowired
    public AssetService(AssetRepository assetRepository, UserRepository userRepository) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    public Asset updateAssetMetaData(Long userId, Long assetId, AssetMetaData assetMetaData) {
        Optional<User> user =  userRepository.findById(userId);
        if(user.isPresent() && (user.get().getRole().equals(UserRole.LIBRARIAN) || user.get().getRole().equals(UserRole.ADMIN))) {
            Asset asset  = assetRepository.findAssetById(assetId);
            if(asset!=null) {
                asset.setMetaData(assetMetaData);
                return assetRepository.save(asset);
            }
        }
        return null;

    }
}
