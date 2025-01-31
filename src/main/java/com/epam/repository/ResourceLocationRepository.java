package com.epam.repository;

import com.epam.entity.S3Location;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceLocationRepository extends JpaRepository<S3Location, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE s3location SET status = 'PERMANENT' where id = :s3LocationId")
    void updateToPermanent(@Param("s3LocationId") Integer s3LocationId);
}
