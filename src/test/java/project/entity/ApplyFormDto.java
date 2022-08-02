package project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.File;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyFormDto {
    private String name;
    private String phoneNumber;
    private String email;
    private File file;
    private String letter;

    public ApplyFormDto() {
    }

    public ApplyFormDto(String name, String phoneNumber, String email, String letter, File file) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.file = file;
        this.letter = letter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "ApplyForm{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", file=" + file +
                ", letter='" + letter + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApplyFormDto that = (ApplyFormDto) o;

        return new EqualsBuilder().append(getName(), that.getName()).append(getPhoneNumber(), that.getPhoneNumber()).append(getEmail(), that.getEmail()).append(getFile(), that.getFile()).append(getLetter(), that.getLetter()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getName()).append(getPhoneNumber()).append(getEmail()).append(getFile()).append(getLetter()).toHashCode();
    }
}