package by.epam.afc.dao.entity;

/**
 * The type User.
 */
public class User extends BaseEntity {
    private String firstname;
    private String lastname;
    private String login;
    private String email;
    private String phone;
    private Role role;
    private Status status;
    private String about;
    private Image profileImage;

    /**
     * Instantiates a new User.
     *
     * @param id           the id
     * @param firstname    the firstname
     * @param lastname     the lastname
     * @param login        the login
     * @param email        the email
     * @param phone        the phone
     * @param role         the role
     * @param status       the status
     * @param about        the about
     * @param profileImage the profile image
     */
    public User(int id, String firstname, String lastname, String login, String email,
                String phone, Role role, Status status, String about, Image profileImage) {
        super(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.about = about;
        this.profileImage = profileImage;
    }

    /**
     * Gets firstname.
     *
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets firstname.
     *
     * @param firstname the firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Gets lastname.
     *
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets lastname.
     *
     * @param lastname the lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
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
     * Gets about.
     *
     * @return the about
     */
    public String getAbout() {
        return about;
    }

    /**
     * Sets about.
     *
     * @param about the about
     */
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * Gets profile image.
     *
     * @return the profile image
     */
    public Image getProfileImage() {
        return profileImage;
    }

    /**
     * Sets profile image.
     *
     * @param profileImage the profile image
     */
    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * The enum Role.
     */
    public enum Role {
        /**
         * Undefined role.
         */
        UNDEFINED,
        /**
         * User role.
         */
        USER,
        /**
         * Moderator role.
         */
        MODERATOR,
        /**
         * Administrator role.
         */
        ADMINISTRATOR,
        /**
         * Guest role.
         */
        GUEST
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
         * Delayed reg status.
         */
        DELAYED_REG,
        /**
         * Active status.
         */
        ACTIVE,
        /**
         * Banned status.
         */
        BANNED
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

        User user = (User) o;

        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (role != user.role) return false;
        if (status != user.status) return false;
        if (about != null ? !about.equals(user.about) : user.about != null) return false;
        return profileImage != null ? profileImage.equals(user.profileImage) : user.profileImage == null;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = firstname != null ? firstname.hashCode() : 0;
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (about != null ? about.hashCode() : 0);
        result = 31 * result + (profileImage != null ? profileImage.hashCode() : 0);
        return result;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", about='" + about + '\'' +
                ", profileImage=" + profileImage +
                '}';
    }

    /**
     * Gets builder.
     *
     * @return the builder
     */
    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }

    /**
     * The type User builder.
     */
    public static class UserBuilder {
        private int nestedId = UNDEFINED_ID;
        private String nestedFirstName;
        private String nestedLastName;
        private String nestedLogin;
        private String nestedEmail;
        private String nestedPhone;
        private Role nestedRole;
        private Status nestedStatus;
        private String nestedAbout;
        private Image nestedProfileImage;

        private UserBuilder() {
        }

        /**
         * Id user builder.
         *
         * @param id the id
         * @return the user builder
         */
        public UserBuilder id(int id) {
            nestedId = id;
            return this;
        }

        /**
         * First name user builder.
         *
         * @param firstName the first name
         * @return the user builder
         */
        public UserBuilder firstName(String firstName) {
            nestedFirstName = firstName;
            return this;
        }

        /**
         * Last name user builder.
         *
         * @param lastName the last name
         * @return the user builder
         */
        public UserBuilder lastName(String lastName) {
            nestedLastName = lastName;
            return this;
        }

        /**
         * Login user builder.
         *
         * @param login the login
         * @return the user builder
         */
        public UserBuilder login(String login) {
            nestedLogin = login;
            return this;
        }

        /**
         * Email user builder.
         *
         * @param email the email
         * @return the user builder
         */
        public UserBuilder email(String email) {
            nestedEmail = email;
            return this;
        }

        /**
         * Phone user builder.
         *
         * @param phone the phone
         * @return the user builder
         */
        public UserBuilder phone(String phone) {
            nestedPhone = phone;
            return this;
        }

        /**
         * Role user builder.
         *
         * @param role the role
         * @return the user builder
         */
        public UserBuilder role(Role role) {
            nestedRole = role;
            return this;
        }

        /**
         * Status user builder.
         *
         * @param status the status
         * @return the user builder
         */
        public UserBuilder status(Status status) {
            nestedStatus = status;
            return this;
        }

        /**
         * About user builder.
         *
         * @param about the about
         * @return the user builder
         */
        public UserBuilder about(String about) {
            nestedAbout = about;
            return this;
        }

        /**
         * Profile image user builder.
         *
         * @param profileImage the profile image
         * @return the user builder
         */
        public UserBuilder profileImage(Image profileImage) {
            nestedProfileImage = profileImage;
            return this;
        }

        /**
         * Build user.
         *
         * @return the user
         */
        public User build() {
            return new User(nestedId, nestedFirstName, nestedLastName, nestedLogin, nestedEmail,
                    nestedPhone, nestedRole, nestedStatus, nestedAbout, nestedProfileImage);
        }
    }

}
