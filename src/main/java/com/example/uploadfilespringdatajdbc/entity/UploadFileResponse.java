package com.example.uploadfilespringdatajdbc.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadFileResponse {

	private boolean isError;
	private String fileName;
	private String fileLink;
}
