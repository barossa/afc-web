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

        return id == region.id;
    }

    @Override
    public int hashCode() {
        return 31 * id;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
