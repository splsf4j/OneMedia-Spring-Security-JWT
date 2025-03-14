package org.vladislavb.onemediatesttask.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.vladislavb.onemediatesttask.dto.UserDto;
import org.vladislavb.onemediatesttask.entity.User;

/**
 * UserMapper is an interface that defines the mapping between the User entity and the UserDto.
 * This interface uses MapStruct to automatically generate the implementation of the mapping methods.
 * The mapping methods convert a User entity to a UserDto and vice versa.
 * It is configured to integrate with Spring and ignores any unmapped target properties.
 *
 * @author Vladislav Baryshev
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Converts a User entity to a UserDto.
     *
     * @param user the User entity to be converted.
     * @return the converted UserDto.
     */
    UserDto toDto(User user);

    /**
     * Converts a UserDto to a User entity.
     *
     * @param userDto the UserDto to be converted.
     * @return the converted User entity.
     */
    User toEntity(UserDto userDto);
}
