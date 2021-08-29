package by.epam.afc.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class Announcement {
    private int id;
    private int ownerId;
    private String title;
    private BigDecimal price;
    private int primaryImageId;
    private String description;
    private Date publicationDate;
    private Status announcementStatus;
    private Category category;

    public Announcement(int id, int ownerId, String title, BigDecimal price, int primaryImageId,
                        String description, Date publicationDate, Status announcementStatus, Category category) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.price = price;
        this.primaryImageId = primaryImageId;
        this.description = description;
        this.publicationDate = publicationDate;
        this.announcementStatus = announcementStatus;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Status getAnnouncementStatus() {
        return announcementStatus;
    }

    public void setAnnouncementStatus(Status announcementStatus) {
        this.announcementStatus = announcementStatus;
    }

    public enum Status {
        MODERATING,
        ACTIVE,
        INACTIVE
    }

    public enum Category {
        REAL_ESTATE,
        AUTO_AND_TRANSPORT,
        APPLIANCES,
        COMPUTER_AND_COMPONENTS,
        PHONES_AND_TABLETS,
        FURNITURE,
        HOUSING,
        REPAIR_AND_CONSTRUCTION,
        GARDEN,
        OTHER
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
        if (announcementStatus != that.announcementStatus) return false;
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
        result = 31 * result + (announcementStatus != null ? announcementStatus.hashCode() : 0);
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
                ", announcementStatus=" + announcementStatus +
                ", category=" + category +
                '}';
    }

    public static AnnouncementBuilder getBuilder() {
        return new AnnouncementBuilder();
    }

    public static class AnnouncementBuilder {
        private int nestedId;
        private int nestedOwnerId;
        private String nestedTitle;
        private BigDecimal nestedPrice;
        private int nestedPrimaryImageId;
        private String nestedDescription;
        private Date nestedPublicationDate;
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

        public AnnouncementBuilder publicationDate(Date publicationDate) {
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
