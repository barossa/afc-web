package by.epam.afc.dao.model;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String login;
    private String email;
    private String phone;
    private Role role;
    private Status status;
    private String about;
    private Image profileImage;

    public User(int id, String firstname, String lastname, String login, String email,
                String phone, Role role, Status status, String about, Image profileImage) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public enum Role {
        USER,
        MODERATOR,
        ADMINISTRATOR
    }

    public enum Status {
        DELAYED_REG,
        ACTIVE,
        BANNED
    }

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

    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private int nestedId;
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

        public UserBuilder id(int id) {
            nestedId = id;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            nestedFirstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            nestedLastName = lastName;
            return this;
        }

        public UserBuilder login(String login) {
            nestedLogin = login;
            return this;
        }

        public UserBuilder email(String email) {
            nestedEmail = email;
            return this;
        }

        public UserBuilder phone(String phone) {
            nestedPhone = phone;
            return this;
        }

        public UserBuilder role(Role role) {
            nestedRole = role;
            return this;
        }

        public UserBuilder status(Status status) {
            nestedStatus = status;
            return this;
        }

        public UserBuilder about(String about) {
            nestedAbout = about;
            return this;
        }

        public UserBuilder profileImage(Image profileImage) {
            nestedProfileImage = profileImage;
            return this;
        }

        public User build() {
            return new User(nestedId, nestedFirstName, nestedLastName, nestedLogin, nestedEmail,
                    nestedPhone, nestedRole, nestedStatus, nestedAbout, nestedProfileImage);
        }
    }

}
