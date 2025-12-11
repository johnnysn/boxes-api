package com.uriel.boxes.mapper;

import com.uriel.boxes.data.entity.Item;
import com.uriel.boxes.dto.input.ItemUpdateInDto;
import com.uriel.boxes.dto.output.ItemOutDto;
import com.uriel.boxes.mapper.resolver.BoxMapperResolver;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {BoxMapperResolver.class}
)
public interface ItemMapper {

    ItemOutDto entityToDto(Item item);

    @Mapping(source = "boxId", target = "box")
    Item dtoToEntity(@Valid ItemUpdateInDto data);
}
