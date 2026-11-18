package io.github.rsinghsd.macmuseum.model;

import io.github.rsinghsd.macmuseum.family.ProductFamily;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/** A single Apple hardware model, e.g. the Macintosh 128K. */
@Entity
@Table(name = "apple_model")
public class AppleModel {

    public static final String APPLE = "Apple";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String slug;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "release_year", nullable = false)
    private int releaseYear;

    /** Launch price in US dollars; {@code null} means unknown. */
    @Column(name = "launch_price_usd", precision = 10, scale = 2)
    private BigDecimal launchPriceUsd;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "family_id", nullable = false)
    private ProductFamily family;

    @Enumerated(EnumType.STRING)
    @Column(name = "cpu_architecture", nullable = false, length = 20)
    private CpuArchitecture cpuArchitecture;

    @Column(name = "cpu_name", nullable = false, length = 100)
    private String cpuName;

    @Column(name = "clock_speed_mhz")
    private Integer clockSpeedMhz;

    @Column(name = "base_ram_kb")
    private Integer baseRamKb;

    @Column(nullable = false, length = 2000)
    private String summary;

    /** Maker of the machine; {@value #APPLE} for Apple products, e.g. "IBM" for contemporaries. */
    @Column(nullable = false, length = 60)
    private String manufacturer = APPLE;

    protected AppleModel() {
        // for JPA
    }

    public AppleModel(String slug, String name, int releaseYear, ProductFamily family,
                      CpuArchitecture cpuArchitecture, String cpuName, String summary) {
        this.slug = slug;
        this.name = name;
        this.releaseYear = releaseYear;
        this.family = family;
        this.cpuArchitecture = cpuArchitecture;
        this.cpuName = cpuName;
        this.summary = summary;
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public BigDecimal getLaunchPriceUsd() {
        return launchPriceUsd;
    }

    public void setLaunchPriceUsd(BigDecimal launchPriceUsd) {
        this.launchPriceUsd = launchPriceUsd;
    }

    public ProductFamily getFamily() {
        return family;
    }

    public void setFamily(ProductFamily family) {
        this.family = family;
    }

    public CpuArchitecture getCpuArchitecture() {
        return cpuArchitecture;
    }

    public void setCpuArchitecture(CpuArchitecture cpuArchitecture) {
        this.cpuArchitecture = cpuArchitecture;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public Integer getClockSpeedMhz() {
        return clockSpeedMhz;
    }

    public void setClockSpeedMhz(Integer clockSpeedMhz) {
        this.clockSpeedMhz = clockSpeedMhz;
    }

    public Integer getBaseRamKb() {
        return baseRamKb;
    }

    public void setBaseRamKb(Integer baseRamKb) {
        this.baseRamKb = baseRamKb;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
