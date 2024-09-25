package com.cv.s04uamservice.repository;

import com.cv.s01coreservice.entity.UserDetail;
import com.cv.s01coreservice.repository.generic.GenericRepository;
import com.cv.s01coreservice.repository.generic.GenericSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository, GenericSpecification<UserDetail>,
        JpaRepository<UserDetail, String>, JpaSpecificationExecutor<UserDetail> {

}
