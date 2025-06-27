package com.example.wine.utils;

import com.example.wine.data.local.entity.AppUserEntity;
import com.example.wine.data.local.entity.ClientEntity;
import com.example.wine.data.local.entity.RegionEntity;
import com.example.wine.data.local.entity.RepresentativeEntity;
import com.example.wine.data.local.entity.SaleEntity;
import com.example.wine.data.local.entity.SaleItemEntity;
import com.example.wine.data.local.entity.WineEntity;
import com.example.wine.data.local.entity.WineStockEntity;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.domain.model.AppUser;
import com.example.wine.domain.model.Client;
import com.example.wine.domain.model.Region;
import com.example.wine.domain.model.Representative;
import com.example.wine.domain.model.Sale;
import com.example.wine.domain.model.SaleItem;
import com.example.wine.domain.model.Wine;
import com.example.wine.domain.model.WineStock;
import com.example.wine.domain.model.Winery;
import com.example.wine.ui.SaleCreateDisplay.ClientSpinnerModel;
import com.example.wine.ui.SaleCreateDisplay.RepresentativeSpinnerModel;
import com.example.wine.ui.SaleDisplay.SaleDisplayModel;
import com.example.wine.ui.adminDisplay.AdminDisplayModel;
import com.example.wine.ui.representative.RepresentativeDisplayModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Mapper {

    public static ClientSpinnerModel toClientSpinnerModel(Client client) {
        if (client == null) return null;
        return new ClientSpinnerModel(client.getId(), client.getName());
    }

    public static AdminDisplayModel toAdminDisplayModel(AppUser appUser) {
        if (appUser == null) return null;
        return new AdminDisplayModel(appUser.getId(), appUser.getName(), appUser.getEmail(), appUser.getRole());
    }

    public static RepresentativeSpinnerModel toRepresentativeSpinnerModel(Representative representative) {
        if (representative == null) return null;
        return new RepresentativeSpinnerModel(representative.getId(), "Rep. " + representative.getUserId().substring(0, 4));
    }

    public static RepresentativeDisplayModel toRepresentativeDisplayModel(Representative representative, AppUser appUser) {
        if (representative == null) return null;
        String name = (appUser != null) ? appUser.getName() : "Nome Desconhecido";
        String email = (appUser != null) ? appUser.getEmail() : "Email Desconhecido";
        return new RepresentativeDisplayModel(representative.getId(), name, representative.getPhone(), email);
    }

    public static Wine toModel(WineEntity entity) {
        Wine wine = new Wine();
        wine.setId(entity.getId());
        wine.setWineryId(entity.getWineryId());
        wine.setName(entity.getName());
        wine.setYear(entity.getYear());
        wine.setGrape(entity.getGrape());
        wine.setCategory(entity.getCategory());
        wine.setAlcoholPercentage(entity.getAlcoholPercentage());
        wine.setPrice(entity.getPrice());
        wine.setStock(entity.getStock());
        wine.setTastingNotes(entity.getTastingNotes());
        wine.setRating(entity.getRating());
        wine.setAgingTime(entity.getAgingTime());
        wine.setServingTemperature(entity.getServingTemperature());
        wine.setAcidity(entity.getAcidity());
        wine.setPairing(entity.getPairing());
        wine.setCommercialCategory(entity.getCommercialCategory());
        wine.setSynced(entity.isSynced());
        wine.setUpdatedAt(entity.getUpdatedAt());
        wine.setDeleted(entity.isDeleted());
        return wine;
    }

    public static WineEntity toEntity(Wine wine) {
        WineEntity entity = new WineEntity();
        entity.setId(wine.getId() != null && !wine.getId().isEmpty() ? wine.getId() : UUID.randomUUID().toString());
        entity.setWineryId(wine.getWineryId());
        entity.setName(wine.getName());
        entity.setYear(wine.getYear());
        entity.setGrape(wine.getGrape());
        entity.setCategory(wine.getCategory());
        entity.setAlcoholPercentage(wine.getAlcoholPercentage());
        entity.setPrice(wine.getPrice());
        entity.setStock(wine.getStock());
        entity.setTastingNotes(wine.getTastingNotes());
        entity.setRating(wine.getRating());
        entity.setAgingTime(wine.getAgingTime());
        entity.setServingTemperature(wine.getServingTemperature());
        entity.setAcidity(wine.getAcidity());
        entity.setPairing(wine.getPairing());
        entity.setCommercialCategory(wine.getCommercialCategory());
        entity.setSynced(wine.isSynced());
        entity.setUpdatedAt(wine.getUpdatedAt());
        entity.setDeleted(wine.isDeleted());
        return entity;
    }

    public static SaleDisplayModel toSaleDisplayModel(Sale sale, Client client) {
        if (sale == null || client == null) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(new Date(sale.getSaleDate()));
        return new SaleDisplayModel(
                sale.getId(),
                client.getName(),
                formattedDate,
                sale.getTotal(),
                client.getLatitude(),
                client.getLongitude()
        );
    }

    public static Winery toDomain(WineryEntity entity) {
        Winery winery = new Winery();
        winery.setId(entity.getId());
        winery.setName(entity.getName());
        winery.setCountry(entity.getCountry());
        winery.setRegion(entity.getRegion());
        winery.setSynced(entity.isSynced());
        winery.setDeleted(entity.isDeleted());
        winery.setUpdatedAt(entity.getUpdatedAt());
        return winery;
    }

    public static WineryEntity toEntity(Winery winery) {
        WineryEntity entity = new WineryEntity();
        entity.setId(winery.getId());
        entity.setName(winery.getName());
        entity.setCountry(winery.getCountry());
        entity.setRegion(winery.getRegion());
        entity.setSynced(winery.isSynced());
        entity.setDeleted(winery.isDeleted());
        entity.setUpdatedAt(winery.getUpdatedAt());
        return entity;
    }

    public static AppUser toModel(AppUserEntity entity) {
        if (entity == null) return null;
        AppUser model = new AppUser();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setEmail(entity.getEmail());
        model.setPasswordHash(entity.getPasswordHash());
        model.setRole(entity.getRole());
        model.setSynced(entity.isSynced());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());
        return model;
    }

    public static AppUserEntity toEntity(AppUser model) {
        if (model == null) return null;
        AppUserEntity entity = new AppUserEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setEmail(model.getEmail());
        entity.setPasswordHash(model.getPasswordHash());
        entity.setRole(model.getRole());
        entity.setSynced(model.isSynced());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setDeleted(model.isDeleted());
        return entity;
    }

    public static Representative toModel(RepresentativeEntity entity) {
        if (entity == null) return null;
        Representative model = new Representative();
        model.setId(entity.getId());
        model.setUserId(entity.getUserId());
        model.setPhone(entity.getPhone());
        model.setSynced(entity.isSynced());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());
        return model;
    }

    public static RepresentativeEntity toEntity(Representative model) {
        if (model == null) return null;
        RepresentativeEntity entity = new RepresentativeEntity();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        entity.setPhone(model.getPhone());
        entity.setSynced(model.isSynced());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setDeleted(model.isDeleted());
        return entity;
    }

    public static Client toModel(ClientEntity entity) {
        if (entity == null) return null;
        Client model = new Client();
        model.setId(entity.getId());
        model.setUserId(entity.getUserId());
        model.setName(entity.getName());
        model.setPhone(entity.getPhone());
        model.setEmail(entity.getEmail());
        model.setCity(entity.getCity());
        model.setRegionId(entity.getRegionId());
        model.setLatitude(entity.getLatitude());
        model.setLongitude(entity.getLongitude());
        model.setObservation(entity.getObservation());
        model.setApproved(entity.isApproved());
        model.setApprovedBy(entity.getApprovedBy());
        model.setApprovedAt(entity.getApprovedAt());
        model.setSynced(entity.isSynced());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());
        return model;
    }

    public static ClientEntity toEntity(Client model) {
        if (model == null) return null;
        ClientEntity entity = new ClientEntity();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId()); // Corrigido: garantir userId
        entity.setName(model.getName());
        entity.setPhone(model.getPhone());
        entity.setEmail(model.getEmail());
        entity.setCity(model.getCity());
        entity.setRegionId(model.getRegionId());
        entity.setLatitude(model.getLatitude());
        entity.setLongitude(model.getLongitude());
        entity.setObservation(model.getObservation());
        entity.setApproved(model.isApproved());
        entity.setApprovedBy(model.getApprovedBy());
        entity.setApprovedAt(model.getApprovedAt());
        entity.setSynced(model.isSynced());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setDeleted(model.isDeleted());
        return entity;
    }

    public static Sale toModel(SaleEntity entity) {
        if (entity == null) return null;
        Sale model = new Sale();
        model.setId(entity.getId());
        model.setClientId(entity.getClientId());
        model.setRepresentativeId(entity.getRepresentativeId());
        model.setSaleDate(entity.getSaleDate());
        model.setTotal(entity.getTotal());
        model.setFreight(entity.getFreight());
        model.setDeliveryMethod(entity.getDeliveryMethod());
        model.setSynced(entity.isSynced());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());
        return model;
    }

    public static SaleEntity toEntity(Sale model) {
        if (model == null) return null;
        SaleEntity entity = new SaleEntity();
        entity.setId(model.getId());
        entity.setClientId(model.getClientId());
        entity.setRepresentativeId(model.getRepresentativeId());
        entity.setSaleDate(model.getSaleDate());
        entity.setTotal(model.getTotal());
        entity.setFreight(model.getFreight());
        entity.setDeliveryMethod(model.getDeliveryMethod());
        entity.setSynced(model.isSynced());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setDeleted(model.isDeleted());
        return entity;
    }

    public static SaleItem toModel(SaleItemEntity entity) {
        if (entity == null) return null;
        SaleItem model = new SaleItem();
        model.setId(entity.getId());
        model.setSaleId(entity.getSaleId());
        model.setWineId(entity.getWineId());
        model.setQuantity(entity.getQuantity());
        model.setUnitPrice(entity.getUnitPrice());
        model.setSynced(entity.isSynced());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());
        return model;
    }

    public static SaleItemEntity toEntity(SaleItem model) {
        if (model == null) return null;
        SaleItemEntity entity = new SaleItemEntity();
        entity.setId(model.getId());
        entity.setSaleId(model.getSaleId());
        entity.setWineId(model.getWineId());
        entity.setQuantity(model.getQuantity());
        entity.setUnitPrice(model.getUnitPrice());
        entity.setSynced(model.isSynced());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setDeleted(model.isDeleted());
        return entity;
    }

    public static Region toModel(RegionEntity entity) {
        if (entity == null) return null;
        Region model = new Region();
        model.setId(entity.getId());
        model.setDescription(entity.getDescription());
        return model;
    }

    public static RegionEntity toEntity(Region model) {
        if (model == null) return null;
        RegionEntity entity = new RegionEntity();
        entity.setId(model.getId());
        entity.setDescription(model.getDescription());
        return entity;
    }

    public static WineStock toModel(WineStockEntity entity) {
        if (entity == null) return null;
        WineStock model = new WineStock();
        model.setId(entity.getId());
        model.setWineId(entity.getWineId());
        model.setRepresentativeId(entity.getRepresentativeId());
        model.setQuantity(entity.getQuantity());
        model.setSynced(entity.isSynced());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());
        return model;
    }

    public static WineStockEntity toEntity(WineStock model) {
        if (model == null) return null;
        WineStockEntity entity = new WineStockEntity();
        entity.setId(model.getId());
        entity.setWineId(model.getWineId());
        entity.setRepresentativeId(model.getRepresentativeId());
        entity.setQuantity(model.getQuantity());
        entity.setSynced(model.isSynced());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setDeleted(model.isDeleted());
        return entity;
    }
}
