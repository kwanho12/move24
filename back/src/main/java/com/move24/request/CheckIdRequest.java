package com.move24.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CheckIdRequest {
    String memberId;
}
