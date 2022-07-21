package com.softbox.voteapi.modules.vote.entities.enums;

import lombok.Getter;

@Getter
public enum VoteDescription {
    YES(1, "Sim"),
    NO(2, "Não");

    private Integer id;
    private String description;

    VoteDescription(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static VoteDescription toEnum(String description) {
        if (description == null) return null;

        for (VoteDescription item: VoteDescription.values()) {
            if (description.equals(item.getDescription())) return item;
        }

        throw new IllegalArgumentException("Invalid description: " + description);
    }
}
