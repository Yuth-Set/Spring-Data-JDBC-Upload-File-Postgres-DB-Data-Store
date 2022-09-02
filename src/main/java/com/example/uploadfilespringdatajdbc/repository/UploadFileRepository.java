package com.example.uploadfilespringdatajdbc.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.uploadfilespringdatajdbc.entity.tbl_uploadfile;

@Repository
@Transactional(readOnly = true)
/* CrudRepository: Spring will create instance for you */
public interface UploadFileRepository extends CrudRepository<tbl_uploadfile, Integer> {

	/*
	 * Generated SQL: SELECT tbl_uploadfile.id FROM schema_uploadfile.tbl_uploadfile
	 * WHERE tbl_uploadfile.file_name = ? LIMIT 1
	 */
	boolean existsByfileName(String fileName);

	/*
	 * Generate SQL: SELECT...FROM schema_uploadfile.tbl_uploadfile WHERE tbl_uploadfile.file_name = ?
	 */
	Optional<tbl_uploadfile> findByFileName(String fileName);

}
