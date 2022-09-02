package com.example.uploadfilespringdatajdbc.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.uploadfilespringdatajdbc.entity.tbl_uploadfile;

public interface UploadFileService {

	tbl_uploadfile saveUploadFile(MultipartFile file) throws Exception;

	tbl_uploadfile getFileUpload(String fileName);
}
