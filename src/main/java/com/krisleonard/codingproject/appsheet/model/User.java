package com.krisleonard.codingproject.appsheet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

/**
 * The User object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    /** The user id */
    private Integer id = null;

    /** The first name of the user */
    private String name = null;

    /** The age of the user */
    private Integer age = null;

    /** The last name of the user */
    private String lastName = null;

    /** The phone number of the user */
    private String number = null;

    /** The URL for a photo of the user */
    private String photo = null;

    /** The bio for the user */
    private String bio = null;

    /**
     * Get the user id
     *
     * @return The user id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the user id
     *
     * @param id The user id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the first name of the user
     *
     * @return The first name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Set the first name of the user
     *
     * @param name The first name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the age of the user
     *
     * @return The age of the user
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Set the age of the user
     *
     * @param age The age of the user
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Get the last name of the user
     *
     * @return The last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the user
     *
     * @param lastName The last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the phone number of the user
     *
     * @return The phone number of the user
     */
    public String getNumber() {
        return number;
    }

    /**
     * Set the phone number of the user
     *
     * @param number The phone number of the user
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Get the photo URL of the user
     *
     * @return The photo URL of the user
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Set the photo URL of the user
     *
     * @param photo The photo URL of the user
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Get the bio of the user
     *
     * @return The bio of the user
     */
    public String getBio() {
        return bio;
    }

    /**
     * Set the bio of the user
     *
     * @param bio The bio of the user
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(this.id, user.id) &&
                Objects.equals(this.name, user.name) &&
                Objects.equals(this.age, user.age) &&
                Objects.equals(this.lastName, user.lastName) &&
                Objects.equals(this.number, user.number) &&
                Objects.equals(this.photo, user.photo) &&
                Objects.equals(this.bio, user.bio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, lastName, number, photo, bio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    age: ").append(toIndentedString(age)).append("\n");
        sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
        sb.append("    number: ").append(toIndentedString(number)).append("\n");
        sb.append("    photo: ").append(toIndentedString(photo)).append("\n");
        sb.append("    bio: ").append(toIndentedString(bio)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}