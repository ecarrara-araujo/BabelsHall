package br.com.ecarrara.bh.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ecarrara
 */
public class Person {

    private String id;
    private String name;
    private AsyncContext asyncContext;
    
    public Person() {
    }
    
    public Person( String id, String name, AsyncContext ctx ) {
        this.id = id;
        this.name = name;
        this.asyncContext = ctx;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setAsyncContext( AsyncContext ctx ) {
        this.asyncContext = ctx;
    }

    public AsyncContext getAsyncContext() {
        return asyncContext;
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
    
    private static final ObjectMapper mapper = new ObjectMapper();
    public void notify( Message message ) throws IOException {
        if( asyncContext != null ) {
            HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
            response.setContentType( "application/json" );
            response.getWriter().write( mapper.writeValueAsString( message ) );
            response.flushBuffer();
            asyncContext.complete();
            asyncContext = null;
        }
    }
}
