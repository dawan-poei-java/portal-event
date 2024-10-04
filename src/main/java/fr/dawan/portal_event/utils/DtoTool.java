package fr.dawan.portal_event.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DtoTool {
    private static ModelMapper mapper = new ModelMapper();

    public static  <TSource, TDestination> TDestination convert(TSource source, Class<TDestination> clazz){
        return mapper.map(source, clazz);
    }
}