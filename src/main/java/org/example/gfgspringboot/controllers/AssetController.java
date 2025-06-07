package org.example.gfgspringboot.controllers;

import org.example.gfgspringboot.models.Asset;
import org.example.gfgspringboot.models.AssetMetaData;
import org.example.gfgspringboot.models.UpdateAssetMetadataRequest;
import org.example.gfgspringboot.services.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssetController {

    private final AssetService assetService;


    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PutMapping("/v1/assetMetadata")
    public ResponseEntity<Asset> updateAssetMetadata(@RequestBody UpdateAssetMetadataRequest updateAssetMetadataRequest) {
        Long userId = updateAssetMetadataRequest.getUserId();
        AssetMetaData assetMetaData = new AssetMetaData();
        assetMetaData.setGenre(updateAssetMetadataRequest.getGenre());
        assetMetaData.setAuthor(updateAssetMetadataRequest.getAuthor());
        return ResponseEntity.ok(assetService.updateAssetMetaData(userId, updateAssetMetadataRequest.getAssetId(), assetMetaData));

    }
}
