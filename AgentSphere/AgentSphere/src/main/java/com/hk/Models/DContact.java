package com.hk.Models;

import java.util.List;

/**
 * Created by Hari on 11/25/13.
 */
public class DContact {


    private String id;
    private String name;
    private String notes;
    private List<DPhone> phones;
    private List<String> emails;
    private List<DAddress> addresses;
    private List<DInstantMessenger> instantMessengers;
    private List<DOrganization> organizations;

    public DContact(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<DPhone> getPhones() {
        return phones;
    }

    public void setPhones(List<DPhone> phones) {
        this.phones = phones;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<DAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<DAddress> addresses) {
        this.addresses = addresses;
    }

    public List<DInstantMessenger> getInstantMessengers() {
        return instantMessengers;
    }

    public void setInstantMessengers(List<DInstantMessenger> instantMessengers) {
        this.instantMessengers = instantMessengers;
    }

    public List<DOrganization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<DOrganization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public String toString() {
        return "DContact [id=" + id + ", name=" + name + ", phones=" + phones
                + ", emails=" + emails + ", addresses=" + addresses
                + ", instantMessengers=" + instantMessengers
                + ", organizations=" + organizations + ", notes=" + notes + "]";
    }


}
