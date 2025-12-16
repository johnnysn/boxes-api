package com.uriel.boxes.mapper;

import com.uriel.boxes.data.entity.Box;
import com.uriel.boxes.dto.output.BoxOutDto;
import com.uriel.boxes.dto.output.BoxWithItemsOutDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoxMapper {

    BoxWithItemsOutDto entityToDtoWithItems(Box box);
    BoxOutDto entityToDto(Box box);
}
