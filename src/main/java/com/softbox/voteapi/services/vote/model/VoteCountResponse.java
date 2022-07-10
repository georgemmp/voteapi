package com.softbox.voteapi.services.vote.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VoteCountResponse {
    private Long yes;
    private Long no;
}
