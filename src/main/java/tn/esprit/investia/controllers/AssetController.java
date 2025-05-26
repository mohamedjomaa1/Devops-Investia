package tn.esprit.investia.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.investia.entities.Asset;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.services.AssetService;
import tn.esprit.investia.services.IUserInterface;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController {
    @Autowired
    private AssetService assetService;
    @Autowired
    private IUserInterface userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
        return ResponseEntity.ok().body(assetService.getAssetById(assetId));
    }
    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(@PathVariable String coinId,
                                                              @RequestHeader("Authorization") String email) throws Exception
                                                            {
        User user = userService.getUserByEmail(email);
        Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);
        return ResponseEntity.ok().body(asset);
    }
    @GetMapping()
    public ResponseEntity<List<Asset>> getAssetsForUser(
            @RequestHeader("Authorization") String email) throws Exception{
        User user = userService.getUserByEmail(email);
        List<Asset> assets = assetService.getUsersAssets(user.getId());
        return ResponseEntity.ok().body(assets);
    }

}
