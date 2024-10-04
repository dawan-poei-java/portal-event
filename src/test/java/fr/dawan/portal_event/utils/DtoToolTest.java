package fr.dawan.portal_event.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class DtoToolTest {

    @Test
    public void testConvert(){
        Source source = new Source("test",34);

        Destination destination = DtoTool.convert(source, Destination.class);
    
        // Vérifier que la conversion a bien fonctionné
        assertNotNull(destination);
        assertEquals(source.getName(), destination.getName());
        assertEquals(source.getValue(), destination.getValue());
    }

    private static class Source{
        private String name;
        private int value;

        public Source(String name, int value){
            this.name = name;
            this.value = value;
        }
        public String getName() {return this.name;}
        public int getValue(){ return this.value;}
    }

    private static class Destination{
        private String name;
        private int value;
        
        public String getName(){return this.name;}
        public void setName(String name){this.name=name;}
        public int getValue(){return this.value;}
        public void setValue(int value){this.value = value;}
    }
}

