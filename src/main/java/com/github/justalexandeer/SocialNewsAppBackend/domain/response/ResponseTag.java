package com.github.justalexandeer.SocialNewsAppBackend.domain.response;

public class ResponseTag {
    private Long id;
    private String name;
    private int amountOfUse;

    public ResponseTag() {
    }

    public ResponseTag(Long id, String name, int amountOfUse) {
        this.id = id;
        this.name = name;
        this.amountOfUse = amountOfUse;
    }

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

    public int getAmountOfUse() {
        return amountOfUse;
    }

    public void setAmountOfUse(int amountOfUse) {
        this.amountOfUse = amountOfUse;
    }
}
