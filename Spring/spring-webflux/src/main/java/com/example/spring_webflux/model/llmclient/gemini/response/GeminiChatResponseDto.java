package com.example.spring_webflux.model.llmclient.gemini.response;

import com.example.spring_webflux.exception.CustomErrorType;
import com.example.spring_webflux.exception.ErrorTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeminiChatResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -1105456784688743622L;

    private List<GeminiCandidate> candidates;

    public String getSingleText() {
        return candidates.stream().findFirst()
                .flatMap(candidate -> candidate.getContent().getParts().stream().findFirst()
                        .map(part -> part.getText()))
                .orElseThrow(() ->
                        new ErrorTypeException("[GeminiResponse : There is no candidate]", CustomErrorType.GEMINI_RESPONSE_ERROR));
    }
}
