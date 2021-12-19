package by.epam.afc.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Announcement.
 */
public class Announcement extends BaseEntity {
    private User owner;
    private String title;
    private BigDecimal price;
    private int primaryImageNumber;
    private String description;
    private LocalDateTime publicationDate;
    private Status status;
    private Category category;
    private Region region;
    private List<Image> images;

    /**
     * Instantiates a new Announcement.
     *
     * @param id                 the id
     * @param owner              the owner
     * @param title              the title
     * @param price              the price
     * @param primaryImageNumber the primary image number
     * @param description        the description
     * @param publicationDate    the publication date
     * @param status             the status
     * @param category           the category
     * @param region             the region
     * @param images             the images
     */
    public Announcement(int id, User owner, String title, BigDecimal price, int primaryImageNumber,
                        String description, LocalDateTime publicationDate, Status status,
                        Category category, Region region, List<Image> images) {
        super(id);
        this.owner = owner;
        this.title = title;
        this.price = price;
        this.primaryImageNumber = primaryImageNumber;
        this.description = description;
        this.publicationDate = publicationDate;
        this.status = status;
        this.category = category;
        this.region = region;
        this.images = new LinkedList<>(images);
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Sets primary image number.
     *
     * @param primaryImageNumber the primary image number
     */
    public void setPrimaryImageNumber(int primaryImageNumber) {
        this.primaryImageNumber = primaryImageNumber;
    }

    /**
     * Gets primary image number.
     *
     * @return the primary image number
     */
    public int getPrimaryImageNumber() {
        return primaryImageNumber;
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
     * Gets publication date.
     *
     * @return the publication date
     */
    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets publication date.
     *
     * @param publicationDate the publication date
     */
    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets region.
     *
     * @return the region
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Sets region.
     *
     * @param region the region
     */
    public void setRegion(Region region) {
        this.region = region;
    }

    /**
     * Gets images.
     *
     * @return the images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * Sets images.
     *
     * @param images the images
     */
    public void setImages(List<Image> images) {
        this.images = new ArrayList<>(images);
    }

    /**
     * The enum Status.
     */
    public enum Status {
        /**
         * Undefined status.
         */
        UNDEFINED,
        /**
         * Moderating status.
         */
        MODERATING,
        /**
         * Active status.
         */
        ACTIVE,
        /**
         * Inactive status.
         */
        INACTIVE
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

        Announcement that = (Announcement) o;

        if (primaryImageNumber != that.primaryImageNumber) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (publicationDate != null ? !publicationDate.equals(that.publicationDate) : that.publicationDate != null)
            return false;
        if (status != that.status) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;
        return images != null ? images.equals(that.images) : that.images == null;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + primaryImageNumber;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Announcement{" +
                "owner=" + owner +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", primaryImageNumber=" + primaryImageNumber +
                ", description='" + description + '\'' +
                ", publicationDate=" + publicationDate +
                ", status=" + status +
                ", category=" + category +
                ", region=" + region +
                ", images=" + images +
                ", id=" + id +
                '}';
    }

    /**
     * Gets builder.
     *
     * @return the builder
     */
    public static AnnouncementBuilder getBuilder() {
        return new AnnouncementBuilder();
    }

    /**
     * The type Announcement builder.
     */
    public static class AnnouncementBuilder {
        private int nestedId = UNDEFINED_ID;
        private User nestedOwner;
        private String nestedTitle;
        private BigDecimal nestedPrice;
        private int nestedPrimaryImageNumber;
        private String nestedDescription;
        private LocalDateTime nestedPublicationDate;
        private Status nestedStatus;
        private Category nestedCategory;
        private Region nestedRegion;
        private List<Image> nestedImages = new LinkedList<>();

        /**
         * Id announcement builder.
         *
         * @param id the id
         * @return the announcement builder
         */
        public AnnouncementBuilder id(int id) {
            nestedId = id;
            return this;
        }

        /**
         * Owner announcement builder.
         *
         * @param owner the owner
         * @return the announcement builder
         */
        public AnnouncementBuilder owner(User owner) {
            nestedOwner = owner;
            return this;
        }

        /**
         * Title announcement builder.
         *
         * @param title the title
         * @return the announcement builder
         */
        public AnnouncementBuilder title(String title) {
            nestedTitle = title;
            return this;
        }

        /**
         * Price announcement builder.
         *
         * @param price the price
         * @return the announcement builder
         */
        public AnnouncementBuilder price(BigDecimal price) {
            nestedPrice = price;
            return this;
        }

        /**
         * Primary image number announcement builder.
         *
         * @param primaryImageNumber the primary image number
         * @return the announcement builder
         */
        public AnnouncementBuilder primaryImageNumber(int primaryImageNumber) {
            nestedPrimaryImageNumber = primaryImageNumber;
            return this;
        }

        /**
         * Description announcement builder.
         *
         * @param description the description
         * @return the announcement builder
         */
        public AnnouncementBuilder description(String description) {
            nestedDescription = description;
            return this;
        }

        /**
         * Publication date announcement builder.
         *
         * @param publicationDate the publication date
         * @return the announcement builder
         */
        public AnnouncementBuilder publicationDate(LocalDateTime publicationDate) {
            nestedPublicationDate = publicationDate;
            return this;
        }

        /**
         * Status announcement builder.
         *
         * @param status the status
         * @return the announcement builder
         */
        public AnnouncementBuilder status(Status status) {
            nestedStatus = status;
            return this;
        }

        /**
         * Category announcement builder.
         *
         * @param category the category
         * @return the announcement builder
         */
        public AnnouncementBuilder category(Category category) {
            nestedCategory = category;
            return this;
        }

        /**
         * Region announcement builder.
         *
         * @param region the region
         * @return the announcement builder
         */
        public AnnouncementBuilder region(Region region) {
            nestedRegion = region;
            return this;
        }

        /**
         * Images announcement builder.
         *
         * @param images the images
         * @return the announcement builder
         */
        public AnnouncementBuilder images(List<Image> images) {
            nestedImages.addAll(images);
            return this;
        }

        /**
         * Build announcement.
         *
         * @return the announcement
         */
        public Announcement build() {
            return new Announcement(nestedId, nestedOwner, nestedTitle, nestedPrice, nestedPrimaryImageNumber,
                    nestedDescription, nestedPublicationDate, nestedStatus, nestedCategory, nestedRegion, nestedImages);
        }
    }
}
