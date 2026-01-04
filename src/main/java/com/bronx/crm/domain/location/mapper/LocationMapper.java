package com.bronx.crm.domain.location.mapper;

import com.bronx.crm.domain.location.dto.LocationRequest;
import com.bronx.crm.domain.location.dto.LocationResponse;
import com.bronx.crm.domain.location.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationMapper {

    LocationResponse toResponse(Location location);

    Location toEntity(LocationRequest request);

    void updateEntityFromRequest(LocationRequest request, @MappingTarget Location location);
}
