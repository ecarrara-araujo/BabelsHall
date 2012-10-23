package br.com.ecarrara.bh.entities;

import br.com.ecarrara.bh.infra.Display;

/**
 *
 * @author ecarrara
 */
public class Person {

    private String id;
    private String name;
    private Display display;
    
    public Person() {
    }
    
    public Person( String id, String name, Display display ) {
        this.id = id;
        this.name = name;
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDisplay( Display display ) {
        this.display = display;
    }

    public Display getDisplay() {
        return display;
    }

    @Override
    public boolean equals( Object obj ) {
        return super.equals( obj ) || 
                ( this.getClass().isInstance( obj )
                  && this.hashCode() == obj.hashCode() );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id.hashCode();
        return hash;
    }
    
    public void notify( Message message ) {
        if( display != null ) {
            display.show( message );
        }
    }
}
