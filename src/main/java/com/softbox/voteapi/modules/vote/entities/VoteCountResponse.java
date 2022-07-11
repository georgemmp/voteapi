package com.softbox.voteapi.modules.vote.entities;

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
