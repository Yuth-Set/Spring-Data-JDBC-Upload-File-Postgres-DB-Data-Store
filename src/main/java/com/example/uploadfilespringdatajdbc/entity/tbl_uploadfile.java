package com.example.uploadfilespringdatajdbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class tbl_uploadfile {

	@Id
	@Column("id")
	private int id;
	
	@Column("file_name")
	private String fileName;
	
	@Column("file_type")
	private String fileType;
	
	@Column("grp_data")
	private byte[] grpData;
}
