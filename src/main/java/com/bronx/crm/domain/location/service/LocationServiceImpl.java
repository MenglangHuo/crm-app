package com.bronx.crm.domain.location.service;

import com.bronx.crm.domain.location.dto.LocationRequest;
import com.bronx.crm.domain.location.dto.LocationResponse;
import com.bronx.crm.domain.location.mapper.LocationMapper;
import com.bronx.crm.domain.location.repository.LocationRepository;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
import com.bronx.crm.domain.location.entity.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public LocationResponse create(LocationRequest request) {
        if (locationRepository.existsByName(request.name())) {
            throw new ConflictException("Location", "name", request.name());
        }
        Location location = locationMapper.toEntity(request);
        Location saved = locationRepository.save(location);
        return locationMapper.toResponse(saved);
    }

    @Override
    public LocationResponse get(Long id) {
        return locationMapper.toResponse(this.findById(id));
    }

    @Override
    public LocationResponse delete(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));
        location.setDeletedAt(LocalDateTime.now());
       return locationMapper.toResponse(locationRepository.save(location));
    }

    @Override
    public LocationResponse update(Long id, LocationRequest dto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));

        if (locationRepository.existsByNameAndIdNotIn(dto.name(),id)) {
            throw new ConflictException("Location", "name", dto.name());
        }

        locationMapper.updateEntityFromRequest(dto, location);
        Location updated = locationRepository.save(location);
        return locationMapper.toResponse(updated);
    }

    @Override
    public PageResponse<LocationResponse> getAll(int page, int size, String orderBy, String sortBy, String query) {
        PageRequest pageRequest=PageRequest.builder()
                .page(page)
                .size(size)
                .sortDirection(sortBy)
                .sortDirection(orderBy)
                .build();
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Location> pageRes = locationRepository.findAllLocationsByName(query,pageable);
        Page<LocationResponse> responsePage = pageRes.map(locationMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }

    private Location findById(Long id){
        return locationRepository.findById(id).orElseThrow(()->new ResolutionException("Location Not Found!"));
    }

}
