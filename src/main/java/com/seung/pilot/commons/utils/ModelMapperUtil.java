package com.seung.pilot.commons.utils;

import org.modelmapper.ModelMapper;

public class ModelMapperUtil {
    private static ModelMapper modelMapper;

    public ModelMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static ModelMapper get() {
        return modelMapper;
    }
}
