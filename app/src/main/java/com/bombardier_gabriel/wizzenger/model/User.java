package com.bombardier_gabriel.wizzenger.model;

/**
 * Created by gabb_ on 2018-01-08.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.bombardier_gabriel.wizzenger.Device;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabb on 2017-10-01.
 */
public class User {
    private String id;
    private String displayName;
    private String userName;
    private String email;
    private String password;
    private int age;
    private String phone;
    private int photoUrl;
    private Bitmap avatar;
    private String thumbnailBase64;
    private List<Device> devices;
    private List<String> contacts;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Exclude
    public Bitmap thumbnail;

    public User() {
        devices = new ArrayList<>();
    }

    public User(int img, String displayName){
        this.displayName = displayName;
        this.photoUrl = img;
    }

    public User(String userName, String email, String password, String phone, int photoUrl){
        this.userName = userName ;
        this.email= email;
        this.password= password;
        this.phone= phone;
        this.photoUrl = photoUrl;
    }

    public User(String userName, String email, String password, String phone, Bitmap avatar){
        this.userName = userName ;
        this.email= email;
        this.password= password;
        this.phone= phone;
        this.avatar = avatar;
    }

    public User(String userName, String email, String phone, int photoUrl){
        this.userName = userName ;
        this.email= email;
        this.phone= phone;
        this.photoUrl = photoUrl;
    }

    @Exclude
    public void addDevice(Device device) {
        devices.add(device);
    }

    @Exclude
    public Bitmap getThumbnail() {
        if(thumbnail == null) {
            byte[] thumbArray = Base64.decode(thumbnailBase64, Base64.DEFAULT);
            thumbnail = BitmapFactory.decodeByteArray(thumbArray, 0, thumbArray.length);
        }

        return thumbnail;
    }

    @Exclude
    public boolean hasFcmTokenChanged(String deviceId, String token) {
        boolean isFound = false;
        for(Device d : devices) {
            if(d != null) {
                if (TextUtils.equals(d.deviceId, deviceId)) {
                    isFound = true;
                    if(!TextUtils.equals(d.fcmToken, token)) {
                        d.fcmToken = token;
                        return true;
                    }
                }
            }
        }

        if(!isFound) {
            Device device = new Device();
            device.deviceId = deviceId;
            device.fcmToken = token;
            addDevice(device);
            return true;
        }

        return false;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(int photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Device> getDevicesList(){return this.devices;}

    public String getPhone(){return this.phone;}

    public String getPassword(){return this.password;}

    public String getid() {
        return id;
    }

    public void setid(String id){
        this.id = id;
    }

    @Exclude
    public Bitmap getAvatar() {
        return avatar;
    }

    @Exclude
    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}

