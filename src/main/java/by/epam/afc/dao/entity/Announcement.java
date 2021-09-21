package by.epam.afc.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Announcement extends BaseEntity{
    private int ownerId;
    private String title;
    private BigDecimal price;
    private int primaryImageId;
    private String description;
    private LocalDateTime publicationDate;
    private Status status;
    private Category category;

    public Announcement(int id, int ownerId, String title, BigDecimal price, int primaryImageId,
                        String description, LocalDateTime publicationDate, Status announcementStatus, Category category) {
        super(id);
        this.ownerId = ownerId;
        this.title = title;
        this.price = price;
        this.primaryImageId = primaryImageId;
        this.description = description;
        this.publicationDate = publicationDate;
        this.status = announcementStatus;
        this.category = category;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPrimaryImageId() {
        return primaryImageId;
    }

    public void setPrimaryImageId(int primaryImageId) {
        this.primaryImageId = primaryImageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public enum Status {
        UNDEFINED,
        MODERATING,
        ACTIVE,
        INACTIVE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Announcement that = (Announcement) o;

        if (id != that.id) return false;
        if (ownerId != that.ownerId) return false;
        if (primaryImageId != that.primaryImageId) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (publicationDate != null ? !publicationDate.equals(that.publicationDate) : that.publicationDate != null)
            return false;
        if (status != that.status) return false;
        return category == that.category;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + ownerId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + primaryImageId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", primaryImageId=" + primaryImageId +
                ", description='" + description + '\'' +
                ", publicationDate=" + publicationDate +
                ", announcementStatus=" + status +
                ", category=" + category +
                '}';
    }

    public static AnnouncementBuilder getBuilder() {
        return new AnnouncementBuilder();
    }

    public static class AnnouncementBuilder {
        private int nestedId = UNDEFINED_ID;
        private int nestedOwnerId = UNDEFINED_ID;
        private String nestedTitle;
        private BigDecimal nestedPrice;
        private int nestedPrimaryImageId = UNDEFINED_ID;
        private String nestedDescription;
        private LocalDateTime nestedPublicationDate;
        private Status nestedAnnouncementStatus;
        private Category nestedCategory;

        public AnnouncementBuilder id(int id) {
            nestedId = id;
            return this;
        }

        public AnnouncementBuilder ownerId(int ownerId) {
            nestedOwnerId = ownerId;
            return this;
        }

        public AnnouncementBuilder title(String title) {
            nestedTitle = title;
            return this;
        }

        public AnnouncementBuilder price(BigDecimal price) {
            nestedPrice = price;
            return this;
        }

        public AnnouncementBuilder primaryImageId(int primaryImageId) {
            nestedPrimaryImageId = primaryImageId;
            return this;
        }

        public AnnouncementBuilder description(String description) {
            nestedDescription = description;
            return this;
        }

        public AnnouncementBuilder publicationDate(LocalDateTime publicationDate) {
            nestedPublicationDate = publicationDate;
            return this;
        }

        public AnnouncementBuilder announcementStatus(Status announcementStatus) {
            nestedAnnouncementStatus = announcementStatus;
            return this;
        }

        public AnnouncementBuilder category(Category category) {
            nestedCategory = category;
            return this;
        }

        public Announcement build() {
            return new Announcement(nestedId, nestedOwnerId, nestedTitle, nestedPrice, nestedPrimaryImageId,
                    nestedDescription, nestedPublicationDate, nestedAnnouncementStatus, nestedCategory);
        }
    }
}
