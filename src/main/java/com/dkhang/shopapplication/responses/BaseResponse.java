package com.dkhang.shopapplication.responses;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public class BaseResponse {

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
