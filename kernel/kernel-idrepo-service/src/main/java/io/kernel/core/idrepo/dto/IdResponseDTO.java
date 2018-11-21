package io.kernel.core.idrepo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class IdResponseDTO extends BaseIdRequestResponseDTO {
	private List<ErrorDTO> err;
	private String registrationId;
	private String status;
	private ResponseDTO response;
}
