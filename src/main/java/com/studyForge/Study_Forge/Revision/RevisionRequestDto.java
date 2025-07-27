package com.studyForge.Study_Forge.Revision;
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
