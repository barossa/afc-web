package by.epam.afc.dao.entity;

/**
 * The type Region.
 */
public class Region extends BaseEntity {
    private final String regionName;

    /**
     * Instantiates a new Region.
     *
     * @param id         the id
     * @param regionName the region name
     */
    public Region(int id, String regionName) {
        super(id);
        this.regionName = regionName;
    }

    /**
     * Instantiates a new Region.
     *
     * @param id the id
     */
    public Region(int id) {
        super(id);
        regionName = "";
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Region region = (Region) o;

        return regionName != null ? regionName.equals(region.regionName) : region.regionName == null;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (regionName != null ? regionName.hashCode() : 0);
        return result;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
