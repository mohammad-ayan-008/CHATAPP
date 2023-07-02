package com.example.Holders;

public class User {
    private String Name, Pass, Email, UID, Status, ProfileURL, bio;

    public User(
            String Name,
            String Pass,
            String Email,
            String UID,
            String Status,
            String ProfileURL,
            String bio) {
        
        
        this.Name = Name;
        this.Pass = Pass;
        this.Email = Email;
        this.UID = UID;
        this.Status = Status;
        this.ProfileURL = ProfileURL;
        this.bio = bio;
        
    }

    public User() {}

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPass() {
        return this.Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getUID() {
        return this.UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getProfileURL() {
        return this.ProfileURL;
    }

    public void setProfileURL(String ProfileURL) {
        this.ProfileURL = ProfileURL;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
