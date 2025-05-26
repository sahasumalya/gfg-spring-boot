package org.example.gfgspringboot.models;


import jakarta.persistence.*;

@Entity
@Table(name="assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    private AssetType type;

   private String name;

   @Embedded
   @AttributeOverrides({
           @AttributeOverride(name="author", column = @Column(name="author")),
           @AttributeOverride(name="publishDate", column = @Column(name="publishDate")),
           @AttributeOverride(name="genre", column = @Column(name="genre")),

   })
   private AssetMetaData metaData;

    public AssetType getType() {
        return type;
    }

    public void setType(AssetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssetMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(AssetMetaData metaData) {
        this.metaData = metaData;
    }

    public User getContributor() {
        return contributor;
    }

    public void setContributor(User contributor) {
        this.contributor = contributor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="contributor_id", referencedColumnName = "id")
   private User contributor;
}
