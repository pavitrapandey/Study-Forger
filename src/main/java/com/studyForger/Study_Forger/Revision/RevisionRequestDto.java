package com.studyForger.Study_Forger.Revision;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevisionRequestDto{
    private String topicId;
    private int qualityScore;
}
