package com.example.uploadfilespringdatajdbc.controller;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.uploadfilespringdatajdbc.entity.UploadFileResponse;
import com.example.uploadfilespringdatajdbc.service.UploadFileService;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Builder
@RequiredArgsConstructor
@Slf4j
public class UploadFileController {

	private final UploadFileService uploadFileService;

	@PostMapping("/files/uploadfile")
	public UploadFileResponse uploadFile(MultipartFile file) {
		try {

			var uploadFile = uploadFileService.saveUploadFile(file);
			return UploadFileResponse.builder().isError(false).fileName(uploadFile.getFileName())
					.fileLink(creteUploadFileLink(uploadFile.getFileName())).build();

		} catch (Exception e) {
			log.error("Upload File Failed.", e);
			return UploadFileResponse.builder().isError(true).fileName(file.getOriginalFilename()).build();
		}
	}

	private String creteUploadFileLink(String fileName) {
		return ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/files/" + fileName).toUriString();
	}

	@GetMapping("/files/{fileName}")
	public ResponseEntity<Resource> getFile(@PathVariable String fileName) {

		var fileUpload = uploadFileService.getFileUpload(fileName);
		var grpData = new ByteArrayResource(fileUpload.getGrpData());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, fileUpload.getFileType())
				/*
				 * Alternative:
				 * .cntentType(MediaType
				 * .valueOf(fileUpload.
				 * getFileType()))
				 */
				.cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)).cachePrivate().mustRevalidate())
				.body(grpData);

	}

	@PostMapping("/files/multi_uploadfile")
	public List<UploadFileResponse> uploadMultiFiles(@RequestPart List<MultipartFile> files) {
		return files.stream().map(this::uploadFile).collect(Collectors.toList());
	}

}
