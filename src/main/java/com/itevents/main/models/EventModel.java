package com.itevents.main.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;

@Entity
@Table(name = "events")
public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String labels;

    @Column(name = "date_description", columnDefinition = "TEXT")
    private String dateDescription;

    @Column
    private java.sql.Date date;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(name = "requires_ticket", nullable = false)
    private boolean requiresTicket = false;

    @Column(name = "total_tickets")
    private Integer totalTickets;

    @Column(name = "available_tickets")
    private Integer availableTickets;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationModel location;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getDateDescription() {
        return dateDescription;
    }

    public void setDateDescription(String dateDescription) {
        this.dateDescription = dateDescription;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isRequiresTicket() {
        return requiresTicket;
    }

    public void setRequiresTicket(boolean requiresTicket) {
        this.requiresTicket = requiresTicket;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(Integer availableTickets) {
        this.availableTickets = availableTickets;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    @AssertTrue(message = "If tickets are required, total and available tickets must be provided.")
    public boolean isTicketDataValid() {
        if (requiresTicket) {
            return totalTickets != null && availableTickets != null;
        }
        return true;
    }
}


