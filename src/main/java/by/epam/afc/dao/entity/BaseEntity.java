package by.epam.afc.dao.entity;

/**
 * The type Base entity.
 */
public abstract class BaseEntity implements Comparable<BaseEntity> {
    /**
     * The constant UNDEFINED_ID.
     */
    public static final int UNDEFINED_ID = -1;

    /**
     * The Id.
     */
    protected int id;

    /**
     * Instantiates a new Base entity.
     */
    public BaseEntity() {
        id = UNDEFINED_ID;
    }

    /**
     * Instantiates a new Base entity.
     *
     * @param id the id
     */
    public BaseEntity(int id) {
        this.id = id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
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

        BaseEntity that = (BaseEntity) o;

        return id == that.id;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Compare to int.
     *
     * @param o the o
     * @return the int
     */
    @Override
    public int compareTo(BaseEntity o) {
        return Integer.compare(id, o.getId());
    }
}
