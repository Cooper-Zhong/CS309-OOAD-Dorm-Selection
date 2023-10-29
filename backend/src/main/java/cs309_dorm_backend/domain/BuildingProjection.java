package cs309_dorm_backend.domain;

import lombok.Data;

@Data
public class BuildingProjection {
    private int buildingId;
    private int maxHeight;
    private String zoneName;

}