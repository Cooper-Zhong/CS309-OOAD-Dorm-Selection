package cs309_dorm_backend.domain;

import jakarta.persistence.*;


//building_id int primary key,
//        zone_name   varchar(30) references zones (name),
//        max_height  int not null

@Entity
@Table(name = "buildings")
public class Building {
    @Id // primary key
    private long buildingId;

    @Column(nullable = false)
    private int maxHeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_name")
    private Zone zone;

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long building_id) {
        this.buildingId = building_id;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int max_height) {
        this.maxHeight = max_height;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }


}

