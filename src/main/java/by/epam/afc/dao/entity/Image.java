package by.epam.afc.dao.entity;

import java.time.LocalDateTime;

public class Image extends BaseEntity {
    private LocalDateTime uploadData;
    private User uploadedBy;
    private String base64;

    public Image(int id, LocalDateTime uploadData, User uploadedBy, String base64) {
        super(id);
        this.uploadData = uploadData;
        this.uploadedBy = uploadedBy;
        this.base64 = base64;
    }

    public LocalDateTime getUploadData() {
        return uploadData;
    }

    public void setUploadData(LocalDateTime uploadData) {
        this.uploadData = uploadData;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getBase64() {
        return base64;
    }

    public void setImage(String base64) {
        this.base64 = base64;
    }

    public static ImageBuilder getBuilder() {
        return new ImageBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Image image = (Image) o;

        if (uploadData != null ? !uploadData.equals(image.uploadData) : image.uploadData != null) return false;
        if (uploadedBy != null ? !uploadedBy.equals(image.uploadedBy) : image.uploadedBy != null) return false;
        return base64 != null ? base64.equals(image.base64) : image.base64 == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (uploadData != null ? uploadData.hashCode() : 0);
        result = 31 * result + (uploadedBy != null ? uploadedBy.hashCode() : 0);
        result = 31 * result + (base64 != null ? base64.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", uploadData=" + uploadData +
                ", uploadedBy=" + uploadedBy +
                ", base64='" + (base64 == null ? "empty" : "exist") + '\'' +
                '}';
    }

    public static class ImageBuilder {
        private int nestedId = UNDEFINED_ID;
        private LocalDateTime nestedUploadData;
        private User nestedUploadedBy;
        private String nestedBase64;

        public ImageBuilder id(int id) {
            nestedId = id;
            return this;
        }

        public ImageBuilder uploadData(LocalDateTime uploadData) {
            nestedUploadData = uploadData;
            return this;
        }

        public ImageBuilder uploadedByUser(User uploadedBy) {
            nestedUploadedBy = uploadedBy;
            return this;
        }

        public ImageBuilder base64(String base64) {
            nestedBase64 = base64;
            return this;
        }

        public Image build() {
            return new Image(nestedId, nestedUploadData, nestedUploadedBy, nestedBase64);
        }
    }
}
