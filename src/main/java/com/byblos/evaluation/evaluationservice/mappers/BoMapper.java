package com.byblos.evaluation.evaluationservice.mappers;
import com.byblos.evaluation.evaluationservice.dtos.BoDto;
import com.byblos.evaluation.evaluationservice.models.Bo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface BoMapper {

    BoDto toBoDto(Bo bo);

    List<BoDto> toBoDtos(List<Bo> bos);

    Bo toBo(BoDto bodto);

}
