package com.example.uploadfilespringdatajdbc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.uploadfilespringdatajdbc.entity.tbl_uploadfile;
import com.example.uploadfilespringdatajdbc.repository.UploadFileRepository;
import com.example.uploadfilespringdatajdbc.service.UploadFileService;
import com.example.uploadfilespringdatajdbc.service.exception.FileNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {

	private final UploadFileRepository uploadFileRepository;

	@Override
	public tbl_uploadfile saveUploadFile(MultipartFile file) throws Exception {

		if (uploadFileRepository.existsByfileName(file.getOriginalFilename())) {
			log.info("This file {} has been already existed: ", file.getOriginalFilename());
			return tbl_uploadfile.builder().fileName(file.getOriginalFilename()).build();
		}

		var uploadFile = tbl_uploadfile.builder().fileName(file.getOriginalFilename()).fileType(file.getContentType())
				.grpData(file.getBytes()).build();

		return uploadFileRepository.save(uploadFile);
	}

	@Override
	public tbl_uploadfile getFileUpload(String fileName) {
		return uploadFileRepository.findByFileName(fileName).orElseThrow(FileNotFoundException::new);
	}

}
