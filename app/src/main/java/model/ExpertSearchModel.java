package model;

public class ExpertSearchModel {
    private String name;
    private String gender;
    private String country;
    private String county;
    private String estate;
    private String neigh;
    private String expert;
    private String phone;
    private String image;

    public ExpertSearchModel(String name, String gender, String country, String county, String estate, String neigh, String expert, String phone, String image) {
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.county = county;
        this.estate = estate;
        this.neigh = neigh;
        this.expert = expert;
        this.phone = phone;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public String getNeigh() {
        return neigh;
    }

    public void setNeigh(String neigh) {
        this.neigh = neigh;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
