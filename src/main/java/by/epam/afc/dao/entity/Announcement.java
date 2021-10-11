package by.epam.afc.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Announcement extends BaseEntity {
    private static final int SHORT_DESCRIPTION_LENGTH = 50;
    private static final String TO_BE_CONTINUED = "...";

    private User owner;
    private String title;
    private BigDecimal price;
    private int primaryImageNumber;
    private String description;
    private String shortDescription;
    private LocalDateTime publicationDate;
    private Status status;
    private Category category;
    private Region region;
    private List<Image> images;

    public Announcement(int id, User owner, String title, BigDecimal price, int primaryImageNumber,
                        String description, LocalDateTime publicationDate, Status announcementStatus,
                        Category category, Region region, List<Image> images) {
        super(id);
        this.owner = owner;
        this.title = title;
        this.price = price;
        this.primaryImageNumber = primaryImageNumber;
        this.description = description;
        this.publicationDate = publicationDate;
        this.status = announcementStatus;
        this.category = category;
        this.region = region;
        this.images = new LinkedList<>(images);
        initializeShortDescription();
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public void setPrimaryImageNumber(int primaryImageNumber) {
        this.primaryImageNumber = primaryImageNumber;
    }

    public int getPrimaryImageNumber() {
        return primaryImageNumber;
    }

    public String getPrimaryImage() {
        if(images.size() > primaryImageNumber){
            Image primaryImage = images.get(primaryImageNumber);
            return primaryImage.getBase64();
        }
        return "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = new ArrayList<>(images);
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

    private void initializeShortDescription() {
        if (description != null) {
            if (!description.isEmpty()) {
                if (description.length() <= SHORT_DESCRIPTION_LENGTH) {
                    shortDescription = description;
                } else {
                    shortDescription = description.substring(0, SHORT_DESCRIPTION_LENGTH) + TO_BE_CONTINUED;
                }
            } else {
                shortDescription = "";
            }
        }
    }

    public static AnnouncementBuilder getBuilder() {
        return new AnnouncementBuilder();
    }

    public static class AnnouncementBuilder {
        private int nestedId = UNDEFINED_ID;
        private User nestedOwner;
        private String nestedTitle;
        private BigDecimal nestedPrice;
        private int nestedPrimaryImageNumber;
        private String nestedDescription;
        private LocalDateTime nestedPublicationDate;
        private Status nestedAnnouncementStatus;
        private Category nestedCategory;
        private Region nestedRegion;
        private List<Image> nestedImages = new LinkedList<>();

        public AnnouncementBuilder id(int id) {
            nestedId = id;
            return this;
        }

        public AnnouncementBuilder owner(User owner) {
            nestedOwner = owner;
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

        public AnnouncementBuilder primaryImageNumber(int primaryImageNumber) {
            nestedPrimaryImageNumber = primaryImageNumber;
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

        public AnnouncementBuilder region(Region region) {
            nestedRegion = region;
            return this;
        }

        public AnnouncementBuilder images(List<Image> images) {
            nestedImages.addAll(images);
            return this;
        }

        public Announcement build() {
            return new Announcement(nestedId, nestedOwner, nestedTitle, nestedPrice, nestedPrimaryImageNumber,
                    nestedDescription, nestedPublicationDate, nestedAnnouncementStatus, nestedCategory, nestedRegion, nestedImages);
        }
    }
}
