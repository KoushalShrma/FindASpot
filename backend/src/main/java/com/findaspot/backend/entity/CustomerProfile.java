package com.findaspot.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

/**
 * Customer Profile Entity - yahan par customer profile table ka representation hai
 */
@Entity
@Table(name = "customer_profiles")
public class CustomerProfile {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @Size(max = 100)
    @Column(name = "name")
    private String name;

    @Size(max = 15)
    @Column(name = "phone_no")
    private String phoneNo;

    @Size(max = 20)
    @Column(name = "vehicle_no")
    private String vehicleNo;

    @Column(name = "age")
    private Integer age;

    @Size(max = 10)
    @Column(name = "gender")
    private String gender;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @Size(max = 50)
    @Column(name = "city")
    private String city;

    @Size(max = 50)
    @Column(name = "state")
    private String state;

    @Column(name = "photo")
    private String photo;

    // yahan par default constructor hai
    public CustomerProfile() {}

    // yahan par constructor with parameters hai
    public CustomerProfile(User user, String name, String phoneNo, String vehicleNo) {
        this.user = user;
        this.userId = user.getUserId();
        this.name = name;
        this.phoneNo = phoneNo;
        this.vehicleNo = vehicleNo;
    }

    // yahan par getters aur setters hain
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userId = user != null ? user.getUserId() : null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}