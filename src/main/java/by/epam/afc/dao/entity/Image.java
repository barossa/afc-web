package by.epam.afc.dao.entity;

import java.time.LocalDateTime;

/**
 * The type Image.
 */
public class Image extends BaseEntity {
    private LocalDateTime uploadData;
    private User uploadedBy;
    private String base64;

    /**
     * Instantiates a new Image.
     *
     * @param id         the id
     * @param uploadData the upload data
     * @param uploadedBy the uploaded by
     * @param base64     the base 64
     */
    public Image(int id, LocalDateTime uploadData, User uploadedBy, String base64) {
        super(id);
        this.uploadData = uploadData;
        this.uploadedBy = uploadedBy;
        this.base64 = base64;
    }

    /**
     * Gets upload data.
     *
     * @return the upload data
     */
    public LocalDateTime getUploadData() {
        return uploadData;
    }

    /**
     * Sets upload data.
     *
     * @param uploadData the upload data
     */
    public void setUploadData(LocalDateTime uploadData) {
        this.uploadData = uploadData;
    }

    /**
     * Gets uploaded by.
     *
     * @return the uploaded by
     */
    public User getUploadedBy() {
        return uploadedBy;
    }

    /**
     * Sets uploaded by.
     *
     * @param uploadedBy the uploaded by
     */
    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    /**
     * Gets base 64.
     *
     * @return the base 64
     */
    public String getBase64() {
        return base64;
    }

    /**
     * Sets image.
     *
     * @param base64 the base 64
     */
    public void setImage(String base64) {
        this.base64 = base64;
    }

    /**
     * Gets builder.
     *
     * @return the builder
     */
    public static ImageBuilder getBuilder() {
        return new ImageBuilder();
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

        Image image = (Image) o;

        if (uploadData != null ? !uploadData.equals(image.uploadData) : image.uploadData != null) return false;
        if (uploadedBy != null ? !uploadedBy.equals(image.uploadedBy) : image.uploadedBy != null) return false;
        return base64 != null ? base64.equals(image.base64) : image.base64 == null;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (uploadData != null ? uploadData.hashCode() : 0);
        result = 31 * result + (uploadedBy != null ? uploadedBy.hashCode() : 0);
        result = 31 * result + (base64 != null ? base64.hashCode() : 0);
        return result;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", uploadData=" + uploadData +
                ", uploadedBy=" + uploadedBy +
                ", base64='" + (base64 == null ? "empty" : "exist") + '\'' +
                '}';
    }

    /**
     * The type Image builder.
     */
    public static class ImageBuilder {
        private int nestedId = UNDEFINED_ID;
        private LocalDateTime nestedUploadData;
        private User nestedUploadedBy;
        private String nestedBase64;

        /**
         * Id image builder.
         *
         * @param id the id
         * @return the image builder
         */
        public ImageBuilder id(int id) {
            nestedId = id;
            return this;
        }

        /**
         * Upload data image builder.
         *
         * @param uploadData the upload data
         * @return the image builder
         */
        public ImageBuilder uploadData(LocalDateTime uploadData) {
            nestedUploadData = uploadData;
            return this;
        }

        /**
         * Uploaded by user image builder.
         *
         * @param uploadedBy the uploaded by
         * @return the image builder
         */
        public ImageBuilder uploadedByUser(User uploadedBy) {
            nestedUploadedBy = uploadedBy;
            return this;
        }

        /**
         * Base 64 image builder.
         *
         * @param base64 the base 64
         * @return the image builder
         */
        public ImageBuilder base64(String base64) {
            nestedBase64 = base64;
            return this;
        }

        /**
         * Build image.
         *
         * @return the image
         */
        public Image build() {
            return new Image(nestedId, nestedUploadData, nestedUploadedBy, nestedBase64);
        }
    }
}
