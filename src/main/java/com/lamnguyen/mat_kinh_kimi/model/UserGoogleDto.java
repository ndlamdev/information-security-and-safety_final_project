package com.lamnguyen.mat_kinh_kimi.model;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author heaty566
 */
@Setter
@Getter
@ToString
public class UserGoogleDto {

    private String id;

    private String email;

    private boolean verified_email;

    private String name;

    private String given_name;

    private String family_name;

    private String picture;

    public UserGoogleDto() {
    }

    public UserGoogleDto(String id, String email, boolean verified_email, String name, String given_name, String family_name, String picture) {
        this.id = id;
        this.email = email;
        this.verified_email = verified_email;
        this.name = name;
        this.given_name = given_name;
        this.family_name = family_name;
        this.picture = picture;
    }
}
