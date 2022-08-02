package project.entity;

import java.io.File;

public class ApplyFormDtoBuilder {
    private String name;
    private String phoneNumber;
    private String email;
    private String letter;
    private File file;

    public ApplyFormDtoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ApplyFormDtoBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ApplyFormDtoBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public ApplyFormDtoBuilder setLetter(String letter) {
        this.letter = letter;
        return this;
    }

    public ApplyFormDtoBuilder setFile(File file) {
        this.file = file;
        return this;
    }

    public ApplyFormDto build() {
        return new ApplyFormDto(name, phoneNumber, email, letter, file);
    }
}