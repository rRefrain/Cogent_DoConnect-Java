package com.cogent.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.cogent.entity.Answer;
import com.cogent.entity.AnswerDTO;
import com.cogent.entity.AnswerVote;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "voteTally", ignore = true)
    @Mapping(target = "votes", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateAnswerFromDto(AnswerDTO dto, @MappingTarget Answer entity);
    
    @Mapping(target = "id", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateAnswerVote(AnswerVote answerVote, @MappingTarget AnswerVote entity);
}
