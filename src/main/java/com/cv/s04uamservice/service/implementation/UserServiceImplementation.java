package com.cv.s04uamservice.service.implementation;

import java.util.Map;
import java.util.stream.Collectors;

import com.cv.s01coreservice.constant.ApplicationConstant;
import com.cv.s01coreservice.constant.ApplicationResponseConstant;
import com.cv.s01coreservice.dto.PaginationDto;
import com.cv.s01coreservice.dto.UserDto;
import com.cv.s01coreservice.entity.UserDetail;
import com.cv.s01coreservice.service.function.StaticFunction;
import com.cv.s04uamservice.exception.ApplicationException;
import com.cv.s04uamservice.repository.UserRepository;
import com.cv.s04uamservice.service.intrface.UserService;
import com.cv.s04uamservice.service.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UserServiceImplementation implements UserService {

    private UserRepository repository;
    private UserMapper mapper;

    @Override
    public UserDto create(UserDto dto) throws Exception {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public UserDto update(UserDto dto) throws Exception {
        return mapper.toDto(repository.findById(dto.getId()).map(entity -> {
            BeanUtils.copyProperties(dto, entity);
            repository.save(entity);
            return entity;
        }).orElseThrow(() -> new ApplicationException(ApplicationResponseConstant.FAILURE_003_RECORD_NOT_FOUND)));
    }

    @Override
    public Boolean updateStatus(String id, boolean status) throws Exception {
        return repository.findById(id).map(entity -> {
            entity.setStatus(status);
            repository.save(entity);
            return true;
        }).orElseThrow(() -> new ApplicationException(ApplicationResponseConstant.FAILURE_003_RECORD_NOT_FOUND));
    }

    @Override
    public UserDto readOne(String id) throws Exception {
        return mapper.toDto(repository
                .findByIdAndStatus(id, ApplicationConstant.APPLICATION_STATUS_ACTIVE, UserDetail.class)
                .orElseThrow(() -> new ApplicationException(ApplicationResponseConstant.FAILURE_003_RECORD_NOT_FOUND)));

    }

    @Override
    public Boolean delete(String id) throws Exception {
        repository.deleteById(id);
        return true;
    }

    @Override
    public PaginationDto readAll(PaginationDto dto) throws Exception {
        Page<UserDetail> page;
        if (StringUtils.hasText(dto.getSearchField()) && StringUtils.hasText(dto.getSearchValue())) {
            page = repository.findAll(repository.searchSpec(dto.getSearchField(), dto.getSearchValue()),
                    StaticFunction.generatePageRequest.apply(dto));
        } else {
            page = repository.findAll(StaticFunction.generatePageRequest.apply(dto));
        }
        dto.setTotal(page.getTotalElements());
        dto.setResult(page.stream().map(entity -> mapper.toDto(entity)).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public Map<String, String> readIdAndNameMap() throws Exception {
        return repository.findAllByStatus(ApplicationConstant.APPLICATION_STATUS_ACTIVE, UserDetail.class).stream()
                .collect(Collectors.toMap(UserDetail::getId, UserDetail::getName));
    }

}
