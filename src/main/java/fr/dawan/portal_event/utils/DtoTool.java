package fr.dawan.portal_event.utils;

import org.modelmapper.ModelMapper;

public class DtoTool {
    private static ModelMapper mapper = new ModelMapper();

    public static <TSource, TDestination> TDestination convert(TSource source, Class<TDestination> clazz){
        return mapper.map(source, clazz);
    }
}
