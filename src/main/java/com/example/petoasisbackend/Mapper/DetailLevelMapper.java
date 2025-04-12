package com.example.petoasisbackend.Mapper;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Request.DataDetailLevel;

import java.util.function.Function;

public interface DetailLevelMapper<T> {
    Function<T, ModelDTO<T>> getMapper(DataDetailLevel level);
}
