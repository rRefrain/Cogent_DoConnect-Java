package com.cogent.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.cogent.entity.Question;
import com.cogent.entity.QuestionDTO;
import com.cogent.entity.QuestionVote;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "voteTally", ignore = true)
    @Mapping(target = "votes", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateQuestion(QuestionDTO obj, @MappingTarget Question entity);
    
    @Mapping(target = "id", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateQuestionVote(QuestionVote questionVote, @MappingTarget QuestionVote entity);
}
