package by.epam.afc.dao.entity;

public class Region extends BaseEntity{
    private final String regionName;

    public Region(int id, String regionName){
        super(id);
        this.regionName = regionName;
    }

    public Region(int id){
        super(id);
        regionName = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Region region = (Region) o;

        return regionName != null ? regionName.equals(region.regionName) : region.regionName == null;
    }

    @Override
    public int hashCode() {
        return regionName != null ? regionName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}