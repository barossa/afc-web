package by.epam.afc.dao.entity;

/**
 * The type Category.
 */
public class Category extends BaseEntity {
    private String description;

    /**
     * Instantiates a new Category.
     *
     * @param id          the id
     * @param description the description
     */
    public Category(int id, String description) {
        super(id);
        this.description = description;
    }

    /**
     * Instantiates a new Category.
     *
     * @param id the id
     */
    public Category(int id) {
        super(id);
    }

    /**
     * Instantiates a new Category.
     *
     * @param description the description
     */
    public Category(String description) {
        this.description = description;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
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

        Category category = (Category) o;

        return description != null ? description.equals(category.description) : category.description == null;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
